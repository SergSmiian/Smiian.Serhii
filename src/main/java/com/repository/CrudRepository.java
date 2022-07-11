package com.repository;

import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean save(T auto);

    boolean saveAll(List<T> auto);

    boolean update(T auto);

    boolean delete(String id);
}
