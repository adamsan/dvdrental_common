package com.codecool.model;

public class Film {
    private Integer id;
    private Integer releaseYear;
    private String title;
    private String description;
    private Rating rating;

    public Film(Integer id, Integer releaseYear, String title, String description, Rating rating) {
        this.id = id;
        this.releaseYear = releaseYear;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public Film setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Film setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Film setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Film setDescription(String description) {
        this.description = description;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public Film setRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", releaseYear=" + releaseYear +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
