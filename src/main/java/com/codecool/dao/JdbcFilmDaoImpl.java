package com.codecool.dao;

import com.codecool.model.Film;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcFilmDaoImpl implements FilmDao {

    private Connection con;
    private RowMapper<Film> mapper = rs -> new Film(
            rs.getInt("film_id"),
            rs.getInt("release_year"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("rating")
    );

    public JdbcFilmDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Film findById(Integer id) {
        return null;
    }

    @Override
    public List<Film> findAll() {
        List<Film> results = new ArrayList<>();
        try (var st = con.createStatement()) {
            ResultSet rs = st.executeQuery("select * from film");
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
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
