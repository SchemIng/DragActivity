package org.scheming.drag;

import android.support.v7.app.AppCompatActivity;

public class DragActivity extends AppCompatActivity implements DragLinearLayout.OnFinishListener {

    @Override
    public void setContentView(int layoutResID) {
        DragLinearLayout dragLinearLayout = new DragLinearLayout(this);
        dragLinearLayout.setOnFinishListener(this);
        setContentView(getLayoutInflater().inflate(layoutResID, dragLinearLayout, true));
    }

    @Override
    public void onFinish() {
        finish();
        overridePendingTransition(0, 0);
    }
}
