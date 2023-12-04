package com.storm.score.controller;

import io.swagger.annotations.*;
import lombok.Builder;
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
 * fileName       : AlarmController
 * author         : senor14
 * date           : 2023-11-28
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-28        senor14       최초 생성
 */
@RestController
@RequestMapping("/api/v1/alarms")
@Api("Alarm Management System")
public class AlarmController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example =
                    "- alarmId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD\n" +
                    "- alarmId: 2\n  inviteId: 2\n  userId: 1\n  status: UNREAD\n" +
                    "- alarmId: 3\n  inviteId: 3\n  userId: 1\n  status: UNREAD",
            dataType = "List"
    )
    private static final List<Alarm> alarmDatabase = new ArrayList<>();

    private static long alarmId = 0;

    static {
        alarmDatabase.add(new Alarm(++alarmId, 1L, 1L, Alarm.STATUS.UNREAD));
        alarmDatabase.add(new Alarm(++alarmId, 2L, 1L, Alarm.STATUS.UNREAD));
        alarmDatabase.add(new Alarm(++alarmId, 3L, 1L, Alarm.STATUS.UNREAD));
    }

    @Getter
    @Setter
    @Builder
    private static class Alarm {
        private Long alarmId;
        private Long inviteId;
        private Long userId;
        private STATUS status;

        enum STATUS {
            READ,
            UNREAD
        }
    }

    // ####################################

    @GetMapping()
    @ApiOperation(value = "알림 목록 조회", notes = "사용 가능한 알림 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all alarms",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- alarmId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD\n" +
                                            "- alarmId: 2\n  inviteId: 2\n  userId: 1\n  status: UNREAD\n" +
                                            "- alarmId: 3\n  inviteId: 3\n  userId: 1\n  status: UNREAD"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<Alarm>> getAllAlarms() {
        List<Alarm> alarmList = alarmDatabase;
        if (alarmList != null) {
            return new ResponseEntity<>(alarmList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{alarmId}")
    @ApiOperation(value = "알림 정보 조회", notes = "특정 알림의 정보를 조회")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "alarmId", value = "알림 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved alarm",
                    response = Alarm.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- alarmId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Alarm> getAlarmByAlarmId(@PathVariable Long alarmId) {
        for (Alarm alarm : alarmDatabase) {
            if (alarm.getAlarmId() == alarmId) {
                return new ResponseEntity<>(alarm, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ApiOperation(value = "알림 등록", notes = "새로운 알림을 등록")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "inviteId", value = "초대 아이디", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully created alarm",
                    response = Alarm.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- alarmId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Alarm> createAlarm(@RequestParam Long inviteId,
                                             @RequestParam Long userId) {
        alarmDatabase.add(Alarm
                .builder()
                .alarmId(++alarmId)
                .inviteId(inviteId)
                .userId((userId))
                .status(Alarm.STATUS.UNREAD)
                .build());
        for (Alarm alarm : alarmDatabase) {
            if (alarm.getAlarmId() == alarmId) {
                return new ResponseEntity<>(alarm, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{alarmId}")
    @ApiOperation(value = "알림 읽음 상태 수정", notes = "알림의 읽음 상태 정보를 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alarmId", value = "알림 아이디", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully updated alarm",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Alarm updated successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<String> updateAlarmByAlarmId(@PathVariable Long alarmId) {
        for (Alarm alarm : alarmDatabase) {
            if (alarm.getAlarmId() == alarmId) {
                alarm.setStatus(Alarm.STATUS.READ);
                return new ResponseEntity<>("Alarm updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{alarmId}")
    @ApiOperation(value = "알림 삭제", notes = "알림의 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alarmId", value = "알림 아이디", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully deleted alarm",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Alarm deleted successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<String> deleteAlarmByAlarmId(@PathVariable Long alarmId) {
        for (Alarm alarm : alarmDatabase) {
            if (alarm.getAlarmId() == alarmId) {
                alarmDatabase.remove(alarm);
                return new ResponseEntity<>("Alarm deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
