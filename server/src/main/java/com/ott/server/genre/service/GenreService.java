package com.ott.server.genre.service;

import com.ott.server.genre.entity.Genre;
import com.ott.server.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final  GenreRepository genreRepository;


    public List<Genre> findTop12Genres(List<String> genreNames) {
        List<Genre> result = new ArrayList<>();

        for (String genre : genreNames) {
            Pageable pageable = PageRequest.of(0, 12); // 첫 페이지, 크기 12
            List<Genre> genres = genreRepository.findByGenreNameIn(Collections.singletonList(genre), pageable);
            result.addAll(genres); // 각 장르의 12개를 결과 리스트에 추가
        }

        return result; // 모든 장르의 12개씩 포함된 리스트 반환
    }

    public List<Genre> findTop36Genres(List<String> genreNames) {
        List<Genre> result = new ArrayList<>();

        for (String genre : genreNames) {
            Pageable pageable = PageRequest.of(0, 36); // 첫 페이지, 크기 36
            List<Genre> genres = genreRepository.findByGenreNameIn(Collections.singletonList(genre), pageable);
            result.addAll(genres); // 각 장르의 12개를 결과 리스트에 추가
        }

        return result; // 모든 장르의 36개씩 포함된 리스트 반환
    }


}
