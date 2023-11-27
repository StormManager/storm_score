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
 * description    : Community Management System
 * packageName    : com.storm.score.controller
 * fileName       : CommunityController
 * author         : senor14
 * date           : 2023/11/23
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/11/23        senor14       최초 생성
 */
@RestController
@RequestMapping("/api/communities")
@Api("Community Management System")
public class CommunityController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example = "- id: 1\n  name: 샤마임\n" +
                    "- id: 2\n  name: 수요기도회\n" +
                    "- id: 3\n  name: 수련회",
            dataType = "List"
    )
    private static final List<Community> communityDatabase = new ArrayList<>();

    private static long communityId = 0;

    static {
        communityDatabase.add(new Community(++communityId, "샤마임"));
        communityDatabase.add(new Community(++communityId, "수요기도회"));
        communityDatabase.add(new Community(++communityId, "수련회"));
    }

    @Getter
    @Setter
    private static class Community {
        private Long id;
        private String name;

        public Community(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    // ####################################

    @GetMapping()
    @ApiOperation(value = "커뮤니티 목록 조회", notes = "사용 가능한 커뮤니티 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all communities",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  name: 샤마임\n" +
                                            "- id: 2\n  name: 수요기도회\n" +
                                            "- id: 3\n  name: 수련회"
                            )
                    )

            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<Community>> getAllCommunities() {
        List<Community> communityList = communityDatabase;
        if (communityList != null) {
            return new ResponseEntity<>(communityList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{communityId}")
    @ApiOperation(value = "커뮤니티 정보 조회", notes = "특정 커뮤니티의 정보를 조회")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "communityId", value = "커뮤니티 번호", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved community",
                    response = Community.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  name: 샤마임"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Community> getCommunityById(@PathVariable Long communityId) {
        for (Community community : communityDatabase) {
            if (community.getId() == communityId) {
                return new ResponseEntity<>(community, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ApiOperation(value = "커뮤니티 등록", notes = "새로운 커뮤니티를 등록")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully created community",
                    response = Community.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  name: 샤마임"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Community> createCommunity(@RequestParam String name) {
        communityDatabase.add(new Community(++communityId, name));
        for (Community community : communityDatabase) {
            if (community.getId() == communityId) {
                return new ResponseEntity<>(community, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{communityId}")
    @ApiOperation(value = "커뮤니티 수정", notes = "커뮤니티의 정보를 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "커뮤니티 번호", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "커뮤니티 이름", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully updated community",
                    response = Community.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Community updated successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<String> updateCommunityById(@PathVariable Long communityId,
                                                      @RequestParam String name) {
        for (Community community : communityDatabase) {
            if (community.getId() == communityId) {
                community.setName(name);
                return new ResponseEntity<>("Community updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{communityId})")
    @ApiOperation(value = "커뮤니티 삭제", notes = "커뮤니티의 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "커뮤니티 번호", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "커뮤니티 이름", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully deleted community",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "Community deleted successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<String> deleteCommunityById(@PathVariable Long communityId) {
        for (Community community : communityDatabase) {
            if (community.getId() == communityId) {
                communityDatabase.remove(community);
                return new ResponseEntity<>("Community deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
