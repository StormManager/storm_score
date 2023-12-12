package com.storm.score.domain.notification;

import com.storm.score.domain.BaseTime;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * description    :
 * packageName    : com.storm.score.domain.Notification
 * fileName       : Notification
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
public class Notification extends BaseTime {
    private final Long notificationId;
    private final Long inviteId;
    private final Long userId;
    private final Status status;

    public Notification(LocalDateTime createdAt, LocalDateTime updatedAt, Long notificationId, Long inviteId, Long userId, Status status) {
        super(createdAt, updatedAt);
        this.notificationId = notificationId;
        this.inviteId = inviteId;
        this.userId = userId;
        this.status = status;
    }
}
