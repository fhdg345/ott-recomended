package com.ott.server.review.mapper;

import com.ott.server.media.entity.Media;
import com.ott.server.member.entity.Member;
import com.ott.server.review.dto.MediaDetailDto;
import com.ott.server.review.dto.MemberDetailDto;
import com.ott.server.review.dto.ReviewDetailDto;
import com.ott.server.review.dto.ReviewDetailMediaDto;
import com.ott.server.review.entity.Review;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-27T03:53:34+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public MemberDetailDto memberToMemberDetailDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDetailDto memberDetailDto = new MemberDetailDto();

        memberDetailDto.setNickname( member.getNickname() );
        memberDetailDto.setAvatarUri( member.getAvatarUri() );
        memberDetailDto.setMemberId( member.getMemberId() );

        return memberDetailDto;
    }

    @Override
    public ReviewDetailDto reviewToReviewDetailDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDetailDto reviewDetailDto = new ReviewDetailDto();

        reviewDetailDto.setId( review.getId() );
        reviewDetailDto.setContent( review.getContent() );
        reviewDetailDto.setCreatedAt( review.getCreatedAt() );
        reviewDetailDto.setLastModifiedAt( review.getLastModifiedAt() );
        reviewDetailDto.setMember( memberToMemberDetailDto( review.getMember() ) );

        return reviewDetailDto;
    }

    @Override
    public ReviewDetailMediaDto reviewToReviewDetailMediaDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDetailMediaDto reviewDetailMediaDto = new ReviewDetailMediaDto();

        reviewDetailMediaDto.setId( review.getId() );
        reviewDetailMediaDto.setContent( review.getContent() );
        reviewDetailMediaDto.setCreatedAt( review.getCreatedAt() );
        reviewDetailMediaDto.setLastModifiedAt( review.getLastModifiedAt() );
        reviewDetailMediaDto.setMedia( mediaToMediaDetailDto( review.getMedia() ) );

        return reviewDetailMediaDto;
    }

    protected MediaDetailDto mediaToMediaDetailDto(Media media) {
        if ( media == null ) {
            return null;
        }

        MediaDetailDto mediaDetailDto = new MediaDetailDto();

        mediaDetailDto.setMediaId( media.getMediaId() );
        mediaDetailDto.setTitle( media.getTitle() );
        mediaDetailDto.setMainPoster( media.getMainPoster() );

        return mediaDetailDto;
    }
}
