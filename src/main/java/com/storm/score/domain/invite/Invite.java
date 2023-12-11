package com.storm.score.domain.invite;

import com.storm.score.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * description    :
 * packageName    : com.storm.score.domain.invite
 * fileName       : Invite
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
public class Invite extends BaseTime {
    private final Long inviteId;
    private final Long senderUserId;
    private final Long receiverUserId;
    private final Long roomId;
    private final Status status;

    @Builder
    public Invite(LocalDateTime createdAt, LocalDateTime updatedAt, Long inviteId, Long senderUserId, Long receiverUserId, Long roomId, Status status) {
        super(createdAt, updatedAt);
        this.inviteId = inviteId;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.roomId = roomId;
        this.status = status;
    }
}
