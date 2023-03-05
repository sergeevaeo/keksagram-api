package edu.keksagram.api;

import edu.keksagram.model.AppUser;
import edu.keksagram.model.Post;
import edu.keksagram.model.PostRepository;
import edu.keksagram.model.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/users")
@RestController
public class UserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping// добавление пользователя
    public AppUser createUser(@RequestParam String username, @RequestParam String password) {
        AppUser oneUser = new AppUser();
        oneUser.setUsername(username);
        oneUser.setPassword(password);
        appUserRepository.save(oneUser);
        return oneUser;
    }

    @GetMapping // список всех пользователей
    public Iterable<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @DeleteMapping("/delete/{id}") // удаление пользователя
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            appUserRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getById/{id}") // поиск пользователя по id
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        Optional<AppUser> user = appUserRepository.findUserById(id);
        return user.map(appUser -> new ResponseEntity<>(appUser, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/update/{id}") // обновление данных о пользователе
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestParam String username, @RequestParam String password) {
        Optional<AppUser> optionalUser = appUserRepository.findUserById(id);
        if (optionalUser.isPresent()) {
            AppUser existingUser = optionalUser.get();
            existingUser.setUsername(username);
            existingUser.setPassword(password);
            AppUser updatedUser = appUserRepository.save(existingUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createPost/{id}") // добавление поста пользователя
    public ResponseEntity<List<Post>> createPost(@PathVariable Long id, @RequestParam String urlString,
                                                 @RequestParam String caption, @RequestParam String hashtag) {
        Optional<AppUser> optionalUser = appUserRepository.findUserById(id); // находим юзера с этим id
        Post onePost = new Post(); // создаем пост
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (optionalUser.isPresent()) {  // если такой юзер существует
            onePost.setUrl(url); // url
            onePost.setPostOwner(optionalUser.get()); // посту присваиваем владельца
            onePost.setCaption(caption); // описание
            onePost.setHashtags(hashtag); // хэштэг
            Post savedPost = postRepository.save(onePost); // сохраняем пост
            AppUser existingUser = optionalUser.get();
            existingUser.getPosts().add(savedPost); // добавляем пост
            AppUser updatedUser = appUserRepository.save(existingUser); //сохраняем пользователя
            return new ResponseEntity<>(updatedUser.getPosts(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPosts/{id}") // вывод всех постов пользователя
    public ResponseEntity<List<Post>> getUsersPosts(@PathVariable Long id) {
        Optional<AppUser> user = appUserRepository.findUserById(id);
        return user.map(appUser -> new ResponseEntity<>(appUser.getPosts(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }





}