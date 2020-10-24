package ru.coursework.akinator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.coursework.akinator.domain.Band;
import ru.coursework.akinator.domain.Role;
import ru.coursework.akinator.domain.User;
import ru.coursework.akinator.repos.BandRepo;
import ru.coursework.akinator.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/band")
public class BandController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BandRepo bandRepo;

    @GetMapping("{band}")
    public String playerProfile(@PathVariable Band band, Model model) {
        Iterable<User> players = userRepo.findTopByScore();
        model.addAttribute("topPlayers", players);

        Iterable<Band> bands = bandRepo.findAll(new Sort(Sort.Direction.DESC, "score"));
        model.addAttribute("topBands", bands);

        model.addAttribute("profile", band);

        List<User> members = new ArrayList<>(band.getMembers());
        for (int i = 1; i <= members.size(); i++) {
            model.addAttribute("member" + i, members.get(i-1));
        }

        return "bandProfile";
    }

    @GetMapping("/removeFromBand/{band}/{user}")
    public String removeFromBand(@PathVariable Band band, @PathVariable User user) {

        band.setNumber(band.getNumber() - 1);
        band.getMembers().remove(user);
        if (user.getBands().size() == 1) user.getRoles().remove(Role.MEMBER);
        user.getBands().remove(band);

        userRepo.save(user);
        bandRepo.save(band);

        return "redirect:/band/" + band.getId();
    }

    @GetMapping("/takeToTheBand/{user}/{band}")
    public String takeToTheBand(@PathVariable Band band, @PathVariable User user) {

        if (band.getNumber() == 9) return "redirect:/band/" + band.getId();

        band.getInvsToPlayers().remove(user);
        band.getUsersReqs().remove(user);
        user.getBandsInvs().remove(band);
        user.getReqsToBands().remove(band);

        user.getBands().add(band);
        band.getMembers().add(user);
        band.setNumber(band.getNumber() + 1);

        user.getRoles().add(Role.MEMBER);

        userRepo.save(user);

        return "redirect:/band/" + band.getId();
    }

    @GetMapping("/removeReqToTheBand/{user}/{band}")
    public String removeReqToTheBand(@PathVariable Band band, @PathVariable User user) {

        band.getUsersReqs().remove(user);
        user.getReqsToBands().remove(band);

        userRepo.save(user);
        bandRepo.save(band);

        return "redirect:/band/" + band.getId();
    }

    @PostMapping("/sendReqToTheBand/{user}/{band}")
    public String sendReqToTheBand(@PathVariable Band band, @PathVariable User user) {

        if (band.getUsersReqs().contains(user)) return "redirect:/band/" + band.getId();

        user.getReqsToBands().add(band);
        userRepo.save(user);
        band.getUsersReqs().add(user);
        bandRepo.save(band);

        return "redirect:/band/" + band.getId();
    }
}
