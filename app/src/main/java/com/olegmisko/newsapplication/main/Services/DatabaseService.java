package com.olegmisko.newsapplication.main.Services;




/* Singleton class for Realm database operations handling */

import android.content.Context;

import com.olegmisko.newsapplication.main.Models.User;

import io.realm.Realm;
import io.realm.RealmResults;


public class DatabaseService {

    private static DatabaseService sharedInstance = null;
    private static Realm realmDataBase;


    private DatabaseService() {
    }

    public static void initRealm(Context context) {
        realmDataBase.init(context);
        realmDataBase = Realm.getDefaultInstance();
    }

    public static DatabaseService getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new DatabaseService();
        }
        return sharedInstance;
    }

    public static Realm getRealmInstance() {
        return realmDataBase;
    }

    public static void writeToDataBase(String username, String password) {
        realmDataBase.beginTransaction();
        User user = realmDataBase.createObject(User.class);
        user.setUsername(username);
        user.setPassword(password);
        realmDataBase.commitTransaction();
    }

    public static boolean checkCredentials(String username, String password) {
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
