package com.storm.score.controller;

import com.storm.score.domain.message.Message;
import io.swagger.annotations.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description    : Room Management System
 * packageName    : com.storm.score.controller
 * fileName       : RoomController
 * author         : senor14
 * date           : 2023/11/23
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/11/23        senor14       최초 생성
 */
@RestController
@RequestMapping("/api/v1/rooms")
@Api("Room Management System")
@ApiResponses(value = {
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class RoomController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example = "- roomId: 1\n  title: 샤마임\n" +
                    "- roomId: 2\n  title: 수요기도회\n" +
                    "- roomId: 3\n  title: 수련회\n",
            dataType = "List"
    )
    private static final List<Room> roomDatabase = new ArrayList<>();

    private static long roomId = 0;

    static {
        roomDatabase.add(new Room(++roomId, "샤마임", "123"));
        roomDatabase.add(new Room(++roomId, "수요기도회", "123"));
        roomDatabase.add(new Room(++roomId, "수련회", "123"));
    }

    @Getter
    @Setter
    @Builder
    private static class Room {
        private Long roomId;
        private String title;
        private String password;
    }

    @Getter
    @Setter
    @Builder
    private static class RoomValidationResponseDTO {
        private String message;
        private boolean success;
    }


    // ####################################

    @GetMapping()
    @ApiOperation(value = "방 목록 조회", notes = "사용 가능한 방 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all rooms",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- roomId: 1\n  title: 샤마임\n" +
                                            "- roomId: 2\n  title: 수요기도회\n" +
                                            "- roomId: 3\n  title: 수련회"
                            )
                    )
            )
    })
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> roomList = roomDatabase;
        if (roomList != null) {
            return new ResponseEntity<>(roomList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{roomId}")
    @ApiOperation(value = "방 정보 조회", notes = "특정 방의 정보를 조회")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "roomId", value = "방 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved room",
                    response = Room.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- roomId: 1\n  title: 샤마임\n"
                            )
                    )
            )
    })
    public ResponseEntity<Room> getRoomByRoomId(@PathVariable Long roomId) {
        for (Room room : roomDatabase) {
            if (room.getRoomId() == roomId) {
                return new ResponseEntity<>(room, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ApiOperation(value = "방 등록", notes = "새로운 방을 등록")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", value = "방 제목", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully created room",
                    response = Room.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- roomId: 1\n  title: 샤마임"
                            )
                    )
            )
    })
    public ResponseEntity<Room> createRoom(@RequestParam String title) {
        roomDatabase.add(Room
                .builder()
                .roomId(++roomId)
                .title(title)
                .build());
        for (Room room : roomDatabase) {
            if (room.getRoomId() == roomId) {
                return new ResponseEntity<>(room, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{roomId}")
    @ApiOperation(value = "방 수정", notes = "방의 정보를 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "방 아이디", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "title", value = "방 제목", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully updated room",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Room updated successfully"
                            )
                    )
            )
    })
    public ResponseEntity<String> updateRoomyByRoomId(@PathVariable Long roomId,
                                                      @RequestParam String title) {
        for (Room room : roomDatabase) {
            if (room.getRoomId() == roomId) {
                room.setTitle(title);
                return new ResponseEntity<>("Room updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{roomId})")
    @ApiOperation(value = "방 삭제", notes = "방의 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "방 아이디", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully deleted room",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Room deleted successfully"
                            )
                    )
            )
    })
    public ResponseEntity<String> deleteRoomByRoomId(@PathVariable Long roomId) {
        for (Room room : roomDatabase) {
            if (room.getRoomId() == roomId) {
                roomDatabase.remove(room);
                return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sorted-by-latest")
    @ApiOperation(value = "방 목록 최신 정렬 조회", notes = "방 목록을 최신순으로 정렬 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved room",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- roomId: 1\n  title: 샤마임\n" +
                                            "- roomId: 2\n  title: 수요기도회\n" +
                                            "- roomId: 3\n  title: 수련회"
                            )
                    )
            )
    })
    public ResponseEntity<List<Room>> getLatestRooms() {
        if (roomDatabase.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roomDatabase, HttpStatus.OK);
    }

    @GetMapping("/sorted-by-name")
    @ApiOperation(value = "방 목록 이름 정렬 조회", notes = "방 목록을 이름순으로 정렬 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved room",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- roomId: 1\n  title: 샤마임\n" +
                                            "- roomId: 2\n  title: 수요기도회\n" +
                                            "- roomId: 3\n  title: 수련회"
                            )
                    )
            )
    })
    public ResponseEntity<List<Room>> getRoomsSortedByName() {
        if (roomDatabase.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roomDatabase, HttpStatus.OK);
    }

    @PostMapping("/validate-password")
    @ApiOperation(value = "방 비밀번호 검증 로직", notes = "방의 비밀번호가 유효한지 검증")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "방 아이디", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "password", value = "방 비밀번호", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully validate room's password",
                    response = RoomValidationResponseDTO.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- message: 비밀번호 검증이 성공했습니다.\n  success: true"
                            )
                    )
            )
    })
    public ResponseEntity<RoomValidationResponseDTO> validateRoomPassword(@RequestParam Long roomId,
                                                    @RequestParam String password) {

        for (Room room : roomDatabase) {
            if (room.getRoomId() == roomId) {
                if (room.getPassword() == password) {
                    return new ResponseEntity<>(
                            RoomValidationResponseDTO.builder()
                                    .message("비밀번호 검증이 성공했습니다.")
                                    .success(true)
                                    .build(), HttpStatus.OK);
                }
                return new ResponseEntity<>(
                        RoomValidationResponseDTO.builder()
                                .message("비밀번호가 올바르지 않습니다. 다시 시도해주세요.")
                                .success(false)
                                .build(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{roomId}/messages")
    @ApiOperation(value = "방 별 메시지 목록 조희", notes = "특정 방의 메시지 목록을 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "방 아이디", required = true, dataType = "Long"),
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retreived thie room's messages",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- messageId: 1\n  userId: 1\n roomId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n" +
                                            "- messageId: 2\n  userId: 2\n roomId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n" +
                                            "- messageId: 3\n  userId: 3\n roomId: 1\n imageUrl: https://s3.[aws-region].amazonaws.com\n"
                            )
                    )
            )
    })
    public ResponseEntity<List<Message>> getRoomMessages(@PathVariable Long roomId) {
        List<Message> messages = new ArrayList<>();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
