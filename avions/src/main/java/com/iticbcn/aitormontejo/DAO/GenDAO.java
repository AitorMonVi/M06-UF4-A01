package com.iticbcn.aitormontejo.DAO;

import java.util.List;

public interface GenDAO<T> {
    // SELECT entidad
    T get(int id);
    
    // SELECT *
    List<T> getAll();

    // CREATE
    void save(T t);

    // UPDATE
    void update(T t);

    // DELETE
    void delete(T t);
}
