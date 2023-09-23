package com.abuabied.teamUp.Helpers;

import com.abuabied.teamUp.Entities.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionsHelper {
    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public int addSession(Session session){
        return 0;
    }
}
