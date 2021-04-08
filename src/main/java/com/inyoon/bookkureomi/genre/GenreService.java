package com.inyoon.bookkureomi.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inyoon.bookkureomi.domain.Genre;

import java.util.List;

@Service
public class GenreService {
	@Autowired
	private GenreMapper genreMapper;


    public List<Genre> getGenreList(){
    	return genreMapper.getGenreList();
    }
    public Genre getGenre(int genreNo) {
    	return genreMapper.getGenre(genreNo);
    }
    public Genre getGenreByName(String genre) {
    	return genreMapper.getGenreByName(genre);
    }
}
