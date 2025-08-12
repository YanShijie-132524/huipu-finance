package com.qst.finance.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public final class JwtUtil {

    // 密钥（≥32字符）
    private String secret;

    // 默认有效期
    private Duration expire = Duration.ofDays(30);

    // 默认头与方案（向后兼容）
    private String header = "Authorization";
    private String tokenType = "Bearer";

    /**
     * 生成 Access Token：sub=userId，typ=access
     */
    public String generateAccessToken(Long userId, Map<String, Object> extraClaims, Long ttlMillis) {
        return buildToken(
                String.valueOf(Objects.requireNonNull(userId, "userId 不能为空")),
                // 额外 claims：typ=access
                merge(extraClaims, Map.of("typ", "access")),
                ttlMillis
        );
    }

    /**
     * 生成 Refresh Token：sub=userId，typ=refresh（TTL 通常更长）
     */
    public String generateRefreshToken(Long userId, Map<String, Object> extraClaims, Long ttlMillis) {
        return buildToken(
                String.valueOf(Objects.requireNonNull(userId, "userId 不能为空")),
                merge(extraClaims, Map.of("typ", "refresh")),
                ttlMillis
        );
    }

    /**
     * 解析 JWT，返回 Claims（自动去除 Bearer 前缀）
     */
    public Claims getClaimByToken(String token) {
        SecretKey key = requireKey();
        JwtParser parser = Jwts.parser().verifyWith(key).build();
        return parser.parseSignedClaims(stripBearer(token)).getPayload();
    }

    /**
     * 过期判断
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration != null && expiration.before(new Date());
    }

    /* ==================== 私有实现 ==================== */

    private String buildToken(String subject, Map<String, Object> claims, Long ttlMillis) {
        SecretKey key = requireKey();
        long now = System.currentTimeMillis();
        Date iat = new Date(now);

        Long ttl = (ttlMillis != null ? ttlMillis : (expire != null ? expire.toMillis() : null));
        Date exp = (ttl != null && ttl > 0) ? new Date(now + ttl) : null;

        var builder = Jwts.builder()
                .subject(subject)            // sub: userId（字符串）
                .issuedAt(iat)               // iat
                .signWith(key, Jwts.SIG.HS256)
                .header().type("JWT").and();

        if (claims != null && !claims.isEmpty()) {
            builder.claims(claims);         // 自定义 claims（包含 typ=access/refresh）
        }
        if (exp != null) {
            builder.expiration(exp);        // exp
        }
        return tokenType + " " + builder.compact();
    }

    private SecretKey requireKey() {
        Objects.requireNonNull(secret, "jwt.secret 未配置");
        if (secret.length() < 32) {
            throw new IllegalArgumentException("jwt.secret 长度至少 32 字符（≈256bit）");
        }
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 去掉 Bearer 前缀
     */
    public static String stripBearer(String token) {
        if (token == null) return null;
        return token.trim().startsWith("Bearer ") ? token.substring(7).trim() : token;
    }

    private static Map<String, Object> merge(Map<String, Object> a, Map<String, Object> b) {
        if (a == null || a.isEmpty()) return b;
        if (b == null || b.isEmpty()) return a;
        var m = new java.util.HashMap<>(a);
        m.putAll(b);
        return m;
    }
}
