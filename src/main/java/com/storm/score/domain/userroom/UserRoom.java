package com.storm.score.domain.userroom;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * description    :
 * packageName    : com.storm.score.domain.userroom
 * fileName       : UserRoom
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
@Builder
@RequiredArgsConstructor
public class UserRoom {
    private final long userId;
    private final long roomId;
}
