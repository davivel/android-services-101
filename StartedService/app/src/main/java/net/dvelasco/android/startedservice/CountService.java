package net.dvelasco.android.startedservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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

    private CountHandler mServiceHandler;

    private final class CountHandler extends Handler {

        CountHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "Current thread is " + Thread.currentThread().getName());

            Intent request = (Intent) msg.obj;
            if (ACTION_COUNT_TO.equals(request.getAction())) {
                int target = request.getIntExtra(EXTRA_COUNT_TARGET, 0);
                countTo(target);
                reportResult(target);
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }

        private void countTo(int target) {
            for (int i=0; i<target; i++) {
                try {
                    Thread.sleep(1000);
                    Log.i(TAG, "" + (i+1));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void reportResult(int target) {
            Toast.makeText(
                CountService.this,
                getString(R.string.toast_result, target),
                Toast.LENGTH_LONG
            ).show();
        }

    }

    @Override
    public void onCreate() {
        Log.i(TAG, "Creating...");

        HandlerThread backgroundThread = new HandlerThread(
            "CounterThread",
            Process.THREAD_PRIORITY_BACKGROUND
        );
        backgroundThread.start();

        mServiceHandler = new CountHandler(backgroundThread.getLooper());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        Log.i(TAG, "Current thread is " + Thread.currentThread().getName());

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // no binding today, null is OK
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Destroying...");
    }

}
