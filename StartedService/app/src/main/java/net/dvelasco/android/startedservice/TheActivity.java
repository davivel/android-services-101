package net.dvelasco.android.startedservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the);

        ((Button)findViewById(R.id.startButton)).setOnClickListener(new View.OnClickListener() {
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
    }

}
