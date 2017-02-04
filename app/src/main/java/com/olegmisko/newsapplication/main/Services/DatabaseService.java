package com.olegmisko.newsapplication.main.Services;




/* Singleton class for Realm database operations handling */

import com.olegmisko.newsapplication.main.Models.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class DatabaseService {

    private static DatabaseService sharedInstance = null;
    private static Realm realmDataBase = Realm.getDefaultInstance();


    private DatabaseService() {
    }

    public DatabaseService getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new DatabaseService();
        }
        return sharedInstance;
    }

    public Realm getRealmInstance() {
        return realmDataBase;
    }

    public void writeToDataBase(String username, String password) {
        realmDataBase.beginTransaction();
        User user = realmDataBase.createObject(User.class);
        user.setUsername(username);
        user.setPassword(password);
        realmDataBase.commitTransaction();
    }

    public boolean checkCredentials(String username, String password) {
        RealmResults<User> results = realmDataBase.where(User.class)
                .beginGroup()
                .equalTo("username", username)
                .equalTo("password", password)
                .endGroup()
                .findAll();

        if (results.size() != 0) return true;
        else return false;
    }


}
