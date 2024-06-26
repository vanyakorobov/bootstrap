package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("error", "User not found");
        }
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit_user";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "")
    public String allUsers(ModelMap model, Authentication auth) {
        List<User> users = userService.getAllUsers();
        User user = userService.findByUsername(auth.getName());
        System.out.println(user);
        model.addAttribute("listOfUsers", users);
        model.addAttribute("user", user);
        return "web";
    }

     @PostMapping("/update")
     public String updateUser(@ModelAttribute User user) {
         userService.saveUser(user);
         return "redirect:/admin";
     }
}
