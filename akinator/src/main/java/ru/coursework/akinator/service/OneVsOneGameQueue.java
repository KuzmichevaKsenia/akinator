package ru.coursework.akinator.service;

import org.springframework.stereotype.Service;
import ru.coursework.akinator.dto.ChatMessage;

import java.util.Collections;

@Service
public class OneVsOneGameQueue extends GameQueue {
    public void addAkinator(String akinatorName) {
        addAkinatorParty(Collections.singleton(akinatorName));
    }

    @Override
    protected ChatMessage.GameType gameType() {
        return ChatMessage.GameType.ONE_VS_ONE;
    }
}
