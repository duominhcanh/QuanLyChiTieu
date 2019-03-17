package com.hutech.quanlychitieu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting);
    }

    public void btnCancelClicked(View view) {
        finish();
    }

    public void btnAcceptClicked(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        bundle.putInt("test", 1);

        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
