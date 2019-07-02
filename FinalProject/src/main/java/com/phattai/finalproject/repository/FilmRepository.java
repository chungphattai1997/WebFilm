package com.phattai.finalproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.phattai.finalproject.model.Film;

public interface FilmRepository extends CrudRepository<Film, Long> {

}
