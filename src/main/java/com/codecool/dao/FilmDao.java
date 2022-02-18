package com.codecool.dao;

import com.codecool.model.Film;
import com.codecool.model.Rating;

import java.util.List;

public interface FilmDao extends Dao<Film> {
    List<Film> findByRating(Rating rating);

    List<Film> findByReleaseYear(int releaseYear);
}
