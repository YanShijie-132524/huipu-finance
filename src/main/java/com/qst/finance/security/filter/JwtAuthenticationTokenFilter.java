package com.qst.finance.security.filter;

import com.qst.finance.common.HttpStatusEnum;
import com.qst.finance.entity.SysUser;
import com.qst.finance.security.jwt.JwtUtil;
import com.qst.finance.security.model.LoginUser;
import com.qst.finance.service.user.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1) 解析 claims
            Claims claims = jwtUtil.getClaimByToken(token);

            if (claims == null || jwtUtil.isTokenExpired(claims.getExpiration())) {
                throw new RuntimeException("Token 无效或已过期");
            }

            // 2) 只在未认证时注入
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                // sub 存的是 userId
                Long userId = Long.valueOf(claims.getSubject());

                // （可选）校验 typ=access，拦掉 refresh 误用
                String typ = claims.get("typ", String.class);
                if ("refresh".equalsIgnoreCase(typ)) {
                    throw new RuntimeException("Refresh Token 不可用于访问受保护资源");
                }

                // 3) 查库拿最新用户（确保锁定、禁用等状态即时生效）
                SysUser user = userService.getById(userId);
                if (Objects.isNull(user)) {
                    throw new RuntimeException("用户不存在或已被删除");
                }

                // 4) 组装 LoginUser（你也可以通过自定义 UserDetailsService 按 userName 加载）
                LoginUser loginUser = new LoginUser(user);

                // 5) 注入 SecurityContext
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                authentication.setDetails(request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatusEnum.UNAUTHORIZED.getCode());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
