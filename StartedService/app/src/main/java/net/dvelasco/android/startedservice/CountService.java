package net.dvelasco.android.startedservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class CountService extends Service {

    public static final String TAG = CountService.class.getName();
    public static final String CANONICAL_NAME = CountService.class.getCanonicalName();
    private static final String ACTION_COUNT_TO = CANONICAL_NAME + "COUNT_TO";
    private static final String EXTRA_COUNT_TARGET = CANONICAL_NAME + "COUNT_TARGET";


    public static void startCount(int countTarget, Context clientContext) {
        Intent requestIntent = new Intent(clientContext, CountService.class);
        requestIntent.setAction(ACTION_COUNT_TO);
        requestIntent.putExtra(EXTRA_COUNT_TARGET, countTarget);
        clientContext.startService(requestIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // no binding today, null is OK
        return null;
    }

}
