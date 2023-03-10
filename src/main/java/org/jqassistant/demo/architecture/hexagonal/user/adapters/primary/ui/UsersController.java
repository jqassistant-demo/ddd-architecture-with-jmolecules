package org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.ui;

import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.jqassistant.demo.architecture.hexagonal.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Implementation of a controller for the Thymeleaf based UI.
 */
@PrimaryAdapter
@Controller
@RequestMapping("/ui/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserApplicationService userApplicationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userApplicationService.findAll());
        return "users/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "users/edit";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        User user = userApplicationService.findById(id);
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        userApplicationService.delete(id);
        return list(model);
    }

    @PostMapping("save")
    public String save(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userApplicationService.create(user);
        }
        return list(model);
    }

}
