package com.storm.score.controller;

import io.swagger.annotations.*;
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
@Api("Score Management System")
public class ScoreController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example =
                    "- id: 1\n  title: 정직한 예배\n singer: 제이어스 J-US\n instrument: G\n" +
                            "- id: 2\n  title: 성령의 바람\n singer: 제이어스 J-US\n instrument: B♭\n" +
                            "- id: 3\n  title: 주의 사랑으로\n singer: 제이어스 J-US\n instrument: D\n",
            dataType = "List"
    )
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
    @ApiOperation(value = "악보 목록 조회", notes = "사용 가능한 악보 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all scores",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value =
                                            "- id: 1\n  title: 정직한 예배\n singer: 제이어스 J-US\n instrument: G\n" +
                                                    "- id: 2\n  title: 성령의 바람\n singer: 제이어스 J-US\n instrument: B♭\n" +
                                                    "- id: 3\n  title: 주의 사랑으로\n singer: 제이어스 J-US\n instrument: D\n"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<Score>> getAllScores() {
        List<Score> scoreList = scoreDatabase;
        if (scoreList != null) {
            return new ResponseEntity<>(scoreList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{scoreId}")
    @ApiOperation(value = "악보 정보 조회", notes = "특정 악보의 정보를 조회")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "scoreId", value = "악보 번호", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved score",
                    response = Score.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  title: 정직한 예배\n singer: 제이어스 J-US\n instrument: G\n"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Score> getScoreById(@PathVariable Long scoreId) {
        for (Score score : scoreDatabase) {
            if (score.getId() == scoreId) {
                return new ResponseEntity<>(score, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ApiOperation(value = "악보 등록", notes = "새로운 악보를 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "악보 제목", required = true, dataType = "String"),
            @ApiImplicitParam(name = "singer", value = "가수 이름", required = true, dataType = "String"),
            @ApiImplicitParam(name = "instrument", value = "코드(키)", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully created score",
                    response = Score.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  title: 정직한 예배\n singer: 제이어스 J-US\n instrument: G\n"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    @ApiOperation(value = "악보 수정", notes = "악보의 정보를 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scoreId", value = "악보 번호", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "title", value = "악보 제목", required = true, dataType = "String"),
            @ApiImplicitParam(name = "singer", value = "가수 이름", required = true, dataType = "String"),
            @ApiImplicitParam(name = "instrument", value = "코드(키)", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully updated score",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Score updated successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    @ApiOperation(value = "악보 삭제", notes = "악보의 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scoreId", value = "악보 번호", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully deleted score",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Score deleted successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
