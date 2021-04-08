package com.inyoon.bookkureomi.genre;

import org.apache.ibatis.annotations.Mapper;

import com.inyoon.bookkureomi.domain.Genre;

import java.util.List;

@Mapper
public interface GenreMapper {
    public List<Genre> getGenreList();
    public Genre getGenre(int genreNo);
    public Genre getGenreByName(String genre);
}
