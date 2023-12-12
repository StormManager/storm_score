package com.storm.score.domain.invite;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * description    :
 * packageName    : com.storm.score.domain.invite
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
    PENDING("STATUS_PENDING", "보류"),
    ACCEPT("STATUS_ACCEPT", "수락"),
    REJECT("STATUS_REJECT", "거절");

    private final String key;
    private final String title;
}
