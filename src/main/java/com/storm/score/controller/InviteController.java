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
 * fileName       : InviteController
 * author         : senor14
 * date           : 2023-12-02
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-02        senor14       최초 생성
 */
@RestController
@RequestMapping("/api/v1/invites")
@Api("Invite Management System")
@ApiResponses(value = {
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class InviteController {
    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example =
                    "- inviteId: 1\n  senderUserId: 1\n  receiverUserId: 2\n,  roomId: 1\n  status: PENDING\n" +
                    "- inviteId: 2\n  senderUserId: 1\n  receiverUserId: 3\n,  roomId: 1\n  status: PENDING\n" +
                    "- inviteId: 3\n  senderUserId: 1\n  receiverUserId: 4\n,  roomId: 1\n  status: PENDING\n",
            dataType = "List"
    )
    private static final List<Invite> inviteDatabase = new ArrayList<>();

    private static long inviteId = 0;

    static {
        inviteDatabase.add(new Invite(++inviteId, 1L, 2L, 1L, Invite.STATUS.PENDING));
        inviteDatabase.add(new Invite(++inviteId, 1L, 3L, 2L, Invite.STATUS.PENDING));
        inviteDatabase.add(new Invite(++inviteId, 1L, 4L, 3L, Invite.STATUS.PENDING));
    }

    @Getter
    @Setter
    @Builder
    private static class Invite {
        private Long inviteId;
        private Long senderUserId;
        private Long receiverUserId;
        private Long roomId;
        private STATUS status;

        enum STATUS {
            PENDING,
            ACCEPT,
            REJECT
        }
    }

    // ####################################

    @GetMapping()
    @ApiOperation(value = "초대 목록 조회", notes = "사용 가능한 초대 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all invites",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value =
                                            "- inviteId: 1\n  senderUserId: 1\n  receiverUserId: 2\n,  roomId: 1\n  status: PENDING\n" +
                                            "- inviteId: 2\n  senderUserId: 1\n  receiverUserId: 3\n,  roomId: 1\n  status: PENDING\n" +
                                            "- inviteId: 3\n  senderUserId: 1\n  receiverUserId: 4\n,  roomId: 1\n  status: PENDING\n"
                            )
                    )
            )
    })
    public ResponseEntity<List<Invite>> getAllInvites() {
        List<Invite> inviteList = inviteDatabase;
        if (inviteList != null) {
            return new ResponseEntity<>(inviteList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{inviteId}")
    @ApiOperation(value = "초대 정보 조회", notes = "특정 초대의 정보를 조회")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "inviteId", value = "초대 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved invite",
                    response = Invite.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- inviteId: 1\n  senderUserId: 1\n  receiverUserId: 2\n,  roomId: 1\n  status: PENDING\n"
                            )
                    )
            )
    })
    public ResponseEntity<Invite> getInviteByInviteId(@PathVariable Long inviteId) {
        for (Invite invite : inviteDatabase) {
            if (invite.getInviteId() == inviteId) {
                return new ResponseEntity<>(invite, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ApiOperation(value = "초대 등록", notes = "새로운 초대를 등록")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "senderUserId", value = "발신 회원 아이디", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "receiverUserId", value = "수신 회원 아이디", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "roomId", value = "방 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully created invite",
                    response = Invite.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- inviteId: 1\n  senderUserId: 1\n  receiverUserId: 2\n,  roomId: 1\n  status: PENDING\n"
                            )
                    )
            )
    })
    public ResponseEntity<Invite> createInvite(@RequestParam Long senderUserId,
                                               @RequestParam Long receiverUserId,
                                               @RequestParam Long roomId) {
        inviteDatabase.add(Invite
                .builder()
                .inviteId(++inviteId)
                .senderUserId(senderUserId)
                .receiverUserId(receiverUserId)
                .roomId(roomId)
                .build());
        for (Invite invite : inviteDatabase) {
            if (invite.getInviteId() == inviteId) {
                return new ResponseEntity<>(invite, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{inviteId}")
    @ApiOperation(value = "초대 읽음 상태 수정",
            notes = "초대의 읽음 상태 정보를 수정\n" +
                    "[ PENDING(보류) => ACCEPT(수락) or PENDING(보류) => REJECT(거절) ]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inviteId", value = "초대 아이디", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "상태(PENDING, ACCEPT, REJECT)", required = true, dataType = "Enum")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully updated invite",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Invite updated successfully"
                            )
                    )
            )
    })
    public ResponseEntity<String> updateInviteByInviteId(@PathVariable Long inviteId,
                                                         @RequestParam Invite.STATUS status) {
        for (Invite invite : inviteDatabase) {
            if (invite.getInviteId() == inviteId) {
                invite.setStatus(status);
                return new ResponseEntity<>("Invite updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{inviteId}")
    @ApiOperation(value = "초대 삭제", notes = "초대의 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inviteId", value = "초대 아이디", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully deleted invite",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Invite deleted successfully"
                            )
                    )
            )
    })
    public ResponseEntity<String> deleteInviteByInviteId(@PathVariable Long inviteId) {
        for (Invite invite : inviteDatabase) {
            if (invite.getInviteId() == inviteId) {
                inviteDatabase.remove(invite);
                return new ResponseEntity<>("Invite deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
