package com.storm.score.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * description    :
 * packageName    : com.storm.score.controller
 * fileName       : ScoreController
 * author         : senor14
 * date           : 2023-11-28
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-28        senor14       최초 생성
 */
@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    private static final List<Score> scoreDatabase = new ArrayList<>();

    private static long scoreId = 0;

    static {
        scoreDatabase.add(new Score(++scoreId, "정직한 예배", "제이어스 J-US", "G"));
        scoreDatabase.add(new Score(++scoreId, "성령의 바람회", "제이어스 J-US", "B♭"));
        scoreDatabase.add(new Score(++scoreId, "주의 사랑으로", "제이어스 J-US", "D"));
    }

    @Getter
    @Setter
    private static class Score {
        private Long id;
        private String title;
        private String singer;
        private String instrument;

        public Score(Long id, String title, String singer, String instrument) {
            this.id = id;
            this.title = title;
            this.singer = singer;
            this.instrument = instrument;
        }
    }

    // ####################################

    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        List<Score> scoreList = scoreDatabase;
        if (scoreList != null) {
            return new ResponseEntity<>(scoreList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{scoreId}")
    public ResponseEntity<Score> getScoreById(@PathVariable Long scoreId) {
        for (Score score : scoreDatabase) {
            if (score.getId() == scoreId) {
                return new ResponseEntity<>(score, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Score> createScore(@RequestParam String title,
                                             @RequestParam String singer,
                                             @RequestParam String instrument) {
        scoreDatabase.add(new Score(++scoreId, title, singer, instrument));
        for (Score score : scoreDatabase) {
            if (score.getId() == scoreId) {
                return new ResponseEntity<>(score, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{scoreId}")
    public ResponseEntity<String> updateScoreById(@PathVariable Long scoreId,
                                                  @RequestParam String title,
                                                  @RequestParam String singer,
                                                  @RequestParam String instrument) {
        for (Score score : scoreDatabase) {
            if (score.getId() == scoreId) {
                score.setTitle(title);
                score.setSinger(singer);
                score.setInstrument(instrument);
                return new ResponseEntity<>("Score updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{scoreId}")
    public ResponseEntity<String> deleteScoreById(@PathVariable Long scoreId) {
        for (Score score : scoreDatabase) {
            if (score.getId() == scoreId) {
                scoreDatabase.remove(score);
                return new ResponseEntity<>("Score deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
