package com.storm.score.domain.message;

import com.storm.score.domain.BaseTime;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * description    :
 * packageName    : com.storm.score.domain.message
 * fileName       : Message
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
public class Message extends BaseTime {
    private final Long messageId;
    private final Long userId;
    private final Long roomId;
    private final String imageUrl;
    private final SaveYn saveYn;

    public Message(LocalDateTime createdAt, LocalDateTime updatedAt, Long messageId, Long userId, Long roomId, String imageUrl, SaveYn saveYn) {
        super(createdAt, updatedAt);
        this.messageId = messageId;
        this.userId = userId;
        this.roomId = roomId;
        this.imageUrl = imageUrl;
        this.saveYn = saveYn;
    }
}
