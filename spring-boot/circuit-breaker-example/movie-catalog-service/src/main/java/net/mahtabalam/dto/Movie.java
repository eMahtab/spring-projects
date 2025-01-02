package net.mahtabalam.dto;

import java.util.List;

public class Movie {
    private String id;
    private String title;
    private String overview;
    private List<Review> reviews;

    public Movie() {
    }

    public Movie(String id, String title, String overview) {
        this.id = id;
        this.title = title;
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public List<Review> getReviews(){
        return reviews;
    }
    public void setReviews(List<Review> reviews){
        this.reviews = reviews;
    }
}
