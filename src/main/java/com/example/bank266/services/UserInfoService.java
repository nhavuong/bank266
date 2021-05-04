package com.example.bank266.services;

import com.example.bank266.UserInfo;

import java.util.List;

public interface UserInfoService {
    public Iterable<UserInfo> findAll();

    public List<UserInfo> searchUserByName(String userName);

    public UserInfo save(UserInfo userInfo);

}
