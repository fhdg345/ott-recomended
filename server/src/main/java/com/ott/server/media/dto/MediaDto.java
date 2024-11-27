package com.ott.server.media.dto;

import com.ott.server.genre.entity.Genre;
import com.ott.server.media.entity.Media;
import com.ott.server.mediaott.entity.MediaOtt;
import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class
MediaDto {
    @Setter
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Create {
        private int tmdbId;
        private String title; // 제목
        private String content;  //내용
        private String category;   // 카테고리
        private String dataType;   //
        private String creator;   // 감독
        private String cast;    //출연진
        private String mainPoster; // 메인이미지
        private String titlePoster;  //제목이미지
        private int releaseDate;  // 출시일
        private String ageRate;   // 이용가
        private Boolean recent;   //최신작
        private List<String> genre;   //장르
        private List<MediaOtt> mediaOtt;  //OTT

        private double popularity;          // 인기도
        private double voteAverage;         // 평균 평점
        private int voteCount;              // 투표 수

        private int runtime;
    }


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class CreateTMDB {
        private int tmdbId;
        private boolean adult;    //성인여버
        private String name;      // 제목
        private String dataType;
        private String description;         // 설명
        private String posterPath;          // 포스터 이미지 경로
        private String backdropPath;        // 백드롭 이미지 경로
        private String releaseDate;         // 첫 방영일
        private String originalLanguage;    // 원래 언어
        private double popularity;          // 인기도
        private double voteAverage;         // 평균 평점
        private int voteCount;              // 투표 수
        private String originCountry;       // 제작 국가
        private List<Integer> genreIds;     // 장르 ID 목록
        private String ottName;             // OTT 이름
        private String category;            //카테고리
        private int runtime;
        private String cast;
        private String homepage;


    }



    @Getter
    public static class Update {
        private Optional<Long> id = Optional.empty();
        private Optional<String> title = Optional.empty();
        private Optional<String> content = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<String> creator = Optional.empty();
        private Optional<String> cast = Optional.empty();
        private Optional<String> mainPoster = Optional.empty();
        private Optional<String> titlePoster = Optional.empty();
        private Optional<Integer> releaseDate = Optional.empty();
        private Optional<String> ageRate = Optional.empty();
        private Optional<Boolean> recent = Optional.empty();
        private Optional<List<String>> genre = Optional.empty();
        private Optional<List<MediaOtt>> mediaOtt = Optional.empty();

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String mainPoster;
        private List<String> genre;
        private List<String> mediaOtt;

        public Response(List<Media> content, long totalElements) {
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response3 {
        private int tmdbId;
        private String title;
        private String content;
        private String category;
        private String creator;
        private String cast;
        private String mainPoster;
        private String titlePoster;
        private Integer releaseDate;
        private String ageRate;
        private Boolean recent;
        private List<String> genre;
        private List<MediaOtt> mediaOtt;
        private Integer countRecommend;
        private Boolean checkBookmark;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response2 {
        private Long id;
        private String title;
        private String mainPoster;

    }
}
