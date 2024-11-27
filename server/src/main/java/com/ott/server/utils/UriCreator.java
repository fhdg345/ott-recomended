package com.ott.server.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UriCreator {
    public static URI createUri(String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }


    // 출시일을 int로 변환하는 메서드
    public static int convertReleaseDateToInt(String releaseDate) {
        if (releaseDate == null || releaseDate.isEmpty() || releaseDate.equals("null")   || releaseDate.equals("0") ) {
            return 0; // 날짜가 없을 경우 0 또는 다른 적절한 기본값 설정
        }
        LocalDate date = LocalDate.parse(releaseDate); // 날짜 문자열을 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.parseInt(date.format(formatter)); // YYYYMMDD 형식의 int로 변환
    }
}



