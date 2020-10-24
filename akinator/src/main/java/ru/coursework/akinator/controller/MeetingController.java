package ru.coursework.akinator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.coursework.akinator.domain.Band;
import ru.coursework.akinator.domain.User;
import ru.coursework.akinator.dto.ChatMessage;
import ru.coursework.akinator.repos.BandRepo;
import ru.coursework.akinator.repos.UserRepo;

@Controller
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private BandRepo bandRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("{band}")
    public String meeting(@PathVariable Band band, Model model) {
        model.addAttribute("band", band);
        return "meeting";
    }

    @MessageMapping("/meeting/{bandId}/addUser")
    @SendTo("/topic/public/{bandId}")
    public ChatMessage addUser(@DestinationVariable String bandId, @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("userId", chatMessage.getSenderId());
        return chatMessage;
    }

    @MessageMapping("/meeting/{bandId}/removeUser")
    @SendTo("/topic/public/{bandId}")
    public ChatMessage removeUser(@DestinationVariable String bandId, @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().remove("userId", chatMessage.getSenderId());
        return chatMessage;
    }

    @MessageMapping("/meeting/{bandId}/startTheGame")
    @SendTo("/topic/public/{bandId}")
    public ChatMessage startTheGame(@Payload ChatMessage chatMessage) {
        Band band = bandRepo.findByBandname(chatMessage.getBandname());
        for (User member : band.getMembers()) {
            member.getSeanceInvs().remove(band);
            userRepo.save(member);
        }
        band.getSeanceInvs().clear();
        bandRepo.save(band);
        return chatMessage;
    }
}
