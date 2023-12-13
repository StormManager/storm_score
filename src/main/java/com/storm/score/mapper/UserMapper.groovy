package com.storm.score.mapper

import com.storm.score.domain.user.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select


/**
 */
/**
 * description    :
 * packageName    : com.storm.score.mapper
 * fileName       : UserMapper
 * author         : wammelier
 * date           : 2023/12/13
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/13        wammelier       최초 생성
 */
@Mapper
interface UserMapper {
    @Select("""
        select * from USER
            """)
    User getUser()

    @Insert("""
        insert into 
        USER (USER_ID, USER_UUID, NICKNAME, EMAIL, PASSWORD, PROFILE_IMAGE) 
        VALUES ('112124123412312312', 'asdofijweoijawef', 'wammelier', 'sw4417@naver.com', '123123', 'asdfasdf')
            """)
    void insertUser()


}