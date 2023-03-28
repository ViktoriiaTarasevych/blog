package com.pet_project.blog.repositories;

import com.pet_project.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository <Post, Long> {

}
