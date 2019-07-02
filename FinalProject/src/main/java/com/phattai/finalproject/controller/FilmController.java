package com.phattai.finalproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phattai.finalproject.dto.FilmDTO;
import com.phattai.finalproject.model.Film;
import com.phattai.finalproject.service.FilmService;

@RestController
@RequestMapping("/film")
public class FilmController {

	@Autowired
	private FilmService filmService;

	// GET all film
	@GetMapping("/getall")
	public List<FilmDTO> getAll() {
		List<Film> listFilm = (List<Film>) filmService.findAll();
		List<FilmDTO> listFilmDTO = new ArrayList<FilmDTO>();
		listFilmDTO = listFilm.stream().map(item -> filmService.convertModelToDTO(item)).collect(Collectors.toList());
		return listFilmDTO;
	}

	// GET film by id
	@GetMapping("/{id}")
	public FilmDTO getById(@PathVariable("id") long id) {
		Film film = filmService.findById(id);
		if (film == null) {
			return null;
		}
		FilmDTO filmDTO = filmService.convertModelToDTO(film);
		return filmDTO;
	}

	// POST film
	@PostMapping("/add")
	public FilmDTO add(@RequestParam("image") MultipartFile fileImage, @RequestParam("film") String strFilmDTO) {
		try {
			FilmDTO filmDTO = new ObjectMapper().readValue(strFilmDTO, FilmDTO.class);
			filmDTO.setImage(fileImage.getBytes());
			Film film = new Film();
			film = filmService.convertDTOToModel(filmDTO);
			filmService.save(film);
			return filmDTO;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// PUT film to update
	@PutMapping("/update")
	public FilmDTO update(@RequestParam("image") MultipartFile fileImage, @RequestParam("film") String strFilmDTO) {
		FilmDTO filmDTO;
		try {
			filmDTO = new ObjectMapper().readValue(strFilmDTO, FilmDTO.class);
			long id = filmDTO.getId();
			if (filmService.findById(id) == null) {
				return null;
			}
			filmDTO.setImage(fileImage.getBytes());
			Film film = new Film();
			film = filmService.convertDTOToModel(filmDTO);
			filmService.save(film);
			return filmDTO;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// DELETE film by id
	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable("id") long id) {
		Film film = filmService.findById(id);
		if (film == null) {
			return "Not found ID";
		}
		filmService.delete(id);
		return "Delete success";
	}
}
