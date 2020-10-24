package ru.coursework.akinator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GameController {

    @GetMapping("/game/{gameType}/{role}/{gameId}")
    public String chooseRole(@PathVariable String gameType, @PathVariable String role, @PathVariable String gameId, Model model) {
        if (role.equals("AKINATOR")) {
            model.addAttribute("role", 2);
        } else model.addAttribute("role", 1);
        model.addAttribute("gameId", gameId);
        model.addAttribute("bandname", "Компания");
        model.addAttribute("place", "");
        if (gameType.equals("ONE_VS_ONE")) {
            return "game1";
        } else return "game2";
    }

    @GetMapping("/game/2/PARTY/{bandname}/{gameId}/{place}")
    public String choosePartyRole(
            @PathVariable String bandname,
            @PathVariable String gameId,
            @PathVariable String place,
            Model model) {
        model.addAttribute("role", 2);
        model.addAttribute("gameId", gameId);
        model.addAttribute("bandname", bandname);
        model.addAttribute("place", place);
        return "game2";
    }
}
