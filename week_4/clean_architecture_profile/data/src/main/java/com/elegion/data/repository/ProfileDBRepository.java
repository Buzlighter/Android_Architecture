package com.elegion.data.repository;

import com.elegion.data.database.BehanceDao;
import com.elegion.domain.model.user.Image;
import com.elegion.domain.model.user.User;
import com.elegion.domain.repository.ProfileRepository;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

public class ProfileDBRepository implements ProfileRepository {

    @Inject
    BehanceDao mBehanceDao;

    @Inject
    public ProfileDBRepository() {
    }

    @Override
    public Single<User> getUser(final String username) {
        return Single.fromCallable(new Callable<User>() {
            @Override
            public User call() {
                User user = mBehanceDao.getUserByName(username);
                Image image = mBehanceDao.getImageFromUser(user.getId());
                user.setImage(image);
                return user;
            }
        });
    }

    @Override
    public void insertUser(User user) {
        Image image = user.getImage();
        image.setId(user.getId());
        image.setUserId(user.getId());
        mBehanceDao.insertUser(user);
        mBehanceDao.insertImage(image);
    }
}
