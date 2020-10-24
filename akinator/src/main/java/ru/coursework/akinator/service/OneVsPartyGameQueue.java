package ru.coursework.akinator.service;

import org.springframework.stereotype.Service;
import ru.coursework.akinator.dto.ChatMessage;

import java.util.Set;

@Service
public class OneVsPartyGameQueue extends GameQueue {
    @Override
    protected ChatMessage.GameType gameType() {
        return ChatMessage.GameType.ONE_VS_PARTY;
    }

    public void addAkinatorParty(Set<String> akinatorParty) {
        super.addAkinatorParty(akinatorParty);
    }
}
