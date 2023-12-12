package com.storm.score.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * description    :
 * packageName    : com.storm.score.domain
 * fileName       : BaseTime
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
@RequiredArgsConstructor
public class BaseTime {
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
