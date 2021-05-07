package com.elegion.domain.service;

import com.elegion.domain.model.user.User;

import io.reactivex.Single;

public interface ProfileService {

    Single<User> getUser(String username);

    void insertUser(User user);
}
