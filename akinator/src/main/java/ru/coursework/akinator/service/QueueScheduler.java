package ru.coursework.akinator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QueueScheduler {

    @Autowired
    private OneVsOneGameQueue oneVsOneGameQueue;

    @Autowired
    private  OneVsPartyGameQueue oneVsPartyGameQueue;

    @Scheduled(fixedRate = 2000)
    public void reportCurrentTime() {
        oneVsOneGameQueue.createChat();
        oneVsPartyGameQueue.createChat();
    }
}
