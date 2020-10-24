package ru.coursework.akinator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.coursework.akinator.domain.Role;
import ru.coursework.akinator.domain.User;
import ru.coursework.akinator.repos.UserRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${repoimages.path}")
    private String repoimagesPath;

    @GetMapping("/registration")
    public String registration() {
        return "login";
    }

    @PostMapping("/registration")
    public String addUser(User user,
                          @RequestParam("file") MultipartFile file,
                          Map<String, Object> model) throws IOException {

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            User userFromDb = userRepo.findByUsername(user.getUsername());
            if (userFromDb != null) {
                model.put("message", "<div class=\"cloud\"><p class=\"text-danger\">Игрок с таким логином уже зарегистрирован!</p></div>");
                return "login";
            }

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            user.setAvatar(resultFilename);
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setScore((long) 0);
        userRepo.save(user);
        model.put("message", "<div class=\"cloud\"><p class=\"text-success\">Новый игрок успешно зарегистрирован</p></div>");

        return "login";
    }

    @PostMapping("/loginAsGuest")
    public String loginAsGuest(HttpServletRequest request) throws ServletException {
        String uuid = UUID.randomUUID().toString();
        String guestUserName = "Гость " + uuid.substring(9, 13);

        String guestPassword = UUID.randomUUID().toString();

        User guest = User.builder()
                .username(guestUserName)
                .password(guestPassword)
                .active(true)
                .avatar(repoimagesPath + "guest.png")
                .roles(Collections.singleton(Role.GUEST))
                .build();

        userRepo.save(guest);

        request.login(guestUserName, guestPassword);
        return "redirect:/main";
    }
}