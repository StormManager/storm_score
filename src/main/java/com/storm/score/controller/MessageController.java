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
@Api("Message Management System")
public class MessageController {

    //  ####### 도메인 추가시 삭제 요망#######
    @ApiModelProperty(
            example =
                    "- id: 1\n  senderUserId: 1\n receiverCommunityId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n" +
                            "- id: 2\n  senderUserId: 2\n receiverCommunityId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n" +
                            "- id: 3\n  senderUserId: 3\n receiverCommunityId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n",
            dataType = "List"
    )
    private static final List<Message> messageDatabase = new ArrayList<>();
    // 임시 데이터 저장소

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
    @ApiOperation(value = "메시지 목록 조회", notes = "사용 가능한 메시지 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all messages",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value =
                                            "- id: 1\n  senderUserId: 1\n receiverCommunityId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n" +
                                                    "- id: 2\n  senderUserId: 2\n receiverCommunityId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n" +
                                                    "- id: 3\n  senderUserId: 3\n receiverCommunityId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messageList = messageDatabase;
        if (messageList != null) {
            return new ResponseEntity<>(messageList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{messageId}")
    @ApiOperation(value = "메시지 정보 조회", notes = "특정 메시지의 정보를 조회")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "messageId", value = "메시지 번호", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved message",
                    response = Message.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  senderUserId: 1\n receiverCommunityId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Message> getMessageById(@PathVariable Long messageId) {
        for (Message message : messageDatabase) {
            if (message.getId() == messageId) {
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ApiOperation(value = "메시지 등록", notes = "새로운 메시지를 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "senderUserId", value = "보낸 회원 번호", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "receiverCommunityId", value = "받는 커뮤니티 번호", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "imageUrl", value = "악보 이미지의 주소", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully created Message",
                    response = Message.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  senderUserId: 1\n receiverCommunityId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    @ApiOperation(value = "메시지 삭제", notes = "메시지의 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "messageId", value = "메시지 번호", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully deleted message",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Message deleted successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
