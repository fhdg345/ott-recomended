package com.ott.server.member.repository;

import com.ott.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.memberOtts LEFT JOIN FETCH m.interests WHERE m.memberId = :id")
    Optional<Member> findByIdWithDetails(@Param("id") Long id);


    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.memberOtts LEFT JOIN FETCH m.interests WHERE m.email = :email")
    Optional<Member> findByEamilWithDetails(@Param("email") String email);


}
