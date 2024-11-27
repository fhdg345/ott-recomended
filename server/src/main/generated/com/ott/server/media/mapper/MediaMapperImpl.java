package com.ott.server.media.mapper;

import com.ott.server.media.dto.MediaDto;
import com.ott.server.media.entity.Media;
import com.ott.server.mediaott.entity.MediaOtt;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-27T03:53:35+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class MediaMapperImpl implements MediaMapper {

    @Override
    public MediaDto.Response3 toResponse3Dto(Media media) {
        if ( media == null ) {
            return null;
        }

        MediaDto.Response3 response3 = new MediaDto.Response3();

        response3.setGenre( fromGenreList( media.getGenres() ) );
        List<MediaOtt> list1 = media.getMediaOtts();
        if ( list1 != null ) {
            response3.setMediaOtt( new ArrayList<MediaOtt>( list1 ) );
        }
        response3.setTmdbId( media.getTmdbId() );
        response3.setTitle( media.getTitle() );
        response3.setContent( media.getContent() );
        response3.setCategory( media.getCategory() );
        response3.setCreator( media.getCreator() );
        response3.setCast( media.getCast() );
        response3.setMainPoster( media.getMainPoster() );
        response3.setTitlePoster( media.getTitlePoster() );
        response3.setReleaseDate( media.getReleaseDate() );
        response3.setAgeRate( media.getAgeRate() );
        response3.setRecent( media.getRecent() );

        return response3;
    }
}
