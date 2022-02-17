package com.codecool.dao;

import com.codecool.model.Film;

import java.util.List;

public interface FilmDao extends Dao<Film> {
    List<Film> findByRating(String rating);

    List<Film> findByReleaseYear(int releaseYear);
}
