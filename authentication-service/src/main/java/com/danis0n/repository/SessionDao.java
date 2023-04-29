package com.danis0n.repository;

import com.danis0n.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

import static com.danis0n.constant.SessionConfiguration.SESSION_EXPIRE_TIME_IN_DAYS;


@Repository
@RequiredArgsConstructor
public class SessionDao {

    private final RedisTemplate<String, Session> redis;

    public void save(Session session) {
        redis.opsForValue()
                .set(
                        session.getSessionId(),
                        session,
                        SESSION_EXPIRE_TIME_IN_DAYS,
                        TimeUnit.DAYS
                );
    }

    public void delete(String uuid) {
        redis.opsForValue()
                .set(
                        uuid,
                        new Session(),
                        1,
                        TimeUnit.NANOSECONDS
                );
    }
}
