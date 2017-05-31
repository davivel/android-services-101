package net.dvelasco.android.startedservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the);

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int target = 0;
                String input = ((TextView)findViewById(R.id.numberInput)).getText().toString();
                if (input.length() > 0) {
                    target = Integer.parseInt(input);
                }
                CountService.startCount(target, TheActivity.this);
            }
        });

        LocalBroadcastManager broadcastManager =
            LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter(CountService.EVENT_COUNT_FINISHED);
        filter.addAction(CountService.EXTRA_COUNT_TARGET);
        broadcastManager.registerReceiver(
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String message = getString(
                        R.string.snackbar_result,
                        intent.getIntExtra(CountService.EXTRA_COUNT_TARGET, 0)
                    );
                    Snackbar snackbar = Snackbar.make(
                        findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_LONG
                    );
                    snackbar.show();
                }
            },
            filter
        );
    }



}
