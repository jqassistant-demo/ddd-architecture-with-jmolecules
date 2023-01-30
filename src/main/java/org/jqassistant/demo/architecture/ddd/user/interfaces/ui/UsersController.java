package org.jqassistant.demo.architecture.ddd.user.interfaces.ui;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.jqassistant.demo.architecture.ddd.user.application.UserApplicationService;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ui/users")
public class UsersController {

    private final UserApplicationService userApplicationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userApplicationService.findAll());
        return "users/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("user", User.builder()
                .build());
        return "users/edit";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        return userApplicationService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "users/edit";
                })
                .orElse(list(model));
    }

    @PostMapping("{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        userApplicationService.delete(id);
        return list(model);
    }

    @PostMapping("save")
    public String save(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userApplicationService.save(user);
        }
        return list(model);
    }

}
