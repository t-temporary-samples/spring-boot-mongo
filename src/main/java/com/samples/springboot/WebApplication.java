package com.samples.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public @ResponseBody
    String root() {
        return "<body>" +
                "<p><a href=\"user\">/user</a>: List all users</p>" +
                "<p><a href=\"user/add\">/user/add?name=***</a>: Add a user with name ***</p>" +
                "<p><a href=\"user/delete\">/user/delete?id=***</a>: Delete a user with id ***</p>" +
                "</body>";
    }

    @RequestMapping("/user")
    public @ResponseBody
    List<User> users() {
        return this.userRepository.findAll();
    }

    @RequestMapping("/user/add")
    public @ResponseBody User addUser(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        return userRepository.save(user);
    }

    @RequestMapping("/user/delete")
    public @ResponseBody String deleteUser(@RequestParam String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return "Deleted";
        } else {
            return "NotFound";
        }
    }
}