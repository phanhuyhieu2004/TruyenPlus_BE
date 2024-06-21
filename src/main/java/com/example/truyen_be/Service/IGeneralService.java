package com.example.truyen_be.Service;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGeneralService<E> {
    Iterable<E> findAll();

    Optional<E> findById(Long id);

    E save(E e);

    void remove(Long id);

}