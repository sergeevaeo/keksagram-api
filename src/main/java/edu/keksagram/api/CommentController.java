package edu.keksagram.api;

import edu.keksagram.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/comments")
@RestController
public class CommentController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/createComment/{userId}/{postId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long userId, @PathVariable Long postId, @RequestParam String text) {
        Optional<Post> optionalPost = postRepository.findPostById(postId); // находим id поста
        Optional<AppUser> optionalUser = appUserRepository.findUserById(userId); // находим юзера, который пишет коммент
        Comment comment = new Comment(); // создаем коммент
        if (optionalPost.isPresent() && optionalUser.isPresent()) {
            Post post = optionalPost.get();
            AppUser user = optionalUser.get();
            comment.setCommentPost(post);
            comment.setCommentOwner(user);
            comment.setText(text);
            List<Comment> newComments = new ArrayList<>(post.getComments());
            newComments.add(comment);
            post.setComments(newComments);

            int numberComments = post.getCommentCount() + 1;
            post.setCommentCount(numberComments);

            postRepository.save(post);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
