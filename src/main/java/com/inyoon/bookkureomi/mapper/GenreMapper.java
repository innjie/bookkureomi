package com.inyoon.bookkureomi.mapper;

import com.inyoon.bookkureomi.domain.Genre;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GenreMapper {
    public List<Genre> getGenreList();
    public Genre getGenre(int genreNo);
    public Genre getGenreByName(String genre);
}
