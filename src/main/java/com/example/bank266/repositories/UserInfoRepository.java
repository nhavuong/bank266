package com.example.bank266.repositories;

import com.example.bank266.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userInfoRepository")
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {

    @Query("FROM UserInfo WHERE name = :userName")
    public List<UserInfo> searchUserByName(@Param("userName") String userName);
}
