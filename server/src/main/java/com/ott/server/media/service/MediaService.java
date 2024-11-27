package com.ott.server.media.service;

import com.ott.server.exception.BusinessLogicException;
import com.ott.server.exception.ExceptionCode;
import com.ott.server.genre.entity.Genre;
import com.ott.server.genre.repository.GenreRepository;
import com.ott.server.media.dto.MediaDto;
import com.ott.server.media.dto.MultiResponseDto;
import com.ott.server.media.entity.Media;
import com.ott.server.media.mapper.MediaMapper;
import com.ott.server.media.repository.MediaRepository;
import com.ott.server.mediaott.entity.MediaOtt;
import com.ott.server.mediaott.repository.MediaOttRepository;
//import org.hibernate.search.mapper.orm.Search;
//import org.hibernate.search.mapper.orm.session.SearchSession;
import com.ott.server.member.entity.Member;
import com.ott.server.recommendation.repository.RecommendationRepository;
import com.ott.server.tmdb.TMDBClient;
import com.ott.server.tmdb.TMDBDataProcessor;
import com.ott.server.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MediaService {
    private final EntityManager entityManager;
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final MediaOttRepository mediaOttRepository;
    private final GenreRepository genreRepository;
    private final RecommendationRepository recommendationRepository;


    private final TMDBClient tmdbClient;

    private final TMDBDataProcessor tmdbDataProcessor;

    public MediaService(MediaRepository mediaRepository, MediaMapper mediaMapper, MediaOttRepository mediaOttRepository,
                        GenreRepository genreRepository, RecommendationRepository recommendationRepository,
                        TMDBClient tmdbClient,
                        TMDBDataProcessor tmdbDataProcessor,
                        EntityManager entityManager) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
        this.mediaOttRepository = mediaOttRepository;
        this.genreRepository = genreRepository;
        this.recommendationRepository = recommendationRepository;
        this.tmdbClient = tmdbClient;
        this.tmdbDataProcessor=tmdbDataProcessor;
        this.entityManager = entityManager;
    }

    @Transactional
    public void indexAllMedia() throws InterruptedException {
//        SearchSession searchSession = Search.session(entityManager);
//
//        searchSession.massIndexer(Media.class)
//                .startAndWait();

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public MediaDto.Response createMedia(MediaDto.Create createDto) {
        Media media = new Media();
        media.setTmdbId(createDto.getTmdbId());
        media.setTitle(createDto.getTitle());
        media.setContent(createDto.getContent());
        media.setCategory(createDto.getCategory());
        media.setDataType(createDto.getDataType());
        media.setCreator(createDto.getCreator());
        media.setCast(createDto.getCast());
        media.setMainPoster(createDto.getMainPoster());
        media.setTitlePoster(createDto.getTitlePoster());
        media.setReleaseDate(createDto.getReleaseDate());
        media.setAgeRate(createDto.getAgeRate());
        media.setRecent(createDto.getRecent());

        media.setPopularity(createDto.getPopularity());
        media.setVoteAverage(createDto.getVoteAverage());
        media.setVoteCount(createDto.getVoteCount());


        List<Genre> genres = createDto.getGenre().stream()
                .map(genreName -> {
                    Genre genre = new Genre();
                    genre.setMedia(media);
                    genre.setGenreName(genreName);
                    return genre;
                })
                .collect(Collectors.toList());
        media.setGenres(genres);

        List<MediaOtt> mediaOtts = createDto.getMediaOtt().stream()
                .map(ottName -> { //"Netflix: www.nadnf.com/cvxcvxczv"
                    MediaOtt mediaOtt = new MediaOtt();
                    mediaOtt.setMedia(media);
                    mediaOtt.setOttName(ottName.getOttName());
                    mediaOtt.setOttAddress(ottName.getOttAddress());
                    return mediaOtt;
                })
                .collect(Collectors.toList());
        media.setMediaOtts(mediaOtts);

        Boolean existsByTmdbId = mediaRepository.existsByTmdbId(media.getTmdbId());
        if (!existsByTmdbId) {
            Media savedMedia = mediaRepository.save(media);
            savedMedia.getGenres().forEach(genreRepository::save);
            savedMedia.getMediaOtts().forEach(mediaOttRepository::save);
            return mapMediaToResponseDto(savedMedia);
        }


        return null;


    }

    //@Cacheable("myCache")
    public List<MediaDto.Response2> getAllMedia() {
        List<Media> allMedia = mediaRepository.findAll();

        List<MediaDto.Response2> allMediaResponseDto = new ArrayList<>();
        for (Media media : allMedia) {
            MediaDto.Response2 responseDto = new MediaDto.Response2();

            responseDto.setId(media.getMediaId());
            responseDto.setTitle(media.getTitle());
            responseDto.setMainPoster(media.getMainPoster());



            allMediaResponseDto.add(responseDto);
        }

        return allMediaResponseDto;
    }




    @Transactional(propagation = Propagation.REQUIRED)
    public void updateMedia(Long mediaId, MediaDto.Update updateDto) {




        Media updatedMedia = mediaRepository.findByMediaId(mediaId)
                .map(media -> {
                    updateDto.getId().ifPresent(media::setId);
                    updateDto.getTitle().ifPresent(media::setTitle);
                    updateDto.getContent().ifPresent(media::setContent);
                    updateDto.getCategory().ifPresent(media::setCategory);
                    updateDto.getCreator().ifPresent(media::setCreator);
                    updateDto.getCast().ifPresent(media::setCast);
                    updateDto.getMainPoster().ifPresent(media::setMainPoster);
                    updateDto.getTitlePoster().ifPresent(media::setTitlePoster);
                    updateDto.getReleaseDate().ifPresent(media::setReleaseDate);
                    updateDto.getAgeRate().ifPresent(media::setAgeRate);
                    updateDto.getRecent().ifPresent(media::setRecent);

                    if(updateDto.getGenre().orElse(new ArrayList<>()).size()>=1){
                        List<Genre> genres = genreRepository.findByMedia(media);
                        for(Genre genre : genres)
                            genreRepository.delete(genre);
                        updateDto.getGenre().ifPresent(newGenre -> {
                            List<Genre> genreList =
                                    newGenre.stream()
                                            .map(genreName -> {
                                                Genre genre = new Genre();
                                                genre.setMedia(media);
                                                genre.setGenreName(genreName);
                                                return genreRepository.save(genre);
                                            })
                                            .collect(Collectors.toList());
                            media.setGenres(genreList);
                        });
                    }

                    if(updateDto.getMediaOtt().orElse(new ArrayList<>()).size()>=1){
                        List<MediaOtt> mediaOtts = mediaOttRepository.findByMedia(media);
                        for(MediaOtt mediaOtt : mediaOtts)
                            mediaOttRepository.delete(mediaOtt);
                        updateDto.getMediaOtt().ifPresent(newMediaOtt -> {
                            List<MediaOtt> mediaOttList = newMediaOtt.stream()
                                    .map(ott -> {
                                        MediaOtt mediaOtt = new MediaOtt();
                                        mediaOtt.setMedia(media);
                                        mediaOtt.setOttName(ott.getOttName());
                                        mediaOtt.setOttAddress(ott.getOttAddress());
                                        return mediaOttRepository.save(mediaOtt);
                                    })
                                    .collect(Collectors.toList());
                            media.setMediaOtts(mediaOttList);
                        });
                    }

//                            mediaRepository.save(media);
////
//                    updatedMedia.getGenres().forEach(genreRepository::save);
//                    updatedMedia.getMediaOtts().forEach(mediaOttRepository::save);
//
                    return mediaRepository.save(media);

                })
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));
    }


    private MediaDto.Response mapMediaToResponseDto(Media media) {
        List<String> genreNames = media.getGenres().stream()
                .map(Genre::getGenreName)
                .collect(Collectors.toList());

        List<String> ottNames = media.getMediaOtts().stream()
                .map(MediaOtt::getOttName)
                .collect(Collectors.toList());

        return new MediaDto.Response(
                media.getMediaId(),
                media.getTitle(),
                media.getMainPoster(),
                genreNames,
                ottNames
        );
    }


    @Transactional
    public MediaDto.Response3 getMedia(Long mediaId) {

        Media media = mediaRepository.findById(mediaId).orElseThrow(EntityNotFoundException::new);


        // 출연진 데이터가 없을경우 업데이트 처리
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






        return mediaRepository.findByIdWithGenres(mediaId)
        .map(mediaMapper::toResponse3Dto)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));

    }




    //todo mapper고치기

