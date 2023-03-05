package edu.keksagram.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<Post> findPostById(Long id);
    List<Post> findAll(Sort sort);
}