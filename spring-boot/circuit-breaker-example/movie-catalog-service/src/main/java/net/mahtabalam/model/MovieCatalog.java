package net.mahtabalam.model;

import java.util.List;

public class MovieCatalog {
    private String name;
    private String description;
    private List<String> movieIds;

    public MovieCatalog() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMovieIds(List<String> movieIds) {
        this.movieIds = movieIds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getMovieIds() {
        return movieIds;
    }
}
