package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentialListByUserId(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " + "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password}" + "WHERE userid = #{userId} AND credentialid = #{credentialId}")
    boolean update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    boolean delete(Integer credentialId);
}
