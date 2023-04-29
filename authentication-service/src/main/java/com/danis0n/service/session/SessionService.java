package com.danis0n.service.session;

import com.danis0n.entity.Session;
import com.danis0n.repository.SessionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionDao sessionDao;

    public void save(String uuid, String username) {
        Session session = new Session(uuid, username);
        sessionDao.save(session);

    }

    public void delete(String uuid) {
        sessionDao.delete(uuid);
    }

}
