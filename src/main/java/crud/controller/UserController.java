package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String auth() {
        return "/authentication";
    }

    //Юзерские страницы

    @RequestMapping(value = "/user/home", method = RequestMethod.GET)
    public String UserProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        return "/user/profile";
    }

    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.GET)
    public String userUpdateGet(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));

        return "/user/update";
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String userUpdatePost(@ModelAttribute("user") User user) {
        userService.updateUser(user);

        return "/user/profile";
    }

    //Админские страницы
    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("userList", userService.getAllUsers());

        return "/admin/users";
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.POST)
    public String deleteUser(@PathVariable("id") int id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("userList", userService.getAllUsers());

        return "/admin/users";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public String addUserGet(Model model) {
        model.addAttribute("user", new User());

        return "/admin/add";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") User user, @ModelAttribute("access") String access, Model model) {
        userService.addUser(user, access);
        model.addAttribute("userList", userService.getAllUsers());

        return "/admin/users";
    }

    @RequestMapping(value = "/admin/update/{id}", method = RequestMethod.GET)
    public String updateUserGet(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));

        return "/admin/update";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String updateUserPost(@ModelAttribute("user") User user, Model model) {
        userService.updateUser(user);
        model.addAttribute("userList", userService.getAllUsers());

        return "/admin/users";
    }


}
