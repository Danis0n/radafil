package com.danis0n.repository;

import com.danis0n.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class SessionDao {

    private final RedisTemplate<String, Session> redis;
    private final TimeUnit SESSION_EXPIRE_TYPE = TimeUnit.DAYS;
    private final Integer SESSION_EXPIRE_TIME_IN_DAYS = 10;

    public Boolean save(Session session) {
        try {

            redis.opsForValue()
                    .set(
                            session.getSessionId(),
                            session,
                            SESSION_EXPIRE_TIME_IN_DAYS,
                            SESSION_EXPIRE_TYPE
                    );
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Boolean delete(String uuid) {

        try {
            redis.opsForValue()
                    .set(
                            uuid,
                            new Session(),
                            1,
                            TimeUnit.NANOSECONDS
                    );
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }
}
