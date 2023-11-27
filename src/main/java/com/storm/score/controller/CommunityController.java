package com.storm.score.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
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
    public ResponseEntity<List<Community>> getAllCommunities() {
        List<Community> communityList = communityDatabase;
        if (communityList != null) {
            return new ResponseEntity<>(communityList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<Community> getCommunityById(@PathVariable Long communityId) {
        for (Community community : communityDatabase) {
            if (community.getId() == communityId) {
                return new ResponseEntity<>(community, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
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
