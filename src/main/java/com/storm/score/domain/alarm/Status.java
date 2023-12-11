package com.storm.score.domain.alarm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * description    :
 * packageName    : com.storm.score.domain.alarm
 * fileName       : STATUS
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
@RequiredArgsConstructor
public enum Status {
    READ("STATUS_READ", "읽음"),
    UNREAD("STATUS_UNREAD", "안읽음");

    private final String key;
    private final String title;
}
