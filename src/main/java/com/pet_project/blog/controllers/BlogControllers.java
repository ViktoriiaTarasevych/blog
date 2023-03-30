package com.pet_project.blog.controllers;

import com.pet_project.blog.models.Post;
import com.pet_project.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogControllers {

    @Autowired
    private PostRepository postRepository;

    @GetMapping ("/blog")
    public String blogMain (Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping ("/blog-add")
    public String blogAdd (Model model) {
        return "blog-add";
    }
    @PostMapping("/blog-add")
    public String blogPostAdd (@RequestParam String title, @RequestParam String anons,
                               @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post); // збереження
        return "redirect:/blog";
    }
}
