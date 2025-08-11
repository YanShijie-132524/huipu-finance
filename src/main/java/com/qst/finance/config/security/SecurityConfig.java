package com.qst.finance.config.security;

import com.qst.finance.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    // 指定密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 从 AuthenticationConfiguration 中获取并注册 AuthenticationManager Bean，
     * 供自定义登录或其他需要手动调用认证逻辑的场景使用。
     *
     * @param config AuthenticationConfiguration 配置对象
     * @return AuthenticationManager 实例
     * @throws Exception 获取失败时抛出异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    /**
     * 配置 Spring Security 的过滤器链（SecurityFilterChain）。
     * <p>
     * 在 Spring Security 6 中，推荐通过注册 SecurityFilterChain Bean 来定制安全规则。
     * 该配置适用于前后端分离的 REST API 场景，使用 Token（如 JWT）进行身份验证，
     * 禁用 CSRF、开启基本认证和表单登录（可视需要保留/去除），并配置 URL 访问权限规则。
     * </p>
     *
     * @param http Spring Security 提供的 HttpSecurity，用于链式配置安全策略
     * @return 构建完成的 SecurityFilterChain
     * @throws Exception 配置过程中发生错误时抛出
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) throws Exception {
        http
                // 1. 禁用 CSRF（跨站请求伪造）保护
                //    适用于 Token 认证，避免 CSRF 检查阻碍前后端分离的请求
                .csrf(AbstractHttpConfigurer::disable)

                // 2. 开启 HTTP Basic 认证（主要用于测试或接口调试）
                .httpBasic(Customizer.withDefaults())

                // 3. 开启表单登录（可选，方便通过浏览器直接访问进行调试）
                .formLogin(Customizer.withDefaults())

                // 4. 配置权限规则
                .authorizeHttpRequests(authorize -> authorize
                        // 放行登录接口
                        .requestMatchers("/api/user/login").permitAll()
                        // 其他请求均需认证
                        .anyRequest().authenticated()
                )
                // 5. 添加自定义 JWT 过滤器
                // 把你的 JWT 过滤器放到 UsernamePasswordAuthenticationFilter 之前
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
