package com.ott.server.tmdb;


import com.ott.server.media.dto.MediaDto;
import com.ott.server.media.entity.Media;
import com.ott.server.media.repository.MediaRepository;
import com.ott.server.media.service.MediaService;
import com.ott.server.mediaott.entity.MediaOtt;
import com.ott.server.mediaott.repository.MediaOttRepository;
import com.ott.server.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class TMDBCollectionScheduler {

    private final TMDBClient tmdbClient;
    private final TMDBDataProcessor tmdbDataProcessor;
    private final MediaService mediaService;

    private final MediaRepository mediaRepository;
    private final MediaOttRepository mediaOttRepository;

    //개별 등록 처리.
    //@Scheduled(fixedDelay = 60000 )
    public void getMovieDetails() {
        try{
            // 마당이 있는 집  - 211748
            //특정 아이디 값 가져오기
            String getMovieDetail = tmdbClient.searchTVShows("마당이 있는 집",1);
            System.out.println(" getMovieDetail 마당이 있는 집  - 211748" + getMovieDetail);
            saveOttData(getMovieDetail, "Netflix" ,"tv", "ott");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    // 24시간마다 실행 (하루에 한 번) * 60 * 24
    // ott
     @Scheduled(fixedDelay = 60000 * 60 * 24)
    //Scheduled(cron = "0 0 0 * * ?")  // 매일 24시 자정
    public void fetchAndSaveOttData() {
        log.info(" fetchAndSaveOttData 스케쥴링 시작");

        try {
            //String disneyPlusData = tmdbClient.getDisneyPlusData(1);
            //String wavveData = tmdbClient.getWavveData(1);

            //String watchaData = tmdbClient.getWatchaData(1);
            // 가져온 데이터를 저장하거나 처리하는 로직
            //saveOttData(disneyPlusData, "Disney Plus" ,"tv", "ott");
            //saveOttData(wavveData, "Wavve" ,"tv", "ott");


            for(int i = 1; i < 10; i++) {
                String netflixData = tmdbClient.getNetflixData(i);
                saveOttData(netflixData, "Netflix" ,"tv", "ott");
            }
//
//            for(int i = 1; i < 10; i++) {
//                String disneyPlusData = tmdbClient.getDisneyPlusData(i);
//                saveOttData(disneyPlusData, "Disney Plus" ,"tv", "ott");
//            }

//            for(int i = 1; i < 10; i++) {
//                String watchaData = tmdbClient.getWatchaData(i);
//                saveOttData(watchaData, "Watcha" ,"tv", "ott");
//            }
//
//            for(int i = 1; i < 10; i++) {
//                String wavveData = tmdbClient.getWavveData(i);
//                saveOttData(wavveData, "Wavve" ,"tv", "ott");
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 영화 데이터를 저장하거나 텍스트 파일로 저장
     *
     * **/
    //@Scheduled(fixedDelay = 60000 * 60 * 25)
    //Scheduled(cron = "0 0 0 * * ?")  // 매일 24시 자정
    public void fetchAndSaveMovie() {
        try {
            // 1. 현재 상영 중인 영화 데이터 가져오기
            fetchAndSaveMovieByCategory("nowPlayingMovies", "movie", (page) -> tmdbClient.getNowPlayingMovies(page));

            // 2. 상영 예정 영화 데이터 가져오기
            fetchAndSaveMovieByCategory("upcomingMovies", "movie", (page) -> tmdbClient.getUpcomingMovies(page));

            // 3. 인기 영화 데이터 가져오기
           fetchAndSaveMovieByCategory("popularMovies", "movie", tmdbClient::getPopularMovies);

            // 4. 높은 평점의 영화 데이터 가져오기
            fetchAndSaveMovieByCategory("topRatedMovies", "movie", tmdbClient::getTopRatedMovies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * TV 데이터 가져오기
     */
    //@Scheduled(fixedDelay = 60000 * 60 * 26)
    //@Scheduled(cron = "0 0 0 * * ?")  // 매일 24시 자정
    public void fetchAndSaveTV() {
        try {
            // 1. 현재 트렌드 TV
            fetchAndSaveMovieByCategory("trendingTVShows", "tv", (page) -> tmdbClient.getTrendingTVShows(page));

            // 2. 온에어 TV 프로그램
           // fetchAndSaveMovieByCategory("onAirTVShows", "tv", (page) -> tmdbClient.getOnAirTVShows(page));

           // 3. 인기 TV
           //fetchAndSaveMovieByCategory("popularTVShows", "tv", (page) -> tmdbClient.getPopularTVShows(page));

            // 4. 높은 시청률
           // fetchAndSaveMovieByCategory("topRatedTVShows", "tv", (page) -> tmdbClient.getTopRatedTVShows(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    
    
    //업데이트 처리하기
    @Transactional
    //@Scheduled(fixedDelay = 60000 * 60 * 27)
    public void updateMedia() {
        int batchSize = 13618;
        int pageNumber = 0;
        List<Media> mediaList;

        do {
            Pageable pageable = PageRequest.of(pageNumber, batchSize);
            mediaList = mediaRepository.findAll(pageable).getContent();


            for (Media media : mediaList) {

                if(!StringUtils.hasText(media.getCast())){

                        String movieDetails ="";
                        if(media.getCategory().equals("movie")) { //영화
                            movieDetails = tmdbClient.getMovieDetails(String.valueOf(media.getTmdbId()));
                        }else{
                            //tv
                            //last_air_date
                            movieDetails = tmdbClient.getTVShowDetails(String.valueOf(media.getTmdbId()));
                        }
                        MediaDto.CreateTMDB createTMDB = tmdbDataProcessor.parseOttDetailData(movieDetails);

                        //더티 체킹 업데이트
                        media.setContent(createTMDB.getDescription());
                        media.setReleaseDate(UriCreator.convertReleaseDateToInt(createTMDB.getReleaseDate()));
                        media.setRuntime(createTMDB.getRuntime());
                        media.setCast(createTMDB.getCast());

                        List<MediaOtt> byMedia = mediaOttRepository.findByMedia(media);
                        MediaOtt mediaOtt = byMedia.get(0);

                        //midaott address  업데이트
                        if(mediaOtt.getOttName().equals("Netflix")){
                            //홈페이지 값이 있을 경우
                            if(StringUtils.hasText(createTMDB.getHomepage())) {
                                mediaOtt.setOttAddress(createTMDB.getHomepage());
                            }
                        }else if(mediaOtt.getOttName().equals("Disney Plus")){
                            mediaOtt.setOttAddress("https://www.disneyplus.com/");

                        }else if(mediaOtt.getOttName().equals("Watcha")) {

                            if(media.getCategory().equals("movie")){
                                mediaOtt.setOttAddress("https://watcha.com/search?domain=movie&query="+media.getTitle());
                            }else{
                                mediaOtt.setOttAddress("https://watcha.com/search?domain=all&query="+media.getTitle());
                            }


                        }else if(mediaOtt.getOttName().equals("Wavve")){
                            mediaOtt.setOttAddress("https://www.wavve.com/search?searchWord="+media.getTitle());
                        }

                }

            }

            
            // Save updated media if necessary
            mediaRepository.saveAll(mediaList);
            pageNumber++;

        } while (mediaList.size() == batchSize); // Continue until less than batchSize is returned
    }





    private void fetchAndSaveMovieByCategory(String category, String type, PageFetcher pageFetcher) {
        int page = 1;
        try {
            while (true) {
                String response = pageFetcher.fetchPage(page);

                // API 응답이 null이거나 비어있는 경우 반복 종료
                if (response == null || response.isEmpty()) {
                    break;
                }

                // JSON 응답 파싱
                JSONObject jsonObject = new JSONObject(response);
                int totalPages = jsonObject.getInt("total_pages");

                saveOttData(response, null, type, category);

                // 모든 페이지를 다 가져오면 반복 종료
                if (page >= totalPages) {
                    break;
                }
                page++;

                if(page>300) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface PageFetcher {
        String fetchPage(int page);
    }





    private void saveOttData(String jsonData, String ottName, String category, String dataType) {
        if (jsonData != null) {
            try {
                List<MediaDto.CreateTMDB> mediaList = tmdbDataProcessor.parseOttData(jsonData);

                // 현재 연도 추출
                int currentYear = java.time.Year.now().getValue();

                // 장르 ID와 이름을 매핑하는 해시맵 생성
                Map<Integer, String> genreMap = new HashMap<>();
                genreMap.put(28, "액션");
                genreMap.put(16, "애니메이션");
                genreMap.put(10749, "로맨스");
                genreMap.put(99, "다큐멘터리");
                genreMap.put(18, "드라마");
                genreMap.put(35, "코미디");
                genreMap.put(27, "공포");
                genreMap.put(10402, "음악");
                genreMap.put(10770, "Reality TV");
                genreMap.put(878, "SF");
                genreMap.put(10751, "가족");
                genreMap.put(80, "범죄");
                genreMap.put(10752, "전쟁");
                genreMap.put(53, "스릴러");
                genreMap.put(14, "판타지");
                genreMap.put(36, "역사");
                genreMap.put(37, "서부");
                genreMap.put(12, "어드벤처");
                genreMap.put(9648, "미스터리");


                List<MediaOtt> mediaOtts = new ArrayList<>();
                MediaOtt mediaOtt = new MediaOtt();
                mediaOtt.setMedia(new Media());
                if (ottName == null) {
                    int ottRandom = (int) (Math.random() * 3) + 1;
                    switch (ottRandom) {
                        case 1:
                            ottName = "Wavve";
                            break;
                        case 2:
                            ottName = "Disney Plus";
                            break;
                        case 3:
                            ottName = "Watcha";
                            break;
                        default:
                            ottName = "Netflix";
                    }
                }
                mediaOtt.setOttName(ottName);
                String address="";
                if(ottName.equals("Wavve")) address ="https://www.wavve.com/search?searchWord=";
                if(ottName.equals("Netflix")) address ="https://www.netflix.com/";
                if(ottName.equals("Watcha")){
                    if(category.equals("movie")){
                        address ="https://watcha.com/search?domain=movie&query=";
                    }else{
                        address ="https://watcha.com/search?domain=all&query=";
                    }
                }
                if(ottName.equals("Disney Plus")) address ="https://www.disneyplus.com/";
                mediaOtt.setOttAddress(address);
                mediaOtts.add(mediaOtt);


                for (MediaDto.CreateTMDB tmdb : mediaList) {

                    //System.out.println("MediaDto.CreateTMDB : " + tmdb.toString());
                    tmdb.setOttName(ottName);

                    // 장르 ID 목록을 장르 이름 목록으로 변환
                    List<String> genreNames = tmdb.getGenreIds().stream()
                            .map(genreMap::get)
                            .filter(Objects::nonNull)  // ID에 해당하는 장르가 있을 때만 추가
                            .collect(Collectors.toList());


                    // 출시일 변환 (YYYY-MM-DD -> int, YYYY만 추출)
                    String releaseDate = tmdb.getReleaseDate();
                    int releaseYear = 0; // 기본값 설정

                    // 출시일이 비어있지 않은 경우에만 변환
                    if (releaseDate != null && releaseDate.length() >= 4) {
                        releaseYear = Integer.parseInt(releaseDate.substring(0, 4));
                    }


                    // 최신작 여부 확인 (출시 연도가 현재 연도와 같으면 true)
                    boolean isRecent = releaseYear == currentYear;

                    // 나이 등급 설정
                    String ageRate = tmdb.isAdult() ? "청소년 관람불가" : "12세 관람가";


                    /**
                     * w500: 이 부분은 이미지의 크기를 지정하는 것으로, 500픽셀
                     * w92: 작은 포스터
                     * w154: 중간 크기 포스터
                     * w342: 큰 포스터
                     * w780: 더 큰 포스터
                     * original: 원본 크기 이미지
                     */
                    String posterPath=null;
                    if(StringUtils.hasText(tmdb.getPosterPath()) && !tmdb.getPosterPath().equals("null") ){
                        posterPath="https://image.tmdb.org/t/p/w342" + tmdb.getPosterPath();
                    }else{
                        if(StringUtils.hasText(tmdb.getBackdropPath()) && !tmdb.getBackdropPath().equals("null")  ){
                            posterPath="https://image.tmdb.org/t/p/original" + tmdb.getBackdropPath();
                        }else{
                            int randomNum = (int) (Math.random() * 10000);
                            posterPath="https://picsum.photos/220/320?random="+randomNum;
                        }
                    }


                    String backdropPath=null;
                    if(StringUtils.hasText(tmdb.getBackdropPath()) && !tmdb.getBackdropPath().equals("null") ){
                        backdropPath="https://image.tmdb.org/t/p/original" + tmdb.getBackdropPath();
                    }else{
                        if(StringUtils.hasText(tmdb.getBackdropPath()) && !tmdb.getBackdropPath().equals("null")  ){
                            backdropPath="https://image.tmdb.org/t/p/original" + tmdb.getPosterPath();
                        }else{
                            int randomNum = (int) (Math.random() * 10000);
                            backdropPath="https://picsum.photos/220/320?random="+randomNum;
                        }
                    }


                    // MediaDto.Create 객체 생성
                    MediaDto.Create create = MediaDto.Create.builder()
                            .tmdbId(tmdb.getTmdbId())
                            .title(tmdb.getName())              // 제목
                            .content(tmdb.getDescription())     // 내용
                            .category(category)                    // 카테고리
                            .dataType(dataType)
                            .creator("")                       // 감독 (현재 데이터가 없으므로 빈 값)
                            .cast("")                          // 출연진 (현재 데이터가 없으므로 빈 값)
                            .mainPoster(posterPath) // 메인 이미지
                            .titlePoster(backdropPath)  // 제목 이미지
                            .releaseDate(releaseYear)           // 출시 연도
                            .ageRate(ageRate)                  // 나이 등급
                            .recent(isRecent)                  // 최신작 여부
                            .genre(genreNames)                 // 장르 이름 목록
                            .mediaOtt(mediaOtts) // OTT 이름 설정
                            .build();

                     mediaService.createMedia(create); // MediaService를 통해 데이터 저장
                }



                System.out.println("OTT 데이터 저장 성공: " + ottName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }









}