package com.elegion.domain.service;

import com.elegion.domain.ApiUtils;
import com.elegion.domain.model.user.User;
import com.elegion.domain.repository.ProfileRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;

public class ProfileServiceImpl implements ProfileService {
    @Inject
    @Named(ProfileRepository.SERVER)
    ProfileRepository mServerRepository;

    @Inject
    @Named(ProfileRepository.DB)
    ProfileRepository mDBRepository;

    @Inject
    public ProfileServiceImpl() {
    }

    @Override
    public Single<User> getUser(String username) {
        return mServerRepository.getUser(username)
                .doOnSuccess(mDBRepository::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mServerRepository.getUser(username).blockingGet() : null);
    }

    @Override
    public void insertUser(User user) {
        mDBRepository.insertUser(user);
    }
}
