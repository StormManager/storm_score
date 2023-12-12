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
 * fileName       : NotificationController
 * author         : senor14
 * date           : 2023-11-28
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-28        senor14       최초 생성
 */
@RestController
@RequestMapping("/api/v1/notifications")
@Api("Notification Management System")
@ApiResponses(value = {
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class NotificationController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example =
                    "- notificationId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD\n" +
                    "- notificationId: 2\n  inviteId: 2\n  userId: 1\n  status: UNREAD\n" +
                    "- notificationId: 3\n  inviteId: 3\n  userId: 1\n  status: UNREAD",
            dataType = "List"
    )
    private static final List<Notification> notificationDatabase = new ArrayList<>();

    private static long notificationId = 0;

    static {
        notificationDatabase.add(new Notification(++notificationId, 1L, 1L, Notification.STATUS.UNREAD));
        notificationDatabase.add(new Notification(++notificationId, 2L, 1L, Notification.STATUS.UNREAD));
        notificationDatabase.add(new Notification(++notificationId, 3L, 1L, Notification.STATUS.UNREAD));
    }

    @Getter
    @Setter
    @Builder
    private static class Notification {
        private Long notificationId;
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
                    message = "Successfully retrieved all notifications",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- notificationId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD\n" +
                                            "- notificationId: 2\n  inviteId: 2\n  userId: 1\n  status: UNREAD\n" +
                                            "- notificationId: 3\n  inviteId: 3\n  userId: 1\n  status: UNREAD"
                            )
                    )
            )
    })
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notificationList = notificationDatabase;
        if (notificationList != null) {
            return new ResponseEntity<>(notificationList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{notificationId}")
    @ApiOperation(value = "알림 정보 조회", notes = "특정 알림의 정보를 조회")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "notificationId", value = "알림 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved Notification",
                    response = Notification.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- notificationId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD"
                            )
                    )
            )
    })
    public ResponseEntity<Notification> getNotificationByNotificationId(@PathVariable Long notificationId) {
        for (Notification notification : notificationDatabase) {
            if (notification.getNotificationId() == notificationId) {
                return new ResponseEntity<>(notification, HttpStatus.OK);
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
                    message = "Successfully created notification",
                    response = Notification.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- notificationId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD"
                            )
                    )
            )
    })
    public ResponseEntity<Notification> createNotification(@RequestParam Long inviteId,
                                             @RequestParam Long userId) {
        notificationDatabase.add(Notification
                .builder()
                .notificationId(++notificationId)
                .inviteId(inviteId)
                .userId((userId))
                .status(Notification.STATUS.UNREAD)
                .build());
        for (Notification notification : notificationDatabase) {
            if (notification.getNotificationId() == notificationId) {
                return new ResponseEntity<>(notification, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{notificationId}")
    @ApiOperation(value = "알림 읽음 상태 수정", 
            notes = "알림의 읽음 상태 정보를 수정\n" +
                    "[ UNREAD(안읽음) => READ(읽음) ]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notificationId", value = "알림 아이디", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully updated notification",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Notification updated successfully"
                            )
                    )
            )
    })
    public ResponseEntity<String> updateNotificationByNotificationId(@PathVariable Long notificationId) {
        for (Notification notification : notificationDatabase) {
            if (notification.getNotificationId() == notificationId) {
                notification.setStatus(Notification.STATUS.READ);
                return new ResponseEntity<>("notification updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{notificationId}")
    @ApiOperation(value = "알림 삭제", notes = "알림의 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notificationId", value = "알림 아이디", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully deleted notification",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Notification deleted successfully"
                            )
                    )
            )
    })
    public ResponseEntity<String> deleteNotificationByNotificationId(@PathVariable Long notificationId) {
        for (Notification notification : notificationDatabase) {
            if (notification.getNotificationId() == notificationId) {
                notificationDatabase.remove(notification);
                return new ResponseEntity<>("notification deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
