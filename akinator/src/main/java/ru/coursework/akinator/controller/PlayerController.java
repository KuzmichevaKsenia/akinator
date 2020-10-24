package ru.coursework.akinator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.coursework.akinator.domain.Band;
import ru.coursework.akinator.domain.Role;
import ru.coursework.akinator.domain.User;
import ru.coursework.akinator.repos.BandRepo;
import ru.coursework.akinator.repos.UserRepo;

import java.util.*;


@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BandRepo bandRepo;

    @GetMapping("{user}")
    public String playerProfile(@AuthenticationPrincipal User currentUser,
                                @PathVariable User user, Model model) {
        Iterable<User> players = userRepo.findTopByScore();
        model.addAttribute("topPlayers", players);

        Iterable<Band> bands = bandRepo.findAll(new Sort(Sort.Direction.DESC, "score"));
        model.addAttribute("topBands", bands);


        Iterable<Band> parentBands = bandRepo.findByParent(currentUser);
        model.addAttribute("parentBands", parentBands);
        model.addAttribute("profile", user);

        return "playerProfile";
    }

    @GetMapping("/joinTheBand/{band}")
    public String joinTheBand(@AuthenticationPrincipal User user, @PathVariable Band band, Model model) {

        if (band.getNumber() == 9) {
            return "redirect:/player/" + user.getId();
        }

        band.getInvsToPlayers().remove(user);
        band.getUsersReqs().remove(user);
        user.getBandsInvs().remove(band);
        user.getReqsToBands().remove(band);

        user.getBands().add(band);
        band.getMembers().add(user);
        band.setNumber(band.getNumber() + 1);

        user.getRoles().add(Role.MEMBER);

        userRepo.save(user);

        return "redirect:/player/" + user.getId();
    }

    @GetMapping("/removeInvToJoinTheBand/{band}")
    public String removeInvToJoinTheBand(@AuthenticationPrincipal User user, @PathVariable Band band) {

        band.getInvsToPlayers().remove(user);
        user.getBandsInvs().remove(band);

        userRepo.save(user);
        bandRepo.save(band);

        return "redirect:/player/" + user.getId();
    }

    @PostMapping("/addNewBand")
    public String addNewBand(@AuthenticationPrincipal User user,
                             @RequestParam String bandname,
                             Map<String, Object> model) {
        if (bandname == "" || bandname == null) return "redirect:/player/" + user.getId();
        Band bandFromDb = bandRepo.findByBandname(bandname);
        if (bandFromDb != null) return "redirect:/player/" + user.getId();

        user.getRoles().add(Role.PARENT);
        user.getRoles().add(Role.MEMBER);

        Band band = Band.builder()
                .bandname(bandname)
                .number(1)
                .score(0L)
                .parent(user)
                .build();

        band.getMembers().add(user);
        user.getBands().add(band);

        userRepo.save(user);
        return "redirect:/player/" + user.getId();
    }

    @PostMapping("/invitePlayerToBand/{user}")
    public String invitePlayerToBand(@RequestParam String parentBandname, @PathVariable User user) {

        Band parentBand = bandRepo.findByBandname(parentBandname);
        if (user.getBandsInvs().contains(parentBand) || user.getBands().contains(parentBand)) {
            return "redirect:/player/" + user.getId();
        }

        parentBand.getInvsToPlayers().add(user);
        user.getBandsInvs().add(parentBand);

        userRepo.save(user);

        return "redirect:/player/" + user.getId();
    }

}
