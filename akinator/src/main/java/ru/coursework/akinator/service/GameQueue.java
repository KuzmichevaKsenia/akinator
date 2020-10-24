package ru.coursework.akinator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.coursework.akinator.dto.ChatMessage;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class GameQueue {
    protected Set<String> sphinxes = ConcurrentHashMap.newKeySet();
    protected Set<Set<String>> akinatorParties = ConcurrentHashMap.newKeySet();

    private AtomicLong gameId = new AtomicLong(0);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    protected abstract ChatMessage.GameType gameType();

    protected void addAkinatorParty(Set<String> akinatorParty) {
        akinatorParties.add(akinatorParty);
    }

    public void addSphinx(String sphinxName) {
        sphinxes.add(sphinxName);
    }

    public final synchronized void createChat() {
        while (!akinatorParties.isEmpty() && !sphinxes.isEmpty()) {
            Iterator<String> sphinxIterator = sphinxes.iterator();
            Iterator<Set<String>> akinatorPartiesIterator = akinatorParties.iterator();

            String sphinx = sphinxIterator.next();
            Set<String> akinatorParty = akinatorPartiesIterator.next();

            sphinxIterator.remove();
            akinatorPartiesIterator.remove();

            long curGameId = gameId.incrementAndGet();
            ChatMessage gameCreatedMessage = new ChatMessage();
            gameCreatedMessage.setGameId(curGameId);
            gameCreatedMessage.setType(ChatMessage.MessageType.GAME_CREATED);
            gameCreatedMessage.setGameType(gameType());

            messagingTemplate.convertAndSendToUser(sphinx, "/queue/reply", gameCreatedMessage);
            akinatorParty.forEach(akinatorName ->
                    messagingTemplate.convertAndSendToUser(
                            akinatorName,
                            "/queue/reply",
                            gameCreatedMessage));
        }
    }

}
