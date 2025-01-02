package net.mahtabalam.dto;

import java.util.List;

public class Catalog {
    private String name;
    private String description;
    private List<Movie> movies;

    public Catalog() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Movie> getMovies(){
        return movies;
    }

    public void setMovies(List<Movie> movies){
        this.movies = movies;
    }
}
