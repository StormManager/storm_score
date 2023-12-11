package com.storm.score.domain.alarm;

import com.storm.score.domain.BaseTime;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * description    :
 * packageName    : com.storm.score.domain.alarm
 * fileName       : Alarm
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
public class Alarm extends BaseTime {
    private final Long alarmId;
    private final Long inviteId;
    private final Long userId;
    private final Status status;

    public Alarm(LocalDateTime createdAt, LocalDateTime updatedAt, Long alarmId, Long inviteId, Long userId, Status status) {
        super(createdAt, updatedAt);
        this.alarmId = alarmId;
        this.inviteId = inviteId;
        this.userId = userId;
        this.status = status;
    }
}
