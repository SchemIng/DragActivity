package org.scheming.parallax;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(FirstActivity.this);
            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SecondActivity.class);
        context.startActivity(starter);
    }
}
