package edu.keksagram.api;

import edu.keksagram.model.Post;
import edu.keksagram.model.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping(value = "/posts")
@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping // список всех постов
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping ("/random")// список всех постов в случайном порядке
    public Iterable<Post> getAllPostsRandom() {
        Iterable<Post> posts = postRepository.findAll();
        List<Post> shuffledPosts = StreamSupport.stream(posts.spliterator(), false)
                .collect(Collectors.toList());
        Collections.shuffle(shuffledPosts);
        return shuffledPosts;
    }

    @GetMapping("/popular")
    public List<Post> getPostsPopular() {
        Sort sort = Sort.by("commentCount").descending();
        return postRepository.findAll(sort);
    }

     @PostMapping("/like/{id}")
    public ResponseEntity<Integer> like(@PathVariable Long id) {
         Optional<Post> optionalPost = postRepository.findPostById(id);
         if (optionalPost.isPresent()) {
             Post post = optionalPost.get();
             int numberOfLikes = post.getLikes() + 1;
             post.setLikes(numberOfLikes);
             postRepository.save(post);
             return new ResponseEntity<>(numberOfLikes, HttpStatus.OK);
         } else {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
     }
}
