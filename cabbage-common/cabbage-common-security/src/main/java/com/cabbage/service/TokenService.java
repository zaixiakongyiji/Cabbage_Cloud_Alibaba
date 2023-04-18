package com.cabbage.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cabbage.core.domain.model.UserResult;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TokenService {
    private static final Long MILLIS_MINUTE_TEN = 20 * 60L;
    /**
     * jwt 加密 key，默认值：cabbage.
     */
    @Value("${configure.jwt.key}")
    private static String KEY;
    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    @Value("${configure.jwt.ttl}")
    private static Long TTL;
    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    @Value("${configure.jwt.remember}")
    private static Long REMEMBER;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    public void verifyToken(UserResult userResult) {
        long currentTime = System.currentTimeMillis();
        long expireTime = currentTime + stringRedisTemplate.getExpire("security:jwt:" + userResult.getUsername());
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(userResult);
        }
    }

    private void refreshToken(UserResult userResult) {
        stringRedisTemplate.expire("security:jwt:" + userResult.getUsername(), TTL, TimeUnit.MILLISECONDS);
    }

    /**
     * 创建JWT
     *
     * @param rememberMe  记住我
     * @param id          用户id
     * @param subject     用户名
     * @param roles       用户角色
     * @param authorities 用户权限
     * @return JWT
     */

    public String createJWT(Boolean rememberMe, Long id, String subject, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder().setId(id.toString()).setSubject(subject).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, KEY).claim("roles", roles).claim("authorities", authorities);
        if (!StringUtils.checkValNotNull(rememberMe)) {
            rememberMe = false;
        }
        // 设置过期时间
        Long ttl = rememberMe ? REMEMBER : TTL;
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        // 将生成的JWT保存至Redis
        stringRedisTemplate.opsForValue().set("security:jwt:" + subject, jwt, ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */

    public String createJWT(Authentication authentication, Boolean rememberMe) {
        UserResult userResult = (UserResult) authentication.getPrincipal();
        return createJWT(rememberMe, userResult.getId(), userResult.getUsername(), userResult.getRoles(), userResult.getAuthorities());
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */

    public Claims parseJWT(String jwt) {

        try {
            Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();

            String username = claims.getSubject();
            String redisKey = "security:jwt:" + username;

            // 校验redis中的JWT是否存在
            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new SecurityException("Token已过期");
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new SecurityException("当前用户已在别处登录，请尝试更改密码或重新登录！");
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new SecurityException("Token已过期");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new SecurityException("token 解析失败，请尝试重新登录！");
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new SecurityException("token 解析失败，请尝试重新登录！");
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new SecurityException("token 解析失败，请尝试重新登录！");
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new SecurityException("token 解析失败，请尝试重新登录！");
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */

    public Boolean invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        return stringRedisTemplate.delete("security:jwt:" + username);
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */

    public String getUsernameFromJWT(String jwt) {
//        String jwt=getJwtFromRequest(request);

        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }


    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.checkValNotNull(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.replace("Bearer ", "");
        }
        return bearerToken;
    }


}
