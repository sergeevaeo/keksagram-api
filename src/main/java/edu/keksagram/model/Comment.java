package edu.keksagram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 100)
    private String text;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser commentOwner;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Post commentPost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AppUser getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(AppUser commentOwner) {
        this.commentOwner = commentOwner;
    }

    public Post getCommentPost() {
        return commentPost;
    }

    public void setCommentPost(Post commentPost) {
        this.commentPost = commentPost;
    }


}
