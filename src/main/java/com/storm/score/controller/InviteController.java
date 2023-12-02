package com.storm.score.controller;

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
public class InviteController {
    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    private static final List<Invite> inviteDatabase = new ArrayList<>();

    private static long inviteId = 0;

    static {
        inviteDatabase.add(new Invite(++inviteId, 1L, 2L, 1L, Invite.STATUS.HOLD));
        inviteDatabase.add(new Invite(++inviteId, 2L, 3L, 2L, Invite.STATUS.HOLD));
        inviteDatabase.add(new Invite(++inviteId, 3L, 1L, 3L, Invite.STATUS.HOLD));
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
            HOLD,
            ACCEPT,
            REJECT
        }
    }

    // ####################################

    @GetMapping()
    public ResponseEntity<List<Invite>> getAllInvites() {
        List<Invite> inviteList = inviteDatabase;
        if (inviteList != null) {
            return new ResponseEntity<>(inviteList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{inviteId}")
    public ResponseEntity<Invite> getInviteByInviteId(@PathVariable Long inviteId) {
        for (Invite invite : inviteDatabase) {
            if (invite.getInviteId() == inviteId) {
                return new ResponseEntity<>(invite, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
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

    @DeleteMapping("/{inviteId")
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
