package com.ott.server.tmdb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class TMDBClient {

    private static final String API_KEY = "77e73b2ee7503ba172e322dfc1724ef0";
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private final RestTemplate restTemplate;

    private String buildUrl(String path, Object... uriVariables) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL + path)
                .queryParam("api_key", API_KEY)
                .queryParam("language", "ko-KR")
                .buildAndExpand(uriVariables)
                .toUriString();
    }

    private String getData(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    // https://api.themoviedb.org/3/discover/tv?with_networks=8&api_key=77e73b2ee7503ba172e322dfc1724ef0&language=ko-KR&page=1
    // OTT 데이터 가져오기 메서드들
    public String getOTTData(String networkId, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/discover/tv")
                .queryParam("with_networks", networkId)
                .queryParam("api_key", API_KEY)
                .queryParam("language", "ko-KR")
                .queryParam("page", page) // 페이지 추가
                .toUriString();
        return getData(url);
    }

    public String getDisneyPlusData(int page) {
        return getOTTData("273", page);
    }

    public String getWavveData(int page) {
        return getOTTData("8", page);
    }

    public String getNetflixData(int page) {
        return getOTTData("213", page);
    }

    public String getWatchaData(int page) {
        return getOTTData("200", page);
    }

    // 영화 데이터 가져오기 메서드들
    public String getMoviesData(String type, int page) {
        String url = buildUrl("/movie/" + type, "")
                + "&page=" + page; // 페이지 추가
        return getData(url);
    }

    public String getNowPlayingMovies(int page) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/now_playing")
                .queryParam("api_key", API_KEY)
                .queryParam("language", "ko-KR")
                .queryParam("page", page)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }



    public String getUpcomingMovies(int page) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/upcoming")
                .queryParam("api_key", API_KEY)
                .queryParam("language", "ko-KR")
                .queryParam("page", page)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }





    public String getPopularMovies(int page) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/popular")
                .queryParam("api_key", API_KEY)
                .queryParam("language", "ko-KR")
                .queryParam("page", page)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }



    public String getTopRatedMovies(int page) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/top_rated")
                .queryParam("api_key", API_KEY)
                .queryParam("language", "ko-KR")
                .queryParam("page", page)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }




    // https://api.themoviedb.org/3/movie/1160018?api_key=77e73b2ee7503ba172e322dfc1724ef0&language=ko-KR&append_to_response=credits
    // 영화 세부 정보 가져오기
    public String getMovieDetails(String movieId) {
        String url = buildUrl("/movie/{movie_id}?append_to_response=credits", movieId);
        return getData(url);
    }




    // TV 데이터 가져오기 메서드들
    public String getTVShowsData(String type, int page) {
        String url = buildUrl("/tv/" + type, "")
                + "&page=" + page; // 페이지 추가
        return getData(url);
    }




    //1.현재 트렌드 TV
    public String getTrendingTVShows(int page) {
        String url = buildUrl("/trending/tv/week") + "&page=" + page;
        return getData(url);
    }

    //2. 온에어 TV 프로그램
    public String getOnAirTVShows(int page) {
        return getTVShowsData("on_the_air", page);
    }

    //3.인기 TV
    public String getPopularTVShows(int page) {
        return getTVShowsData("popular", page);
    }

    
    //4.높은 시청률
    public String getTopRatedTVShows(int page) {
        return getTVShowsData("top_rated", page);
    }



    //  https://api.themoviedb.org/3/tv/1160018?api_key=77e73b2ee7503ba172e322dfc1724ef0&language=ko-KR&append_to_response=credits
    // TV 프로그램 세부 정보 가져오기
    public String getTVShowDetails(String tvId) {
        String url = buildUrl("/tv/{tv_id}?append_to_response=credits", tvId);
        return getData(url);
    }

    // 영화 검색
    public String searchMovies(String query, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/search/movie")
                .queryParam("api_key", API_KEY)
                .queryParam("language", "ko-KR")
                .queryParam("query", query)
                .queryParam("page", page)
                .toUriString();
        return getData(url);
    }

    // TV 프로그램 검색
    // https://api.themoviedb.org/3/search/tv?api_key=77e73b2ee7503ba172e322dfc1724ef0&language=ko-KR&query=마당이 있는 집&page=1
    public String searchTVShows(String query, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/search/tv")
                .queryParam("api_key", API_KEY)
                .queryParam("language", "ko-KR")
                .queryParam("query", query)
                .queryParam("page", page)
                .toUriString();
        return getData(url);
    }
}
