package ru.coursework.akinator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.coursework.akinator.dto.ChatMessage;
import ru.coursework.akinator.service.GameQueue;
import ru.coursework.akinator.service.OneVsOneGameQueue;
import ru.coursework.akinator.service.OneVsPartyGameQueue;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class GreetingController {

	@Autowired
	private OneVsOneGameQueue oneVsOneGameQueue;

	@Autowired
	private OneVsPartyGameQueue oneVsPartyGameQueue;

	@MessageMapping("/add")
	public void addInQueue(Principal principal, ChatMessage message) {
		switch (message.getPlayerType()) {
			case AKINATOR:
				oneVsOneGameQueue.addAkinator(message.getSenderName());
				break;
			case SPHINX1:
				oneVsOneGameQueue.addSphinx(message.getSenderName());
				break;
			case PARTY:
				Set<String> akinators = new HashSet<String>(Arrays.asList(message.getSenderName().split(", ")));
				oneVsPartyGameQueue.addAkinatorParty(akinators);
				break;
			case SPHINX2:
				oneVsPartyGameQueue.addSphinx(message.getSenderName());
				break;
		}
	}

}
