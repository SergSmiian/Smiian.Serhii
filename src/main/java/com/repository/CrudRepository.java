package com.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T vehicle);

    boolean saveAll(List<T> vehicle);

    boolean update(T vehicle);

    boolean delete(String id);
}
