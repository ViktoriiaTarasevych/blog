package com.pet_project.blog.controllers;

import com.pet_project.blog.models.Post;
import com.pet_project.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogControllers {

    @Autowired
    private PostRepository postRepository; // залежність

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog-add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog-add") // додавання поста
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,
                              @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post); // збереження
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}") // вивод потрібного посту
    public String blogDetails(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>(); // щоб було простіше перекидуємо в arraylist все
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit") // редагування
    public String blogEdit(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit") // додавання поста
    public String blogPostUpdate(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String anons,
                                 @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove") // видалення поста
    public String blogPostDelete(@PathVariable(value = "id") Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

    @PutMapping("/blog/{id}/views") //кількість переглядів
    public String viewsPost(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post currentPost = post.get();
            currentPost.setViews(currentPost.getViews()+1);
           // currentPost.incrementViews();
            postRepository.save(currentPost);
            model.addAttribute("post", currentPost);
        }
        return "blog-details";
    }
}
