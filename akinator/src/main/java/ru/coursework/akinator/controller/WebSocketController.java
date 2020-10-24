package ru.coursework.akinator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.coursework.akinator.domain.Band;
import ru.coursework.akinator.domain.Characters;
import ru.coursework.akinator.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.coursework.akinator.domain.User;
import ru.coursework.akinator.repos.BandRepo;
import ru.coursework.akinator.repos.CharactersRepo;
import ru.coursework.akinator.repos.UserRepo;

import java.util.List;

@Controller
public class WebSocketController {
    @Autowired
    private CharactersRepo characterRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BandRepo bandRepo;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("userId", chatMessage.getSenderId());
        return chatMessage;
    }

    @MessageMapping("/chat.sendEndData")
    @SendTo("/topic/public")
    public ChatMessage sendEndData(@Payload ChatMessage chatMessage) {
        Long sphinxScore;
        Long akinatorScore;

        User sphinx = userRepo.findByUsername(chatMessage.getSphinxName());
        User akinator = userRepo.findByUsername(chatMessage.getAkinatorName());

        List<Characters> charactersFromDB = characterRepo.findByCharname(chatMessage.getCharacterName());

        if (charactersFromDB.isEmpty()) {
            chatMessage.setCharacterRepeat("0");
        } else chatMessage.setCharacterRepeat(charactersFromDB.get(0).getRepeats().toString());

        if (chatMessage.getGuessed().equals("true")) {
            chatMessage.setGuessed("Да");
            if (charactersFromDB.isEmpty()) {
                Characters character = new Characters(chatMessage.getCharacterName());
                characterRepo.save(character);
                sphinxScore = 20L;
                akinatorScore = 20L;
            } else {
                for (Characters character : charactersFromDB) {
                    character.setRepeats(character.getRepeats() + 1);
                    characterRepo.save(character);
                }
                sphinxScore = 10L;
                akinatorScore = 10L;
            }

        } else {
            chatMessage.setGuessed("Нет");
            if (chatMessage.getCauser().equals("1")) {
                sphinxScore = 0L;
                akinatorScore = 5L;
            } else if (chatMessage.getCauser().equals("2")) {
                sphinxScore = 5L;
                akinatorScore = 0L;
            } else {
                sphinxScore = 0L;
                akinatorScore = 0L;
            }
        }

        if (akinator.isPlayer()) {
            akinator.setScore(akinator.getScore() + akinatorScore);
            userRepo.save(akinator);
            chatMessage.setAkinatorScore(akinatorScore.toString());
        } else chatMessage.setAkinatorScore("-");

        if (sphinx.isPlayer()) {
            sphinx.setScore(sphinx.getScore() + sphinxScore);
            userRepo.save(sphinx);
            chatMessage.setSphinxScore(sphinxScore.toString());
        } else chatMessage.setSphinxScore("-");

/*       Set<User> users = new HashSet<>();
       users.add(akinator);
       users.add(sphinx);
        userRepo.saveAll(users);*/

        return chatMessage;
    }

    @MessageMapping("/chat.sendEndData2")
    @SendTo("/topic/public")
    public ChatMessage sendEndData2(@Payload ChatMessage chatMessage) {
        Long sphinxScore = 0L;
        Long akinatorsScore = 0L;
        Long winnerAkinatorScore = 0L;
        Long bandScore = 0L;

        User winnerAkinator;
        Band band = bandRepo.findByBandname(chatMessage.getBandname());
        User sphinx = userRepo.findByUsername(chatMessage.getSphinxName());

        List<Characters> charactersFromDB = characterRepo.findByCharname(chatMessage.getCharacterName());

        if (charactersFromDB.isEmpty()) {
            chatMessage.setCharacterRepeat("0");
        } else chatMessage.setCharacterRepeat(charactersFromDB.get(0).getRepeats().toString());

        if (chatMessage.getGuessed().equals("true")) {
            chatMessage.setGuessed("Да");
            if (charactersFromDB.isEmpty()) {
                Characters character = new Characters(chatMessage.getCharacterName());
                characterRepo.save(character);
                sphinxScore = 30L;
                winnerAkinatorScore = 10L;
                akinatorsScore = 20L;
                bandScore = 20L;
            } else {
                for (Characters character : charactersFromDB) {
                    character.setRepeats(character.getRepeats() + 1);
                    characterRepo.save(character);
                }
                sphinxScore = 20L;
                winnerAkinatorScore = 10L;
                akinatorsScore = 10L;
                bandScore = 10L;
            }
            winnerAkinator = userRepo.findByUsername(chatMessage.getCauser());
            if (winnerAkinator != null) {
                winnerAkinator.setScore(winnerAkinator.getScore() + winnerAkinatorScore);
                userRepo.save(winnerAkinator);
            }

        } else {
            chatMessage.setGuessed("Нет");
            if (chatMessage.getCauser().equals("1")) {
                akinatorsScore = 5L;
                bandScore = 5L;
            } else if (chatMessage.getCauser().equals("2")) {
                sphinxScore = 5L;
            }
        }

        if (sphinx.isPlayer()) {
            sphinx.setScore(sphinx.getScore() + sphinxScore);
            userRepo.save(sphinx);
            chatMessage.setSphinxScore(sphinxScore.toString());
        } else chatMessage.setSphinxScore("-");

        for (String akinatorName : chatMessage.getAkinatorsName().split(",")) {
            User akinator = userRepo.findByUsername(akinatorName);
            if (akinator != null) {
                akinator.setScore(akinator.getScore() + akinatorsScore);
                userRepo.save(akinator);
            }
        }

        if (band != null) {
            band.setScore(band.getScore() + bandScore);
            bandRepo.save(band);
        }

        chatMessage.setAkinatorsScore(akinatorsScore.toString());
        chatMessage.setBandScore(bandScore.toString());
        Long sum = winnerAkinatorScore + akinatorsScore;
        chatMessage.setWinnerAkinatorScore(sum.toString());

        return chatMessage;
    }

}