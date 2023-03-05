package edu.keksagram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private URL url;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser postOwner;

    @Size(max = 100)
    private String caption;

    @Pattern(regexp = "^#\\w+(?:_\\w+)*$")
    private String hashtags;

    @OneToMany(mappedBy = "commentPost", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    private int likes;

    private int commentCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public AppUser getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(AppUser postOwner) {
        this.postOwner = postOwner;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }


    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}