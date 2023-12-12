package com.storm.score.domain.score;

import com.storm.score.domain.BaseTime;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * description    :
 * packageName    : com.storm.score.domain.score
 * fileName       : Score
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
public class Score extends BaseTime {
    private final Long scoreId;
    private final Long messageId;
    private final String title;
    private final String instrument;
    private final String singer;
    private final String imageUrl;

    public Score(LocalDateTime createdAt, LocalDateTime updatedAt, Long scoreId, Long messageId, String title, String instrument, String singer, String imageUrl) {
        super(createdAt, updatedAt);
        this.scoreId = scoreId;
        this.messageId = messageId;
        this.title = title;
        this.instrument = instrument;
        this.singer = singer;
        this.imageUrl = imageUrl;
    }
}
