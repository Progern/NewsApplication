package com.olegmisko.newsapplication.main.Services;


public class NetworkService {
    public static final RequestService API = RequestService.RETROFIT.create(RequestService.class);
}
