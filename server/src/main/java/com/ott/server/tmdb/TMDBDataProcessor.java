package com.ott.server.tmdb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ott.server.media.dto.MediaDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TMDBDataProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<MediaDto.CreateTMDB> parseOttData(String jsonData) {
        List<MediaDto.CreateTMDB> mediaList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode results = rootNode.path("results");

            if (results.isArray()) {
                for (JsonNode node : results) {
                    MediaDto.CreateTMDB media = new MediaDto.CreateTMDB();


                    //내용이 있는 것만 등록
                    if(StringUtils.hasText(node.path("overview").asText())){


                                media.setTmdbId(node.path("id").asInt()); // TMDB ID 설정
                                media.setAdult(node.path("adult").asBoolean());
                                media.setName(node.path("name").asText()); //제목 설정

                                if(!StringUtils.hasText(node.path("name").asText())){
                                    media.setName(node.path("title").asText());
                                }

                                media.setPopularity(node.path("popularity").asDouble());
                                media.setVoteAverage(node.path("vote_average").asDouble());
                                media.setVoteCount(node.path("vote_count").asInt()); // ���표 수

                                media.setDescription(node.path("overview").asText()); // 설명 설정
                                media.setPosterPath(node.path("poster_path").asText()); // 포스터 경로 설정
                                media.setBackdropPath(node.path("backdrop_path").asText()); // 백드롭 경로 설정


                                String releaseDate="";
                                if( StringUtils.hasText(node.path("first_air_date").asText())){
                                    releaseDate=node.path("first_air_date").asText();
                                }
                                if( StringUtils.hasText(node.path("release_date").asText())){
                                    releaseDate=node.path("release_date").asText();
                                }
                                media.setReleaseDate(releaseDate); // 첫 방영일 설정

                                media.setOriginalLanguage(node.path("original_language").asText()); // 원래 언어 설정
                                media.setPopularity(node.path("popularity").asDouble()); // 인기도 설정
                                media.setVoteAverage(node.path("vote_average").asDouble()); // 평균 평점 설정
                                media.setVoteCount(node.path("vote_count").asInt()); // 투표 수 설정
                                media.setOriginCountry(node.path("origin_country").toString()); // 제작 국가 설정

                                //release_date
                                media.setGenreIds(objectMapper.convertValue(node.path("genre_ids"), new TypeReference<List<Integer>>(){})); // 장르 ID 목록 설정

                                //제목이 한국어만 드록
                                if(isKorean(media.getName())){
                                    mediaList.add(media);
                                }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaList;
    }



    public boolean isKorean(String text) {
        // 한글 유니코드 범위: 가-힣
        return text != null && text.matches(".*[가-힣]+.*");
    }




    //  https://api.themoviedb.org/3/movie/1160018?api_key=77e73b2ee7503ba172e322dfc1724ef0&language=ko-KR&append_to_response=credits

    // 상세보기 메서드
    public MediaDto.CreateTMDB parseOttDetailData(String jsonData) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonData);

            MediaDto.CreateTMDB media = new MediaDto.CreateTMDB();

            // 출시일 추가
            media.setReleaseDate(rootNode.path("release_date").asText()); // 출시일 설정

            // 개요 추가
            media.setDescription(rootNode.path("overview").asText()); // 개요 설정

            media.setRuntime(rootNode.path("runtime").asInt()); // 상영 시간 설정

            
            //Tv 일경우
            if(!StringUtils.hasText(rootNode.path("release_date").asText())){
                media.setReleaseDate(rootNode.path("last_air_date").asText());
            }

            //homepage  값이 있을 경우
            if(StringUtils.hasText(rootNode.path("homepage").asText())){
                media.setHomepage(rootNode.path("homepage").asText());
            }



            // 출연진 정보 파싱
            StringBuilder sb = new StringBuilder();
            JsonNode creditsNode = rootNode.path("credits").path("cast");
            if (creditsNode.isArray()) {
                int count = 0; // 출연진 수 카운트
                for (JsonNode castNode : creditsNode) {
                    if (count < 7) { // 최대 7명의 출연진만 추가
                        sb.append(castNode.path("name").asText());
                        sb.append(", "); // 출연진 이름을 구분하기 위한 콤마 추가
                        count++;
                    } else {
                        break; // 7명 이상이면 루프 종료
                    }
                }
                // 마지막 콤마 및 공백 제거
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 2); // 마지막 ", " 제거
                }
            }
            media.setCast(sb.toString()); // 출연진 정보
            return media;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
