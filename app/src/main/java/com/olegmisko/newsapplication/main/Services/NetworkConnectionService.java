package com.olegmisko.newsapplication.main.Services;

import java.io.IOException;

public class NetworkConnectionService {

    private static NetworkConnectionService instance = new NetworkConnectionService();

    private NetworkConnectionService() {
    }

    public static NetworkConnectionService getInstance() {
        return instance;
    }

    /* This method sends a ping so we can actually check not if we're only
     * connected to the Internet but if there is an actual living connection
      * so we can load some data */
    public static boolean checkInternetConnection() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
