package com.adesso.project.awesomepizza.service;

import com.adesso.project.awesomepizza.entity.Order;

import java.util.List;

public interface GeneralService<T, ID> {

    // CREATE
    T create(T entity);

    // CREATE
    Order create(Order order);

    // READ
    T findById(ID id);

    List<T> findAll();

    // UPDATE
    T update(ID id, T updatedEntity);

    // DELETE
    void delete(ID id);
}
