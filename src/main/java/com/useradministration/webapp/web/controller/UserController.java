package com.useradministration.webapp.web.controller;

import com.useradministration.webapp.dao.entity.UserAccount;
import com.useradministration.webapp.dao.entity.enumeration.Role;
import com.useradministration.webapp.dto.UserDto;
import com.useradministration.webapp.dto.UserSearchDto;
import com.useradministration.webapp.service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.ConstraintViolationException;

import static com.useradministration.webapp.web.urlPath.UrlPath.EDIT;
import static com.useradministration.webapp.web.urlPath.UrlPath.SAVE;
import static com.useradministration.webapp.web.urlPath.UrlPath.SEARCH;
import static com.useradministration.webapp.web.urlPath.UrlPath.USER;

@Controller
@SessionAttributes({"currentUser"})
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @ModelAttribute("allRoles")
    public Role[] accountTypes() {
        return Role.values();
    }

    @GetMapping(USER)
    public String getUsers(Model model, Integer nextPage) {
        if (nextPage == null) {
            nextPage = 0;
        }
        Page<UserAccount> all = userService.getAll(nextPage);

        if (all.hasPrevious()) {
            model.addAttribute("previousPage", all.previousPageable().getPageNumber());
        }

        if (all.hasNext()) {
            model.addAttribute("nextPage", all.nextPageable().getPageNumber());
        }
        model.addAttribute("users", all);
        return "list";
    }

    @PostMapping(USER + SEARCH)
    public String searchUsers(Model model, UserSearchDto dto) {
        model.addAttribute("users", userService.getWithFilters(dto));

        return "list";
    }

    @GetMapping(USER + "/{id}")
    public String userDetails(Model model, @PathVariable Long id) {
        if (userService.getById(id).isPresent()) {
            model.addAttribute("user", userService.getById(id).get());
        }
        return "view";
    }

    @GetMapping(USER + "/{id}" + EDIT)
    public String editUser(Model model, @PathVariable Long id) {
        if (userService.getById(id).isPresent()) {
            model.addAttribute("currentUser", userService.getById(id).get());
        }
        return "edit";
    }

    @PostMapping(USER + SAVE)
    public String saveUser(Model model, UserDto dto, @ModelAttribute("currentUser") UserAccount account) throws ConstraintViolationException {
        model.addAttribute("user", userService.saveOrUpdate(dto, account));
        return "view";
    }

    @GetMapping("/accessDeniedPage")
    public String accessDeniedPage() {
        return "accessDeniedPage";
    }

}
