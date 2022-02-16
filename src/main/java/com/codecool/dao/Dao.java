package com.codecool.dao;

import java.util.List;

public interface Dao<T> {
    T findById(Integer id);

    List<T> findAll();

    void save(T entity);

    void deleteById(Integer id);
}
