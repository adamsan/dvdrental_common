package com.codecool.dao;

import com.codecool.model.Film;
import com.codecool.model.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcFilmDaoImpl implements FilmDao {

    private Connection con;
    private RowMapper<Film> mapper = rs -> new Film(
            rs.getInt("film_id"),
            rs.getInt("release_year"),
            rs.getString("title"),
            rs.getString("description"),
            Rating.of(rs.getString("rating"))
    );

    public JdbcFilmDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Film findById(Integer id) {
        String sql = "select * from film where film_id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapper.map(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public void save(Film film) {
        if (film.getId() == null) insert(film);
        else update(film);
    }

    private void update(Film film) {
        String sql = """
                update film set
                title = ?,
                description = ?,
                release_year = ?,
                rating = ?::mpaa_rating
                where film_id = ?
                """;
        try (var ps = con.prepareStatement(sql)) {
            ps.setString(1, film.getTitle());
            ps.setString(2, film.getDescription());
            ps.setInt(3, film.getReleaseYear());
            ps.setString(4, film.getRating().toString());
            ps.setInt(5, film.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insert(Film film) {
        String sql = """
                insert into film 
                (title, description, release_year, rating, language_id) 
                values(?,?,?,?::mpaa_rating, 1)
                """;
        try (var ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, film.getTitle());
            ps.setString(2, film.getDescription());
            ps.setInt(3, film.getReleaseYear());
            ps.setString(4, film.getRating().toString());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                film.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Film> findByRating(Rating rating) {
        try (var ps = con.prepareStatement("select * from film where rating=?::mpaa_rating")) {
            ps.setString(1, rating.toString());
            ResultSet rs = ps.executeQuery();
            List<Film> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Film> findByReleaseYear(int releaseYear) {
        try (var ps = con.prepareStatement("select * from film where release_year=?")) {
            ps.setInt(1, releaseYear);
            ResultSet rs = ps.executeQuery();
            List<Film> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