//        return mediaRepository.findById(mediaId)
//                .map(mediaMapper::toResponse3Dto)
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));


    public MultiResponseDto<MediaDto.Response2> getMediasAll(List<Genre> genres, List<MediaOtt> otts, Pageable pageable) {


        Page<Media> mediasPage = mediaRepository.findDistinctByGenresInAndMediaOttsIn(genres, otts, pageable);


        List<Media> medias = mediasPage.getContent();

        List<MediaDto.Response2> responses = new ArrayList<>();
        for (Media media : medias) {
            MediaDto.Response2 response = new MediaDto.Response2();
            response.setId(media.getMediaId());
            response.setTitle(media.getTitle());
            response.setMainPoster(media.getMainPoster());
            responses.add(response);
        }

        return new MultiResponseDto<>(responses, mediasPage.getNumber() + 1, mediasPage.getTotalPages());
    }



    public MultiResponseDto<MediaDto.Response2> getMedias(String category, List<Genre> genres,  Pageable pageable) {
        log.info("1. getMedias category {}", category);
        log.info("2. getMedias genres {}", genres.stream().map(Genre::getGenreName).collect(Collectors.joining(", ")));
        //List<MediaOtt> otts,
        //log.info("3. getMedias otts {}", otts.stream().map(MediaOtt::getOttName).collect(Collectors.joining(", ")));

        if(category.equals("영화")){
            category = "movie";
        }
        Page<Media> mediasPage = mediaRepository.findDistinctByCategoryAndGenresIn(category, genres,  pageable);

        List<Media> medias = mediasPage.getContent();

        log.info("결==============과. gmedias {}", medias.size());


        List<MediaDto.Response2> responses = new ArrayList<>();
        for (Media media : medias) {
            MediaDto.Response2 response = new MediaDto.Response2();
            response.setId(media.getMediaId());
            response.setTitle(media.getTitle());
            response.setMainPoster(media.getMainPoster());
            responses.add(response);
        }

        return new MultiResponseDto<>(responses, mediasPage.getNumber() + 1, mediasPage.getTotalPages());
    }


    // 서비스나 리포지토리 클래스에서 호출
    public Page<Media> findMediaByCategoryAndGenres(String category, List<Genre> genres, Pageable pageable) {
        List<Media> mediaList = new ArrayList<>();
        if (genres != null) {
            for (int i = 0; i < genres.size(); i += 1000) {
                List<Genre> subList = genres.subList(i, Math.min(i + 1000, genres.size()));
                Page<Media> mediaPage = mediaRepository.findDistinctByCategoryAndGenresIn(category, subList, pageable);
                mediaList.addAll(mediaPage.getContent());
            }
        } else {
            mediaList.addAll(mediaRepository.findDistinctByCategoryAndGenresIn(category, null, pageable).getContent());
        }
        return new PageImpl<>(mediaList, pageable, mediaList.size());
    }




    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteMedia(Long mediaId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new BusinessLogicException(ExceptionCode.INVALID_AUTHORIZATION);
//        } //todo 시큐리티 활성화시 동작됨 401 에러 코드, api 명세서에선 401 에러코드가 정의되어 있지 않음 수정필요.

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));

        mediaRepository.delete(media);
    }

    public Integer countRecommendByMedia(Long mediaId) {
        return mediaRepository.findRecommendCountByMediaId(mediaId);
    }

    public Boolean checkBookmarkByMedia(Long mediaId) {
        Integer check = mediaRepository.checkBookmarkByMediaId(mediaId);
        if(check > 0)
            return true;
        else{
            return false;
        }
    }

    //todo 컨텐츠 검색 search 엔드포인트는 search에서 만들어서 처리

    public MultiResponseDto<MediaDto.Response2> getRecommendationsByMediaIdOrderByCount(Pageable pageable) {
        Page<Object[]> mediasPage = recommendationRepository.findRecommendCountByMediaIdOrderByCount(pageable);
        List<Object[]> medias = mediasPage.getContent();
        List<MediaDto.Response2> responses = new ArrayList<>();
        for(int i = 0; i < medias.size(); i++){
            Long mediaId = (Long) medias.get(i)[0];
            Media findMedia = findVerifiedMedia(mediaId);
            MediaDto.Response2 response = new MediaDto.Response2();
            response.setId(findMedia.getMediaId());
            response.setTitle(findMedia.getTitle());
            response.setMainPoster(findMedia.getMainPoster());
            responses.add(response);
        }
        return new MultiResponseDto<>(responses, mediasPage.getNumber() + 1, mediasPage.getTotalPages());
    }

    public MultiResponseDto<MediaDto.Response2> findMediaByOttNameOrderByRecommendationCount(String ottName, Pageable pageable){
        Page<Media> mediasPage = mediaRepository.findMediaByOttNameOrderByRecommendationCountAsc(ottName, pageable);
        List<Media> medias = mediasPage.getContent();
        List<MediaDto.Response2> responses = new ArrayList<>();
        for(int i = 0; i < medias.size(); i++){
            Long mediaId = medias.get(i).getMediaId();
            Media findMedia = findVerifiedMedia(mediaId);
            MediaDto.Response2 response = new MediaDto.Response2();
            response.setId(findMedia.getMediaId());
            response.setTitle(findMedia.getTitle());
            response.setMainPoster(findMedia.getMainPoster());
            responses.add(response);
        }
        return new MultiResponseDto<>(responses, mediasPage.getNumber() + 1, mediasPage.getTotalPages());
    }

    public Media findVerifiedMedia(long mediaId) {
        Optional<Media> optionalMedia =
                mediaRepository.findById(mediaId);
        Media findMedia =
                optionalMedia.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));
        return findMedia;
    }


//    public Page<Media> getDistinctMediaByCategoryAndGenresAndOtts(String category, List<String> genres, List<String> otts, Pageable pageable) {
//        // 장르와 OTT 객체로 변환
//        List<Genre> genreList = genres.stream()
//                .map(Genre::new)  // 필요에 따라 Genre 객체로 변환
//                .distinct()
//                .collect(Collectors.toList());
//
//        List<MediaOtt> ottList = otts.stream().map(MediaOtt::new)
//                .distinct()
//                .collect(Collectors.toList());
//
//        return mediaRepository.findDistinctByCategoryAndGenresInAndMediaOttsIn(category, genreList, ottList, pageable);
//    }

}
