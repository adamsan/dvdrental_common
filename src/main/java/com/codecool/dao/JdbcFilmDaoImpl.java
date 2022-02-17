package com.codecool.dao;

import com.codecool.model.Film;

import java.sql.Connection;
import java.util.List;

public class JdbcFilmDaoImpl implements FilmDao {

    private Connection con;

    public JdbcFilmDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Film findById(Integer id) {
        return null;
    }

    @Override
    public List<Film> findAll() {
        return null;
    }

    @Override
    public void save(Film entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Film> findByRating(String rating) {
        return null;
    }

    @Override
    public List<Film> findByReleaseYear(int releaseYear) {
        return null;
    }
}
