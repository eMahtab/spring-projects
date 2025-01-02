package net.mahtabalam.model;

public class Movie {
    private String id;
    private String title;
    private String overview;

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
}
