package com.ott.server.member.mapper;

import com.ott.server.interest.dto.InterestDto;
import com.ott.server.interest.entity.Interest;
import com.ott.server.member.dto.MemberDto;
import com.ott.server.member.entity.Member;
import com.ott.server.memberott.dto.MemberOttDto;
import com.ott.server.memberott.entity.MemberOtt;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-27T03:53:35+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(MemberDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.nickname( requestBody.getNickname() );
        member.email( requestBody.getEmail() );
        member.password( requestBody.getPassword() );
        member.avatarUri( requestBody.getAvatarUri() );
        member.category( requestBody.getCategory() );

        return member.build();
    }

    @Override
    public Member memberPatchToMember(MemberDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.memberId( requestBody.getMemberId() );
        member.nickname( requestBody.getNickname() );
        member.avatarUri( requestBody.getAvatarUri() );
        member.category( requestBody.getCategory() );

        return member.build();
    }

    @Override
    public MemberDto.Response memberToMemberResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        List<MemberOttDto.Response> memberOtts = null;
        List<InterestDto.Response> interests = null;
        List<String> roles = null;
        Long memberId = null;
        String nickname = null;
        String avatarUri = null;
        LocalDateTime createdAt = null;
        String category = null;

        memberOtts = memberOttListToResponseList( member.getMemberOtts() );
        interests = interestSetToResponseList( member.getInterests() );
        List<String> list2 = member.getRoles();
        if ( list2 != null ) {
            roles = new ArrayList<String>( list2 );
        }
        memberId = member.getMemberId();
        nickname = member.getNickname();
        avatarUri = member.getAvatarUri();
        createdAt = member.getCreatedAt();
        category = member.getCategory();

        MemberDto.Response response = new MemberDto.Response( memberId, nickname, avatarUri, createdAt, memberOtts, interests, roles, category );

        return response;
    }

    @Override
    public List<MemberDto.Response> membersToMemberResponses(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberDto.Response> list = new ArrayList<MemberDto.Response>( members.size() );
        for ( Member member : members ) {
            list.add( memberToMemberResponse( member ) );
        }

        return list;
    }

    protected MemberOttDto.Response memberOttToResponse(MemberOtt memberOtt) {
        if ( memberOtt == null ) {
            return null;
        }

        String memberOttName = null;

        memberOttName = memberOtt.getMemberOttName();

        MemberOttDto.Response response = new MemberOttDto.Response( memberOttName );

        return response;
    }

    protected List<MemberOttDto.Response> memberOttListToResponseList(List<MemberOtt> list) {
        if ( list == null ) {
            return null;
        }

        List<MemberOttDto.Response> list1 = new ArrayList<MemberOttDto.Response>( list.size() );
        for ( MemberOtt memberOtt : list ) {
            list1.add( memberOttToResponse( memberOtt ) );
        }

        return list1;
    }

    protected InterestDto.Response interestToResponse(Interest interest) {
        if ( interest == null ) {
            return null;
        }

        String interestName = null;

        interestName = interest.getInterestName();

        InterestDto.Response response = new InterestDto.Response( interestName );

        return response;
    }

    protected List<InterestDto.Response> interestSetToResponseList(Set<Interest> set) {
        if ( set == null ) {
            return null;
        }

        List<InterestDto.Response> list = new ArrayList<InterestDto.Response>( set.size() );
        for ( Interest interest : set ) {
            list.add( interestToResponse( interest ) );
        }

        return list;
    }
}
