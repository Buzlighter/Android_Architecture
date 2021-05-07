package com.elegion.test.behancer.di;

import androidx.room.Room;

import com.elegion.data.database.BehanceDao;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.data.database.BehanceDatabase;

import toothpick.config.Module;

/**
 * Created by tanchuev on 23.04.2018.
 */

public class AppModule extends Module {

    private final AppDelegate mApp;

    public AppModule(AppDelegate app) {
        this.mApp = app;
        bind(AppDelegate.class).toInstance(mApp);
        bind(BehanceDao.class).toInstance(provideBehanceDao());
    }

    AppDelegate provideApp() {
        return mApp;
    }


    private BehanceDatabase behanceDatabase() {
        return Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    private BehanceDao provideBehanceDao() {
        return behanceDatabase().getBehanceDao();
    }

}
