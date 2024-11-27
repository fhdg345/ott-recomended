package com.ott.server.search.mapper;

import com.ott.server.media.dto.MediaResponseDto;
import com.ott.server.media.entity.Media;
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
public class SearchMapperImpl implements SearchMapper {

    @Override
    public MediaResponseDto mediaToMediaResponseDto(Media media) {
        if ( media == null ) {
            return null;
        }

        MediaResponseDto.MediaResponseDtoBuilder mediaResponseDto = MediaResponseDto.builder();

        mediaResponseDto.id( media.getMediaId() );
        mediaResponseDto.title( media.getTitle() );
        mediaResponseDto.mainPoster( media.getMainPoster() );

        return mediaResponseDto.build();
    }

    @Override
    public List<MediaResponseDto> mediasToMediaResponseDtos(List<Media> medias) {
        if ( medias == null ) {
            return null;
        }

        List<MediaResponseDto> list = new ArrayList<MediaResponseDto>( medias.size() );
        for ( Media media : medias ) {
            list.add( mediaToMediaResponseDto( media ) );
        }

        return list;
    }
}
