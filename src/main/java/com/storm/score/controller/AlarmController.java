package com.storm.score.controller;

import io.swagger.annotations.ApiModelProperty;
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
@RequestMapping("/api/alarms")
public class AlarmController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example = "- alarmId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD\n" +
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
    public ResponseEntity<List<Alarm>> getAllAlarms() {
        List<Alarm> alarmList = alarmDatabase;
        if (alarmList != null) {
            return new ResponseEntity<>(alarmList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{alarmId}")
    public ResponseEntity<Alarm> getAlarmByAlarmId(@PathVariable Long alarmId) {
        for (Alarm alarm : alarmDatabase) {
            if (alarm.getAlarmId() == alarmId) {
                return new ResponseEntity<>(alarm, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
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
