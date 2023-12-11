package com.storm.score.domain.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * description    :
 * packageName    : com.storm.score.domain.message
 * fileName       : SaveYN
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
@RequiredArgsConstructor
public enum SaveYn {
    Y("SaveYn_Y", "저장됨"),
    N("SaveYn_N", "저장안됨");

    private final String key;
    private final String title;
}
