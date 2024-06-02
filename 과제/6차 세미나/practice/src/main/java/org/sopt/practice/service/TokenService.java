package org.sopt.practice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final String REFRESH_TOKEN_KEY_PREFIX = "auth:refreshToken:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void storeRefreshToken(String userId, String refreshToken, long durationInMilliseconds) {
        redisTemplate.opsForValue().set(REFRESH_TOKEN_KEY_PREFIX + userId, refreshToken, durationInMilliseconds, TimeUnit.MILLISECONDS);
    }

    public boolean validateRefreshToken(String userId, String refreshToken) {
        String storedToken = redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY_PREFIX + userId);
        return refreshToken.equals(storedToken);
    }

    public void removeRefreshToken(String userId) {
        redisTemplate.delete(REFRESH_TOKEN_KEY_PREFIX + userId);
    }
}
