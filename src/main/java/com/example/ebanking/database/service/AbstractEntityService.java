package com.example.ebanking.database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractEntityService<E, ID, R extends JpaRepository<E, ID>> {
    protected final R repository;

    public AbstractEntityService(R repository) {
        this.repository = repository;
    }

    @Transactional
    public E save(E entity) {
        repository.save(entity);
        log.debug("Saved {}", entity);
        return entity;
    }

    @Transactional
    public void saveAll(Iterable<E> entity) {
        repository.saveAll(entity);
    }

    @Transactional
    public E delete(E entity) {
        repository.delete(entity);
        log.debug("Deleted {}", entity);
        return entity;
    }

    public Optional<E> findById(ID id) {
        if (id == null)
            return Optional.empty();
        return repository.findById(id);
    }

    public boolean existsById(ID id) {
        return id != null && repository.existsById(id);
    }

    public List<E> findAll() {
        return repository.findAll();
    }
}
