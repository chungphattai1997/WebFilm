package com.phattai.finalproject.dto;

import com.phattai.finalproject.model.Film;

public class FilmDTO extends Film {
	private long id_category;

	public long getId_category() {
		return id_category;
	}

	public void setId_category(long id_category) {
		this.id_category = id_category;
	}

}
