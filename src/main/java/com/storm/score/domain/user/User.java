package com.storm.score.domain.user;

import com.storm.score.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * description    :
 * packageName    : com.storm.score.domain.user
 * fileName       : User
 * author         : senor14
 * date           : 2023-12-11
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-11        senor14       최초 생성
 */
@Getter
public class User extends BaseTime {

    private final Long userId;
    private final String userUUID;
    private final String nickname;
    private final String email;
    private final String password;
    private final String profileImage;
    private final Role role;

    @Builder
    public User(LocalDateTime createdAt, LocalDateTime updatedAt, Long userId, String userUUID, String nickname, String email, String password, String profileImage, Role role) {
        super(createdAt, updatedAt);
        this.userId = userId;
        this.userUUID = userUUID;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
    }
}
