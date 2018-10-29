package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import utils.FirebaseListeners;
import utils.Utils;

/**
 * Created by applify on 8/28/2017.
 */

public class FirebaseChatService extends Service {

    Utils utils;

    @Override
    public void onCreate() {
        utils = new Utils(getApplicationContext());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (utils.getInt("status", 0)==2) {
            FirebaseListeners.getInstance().intializeDataBase(getApplicationContext());
            FirebaseListeners.getInstance().startProfileListener(utils.getString("user_id", ""));
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
