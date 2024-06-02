package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserServiceImpl userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = (UserServiceImpl) userService;
        this.roleService = roleService;
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String showEditUserForm(@RequestParam("id") long id, ModelMap model) {
        Optional<User> user = userService.getUserById(id);
        model.addAttribute("user", user.get());
        return "edit_user";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "")
    public String allUsers(ModelMap model, @ModelAttribute("user") User user) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "web";
    }

    // @PostMapping("/update")
    // public String updateUser(@ModelAttribute User user) {
    //     userService.saveUser(user);
    //     return "redirect:/admin";
    // }
}
