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
 * fileName       : MessageController
 * author         : senor14
 * date           : 2023-11-28
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-28        senor14       최초 생성
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    private static final List<Message> messageDatabase = new ArrayList<>();

    private static long messageId = 0;

    static {
        messageDatabase.add(new Message(++messageId, 1L, 1L, "https://s3.[aws-region].amazonaws.com/[bucket name]"));
        messageDatabase.add(new Message(++messageId, 2L, 1L, "https://s3.[aws-region].amazonaws.com/[bucket name]"));
        messageDatabase.add(new Message(++messageId, 3L, 1L, "https://s3.[aws-region].amazonaws.com/[bucket name]"));
    }

    @Getter
    @Setter
    private static class Message {
        private Long id;
        private Long senderUserId;
        private Long receiverCommunityId;
        private String imageUrl;

        public Message(Long id, Long senderUserId, Long receiverCommunityId, String imageUrl) {
            this.id = id;
            this.senderUserId = senderUserId;
            this.receiverCommunityId = receiverCommunityId;
            this.imageUrl = imageUrl;
        }
    }

    // ####################################

    @GetMapping()
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messageList = messageDatabase;
        if (messageList != null) {
            return new ResponseEntity<>(messageList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long messageId) {
        for (Message message : messageDatabase) {
            if (message.getId() == messageId) {
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Message> createMessage(@RequestParam Long senderUserId,
                                                 @RequestParam Long receiverCommunityId,
                                                 @RequestParam String imageUrl) {
        messageDatabase.add(new Message(++messageId, senderUserId, receiverCommunityId, imageUrl));
        for (Message message : messageDatabase) {
            if (message.getId() == messageId) {
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Long messageId) {
        for (Message message : messageDatabase) {
            if (message.getId() == messageId) {
                messageDatabase.remove(message);
                return new ResponseEntity<>("Message deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
