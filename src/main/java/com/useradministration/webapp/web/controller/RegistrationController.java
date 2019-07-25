package com.useradministration.webapp.web.controller;


import com.useradministration.webapp.dao.entity.UserAccount;
import com.useradministration.webapp.dto.UserDto;
import com.useradministration.webapp.exception.NotValidPasswordException;
import com.useradministration.webapp.service.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.useradministration.webapp.web.urlPath.UrlPath.NEW;
import static com.useradministration.webapp.web.urlPath.UrlPath.USER;

@Slf4j
@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final UserService userService;

    @GetMapping(USER + NEW)
    public String getRegistrationPage() {
        return "new";
    }

    @PostMapping(USER + NEW)
    public String registration(Model model, UserDto dto) throws NotValidPasswordException {
        if (!dto.getUsernameQ().equals("")) {
            if (userService.getByLogin(dto.getUsernameQ()).isPresent()) {
                return "login";
            } else {
                UserAccount userAccount = new UserAccount();
                model.addAttribute("user", userService.register(dto, userAccount));
                return "view";
            }
        } else return "new";
    }

}