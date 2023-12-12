package com.storm.score.domain.room;

import com.storm.score.domain.BaseTime;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * description    :
 * packageName    : com.storm.score.domain.room
 * fileName       : Room
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
public class Room extends BaseTime {
    private final Long roomId;
    private final Long createdUserId;
    private final String title;
    private final String password;
    private final Integer maxCapacity;

    public Room(LocalDateTime createdAt, LocalDateTime updatedAt, Long roomId, Long createdUserId, String title, String password, Integer maxCapacity) {
        super(createdAt, updatedAt);
        this.roomId = roomId;
        this.createdUserId = createdUserId;
        this.title = title;
        this.password = password;
        this.maxCapacity = maxCapacity;
    }
}
