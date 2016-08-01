package com.example.junior.test_token_video;

/**
 * Created by junior on 13/07/16.
 */
public class Post {
    private String id;
    private String title;
    private String description;
    String media;
    private String category;

    public Post(){

    }

    public Post(String title, String description, String category, String media){
        this.setTitle(title);
        this.setDescription(description);
        this.setMedia(media);
        this.setCategory(category);
    }

    public Post(String title, String description, String category){
        this.setTitle(title);
        this.setDescription(description);
        this.setMedia(null);
        this.setCategory(category);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
