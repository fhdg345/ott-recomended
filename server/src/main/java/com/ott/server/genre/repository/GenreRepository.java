package com.ott.server.genre.repository;

import com.ott.server.genre.entity.Genre;
import com.ott.server.media.entity.Media;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    void deleteByMedia(Media media);

    List<Genre> findByMedia(Media media);
    List<Genre> findByGenreNameIn(List<String> names);


    @Query("SELECT g FROM Genre g WHERE g.genreName IN :names")
    List<Genre> findByGenreNameIn(@Param("names") List<String> names, Pageable pageable);

}