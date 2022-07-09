package com.repository;

import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean create(T object);

    boolean create(List<T> objects);

    boolean update(T object);

    boolean delete(String id);
}
