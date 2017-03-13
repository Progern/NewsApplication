package com.olegmisko.newsapplication.main.Services;


public class NetworkRequestService {
    public static final RequestService API = RequestService.RETROFIT.create(RequestService.class);
}
