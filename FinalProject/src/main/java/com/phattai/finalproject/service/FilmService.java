package com.phattai.finalproject.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phattai.finalproject.dto.FilmDTO;
import com.phattai.finalproject.model.Category;
import com.phattai.finalproject.model.Film;
import com.phattai.finalproject.repository.FilmRepository;

@Service
public class FilmService {

	@Autowired
	FilmRepository filmRepository;

	// Find all film
	public Iterable<Film> findAll() {
		return filmRepository.findAll();
	}

	// Find film by id
	public Film findById(long id) {
		Optional<Film> film = filmRepository.findById(id);
		if (!film.isPresent()) {
			return null;
		}
		return film.get();
	}

	// Add or update film
	public void save(Film film) {
		filmRepository.save(film);
	}

	// Delete film
	public void delete(long id) {
		filmRepository.deleteById(id);
	}

	// Convert FilmDTO to Film
	public Film convertDTOToModel(FilmDTO filmDTO) {
		Film film = new Film();
		Category category = new Category();
		category.setId(filmDTO.getId_category());
		filmDTO.setCategory(category);
		BeanUtils.copyProperties(filmDTO, film);
		return film;
	}

	// Convert Film to FilmDTO
	public FilmDTO convertModelToDTO(Film film) {
		FilmDTO filmDTO = new FilmDTO();
		BeanUtils.copyProperties(film, filmDTO);
		filmDTO.setId_category(film.getCategory().getId());
		return filmDTO;
	}

}
