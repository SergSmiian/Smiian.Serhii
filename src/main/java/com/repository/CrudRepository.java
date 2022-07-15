package com.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T auto);

    boolean saveAll(List<T> auto);

    boolean update(T auto);

    boolean delete(String id);
}
