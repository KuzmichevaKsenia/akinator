package ru.coursework.akinator.controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.coursework.akinator.domain.Band;
import ru.coursework.akinator.domain.User;
import ru.coursework.akinator.repos.BandRepo;
import ru.coursework.akinator.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BandRepo bandRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<User> players = userRepo.findTopByScore();
        model.addAttribute("topPlayers", players);

        Iterable<Band> bands = bandRepo.findAll(new Sort(Sort.Direction.DESC, "score"));
        model.addAttribute("topBands", bands);

        return "main";
    }

    @PostMapping("/main/invBandToSeance")
    public String invitePlayerToBand(@RequestParam String bandname, Model model) {

        Band band = bandRepo.findByBandname(bandname);
        if (band.getSeanceInvs().size() == 0) {
            Set<User> members = band.getMembers();
            band.getSeanceInvs().addAll(members);

            for (User member : members) {
                member.getSeanceInvs().add(band);
            }
            bandRepo.save(band);
        }

        return "redirect:/meeting/" + band.getId();
    }

}