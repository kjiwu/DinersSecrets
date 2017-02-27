package com.starter.dinerssecrets.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.managers.NetwrokManager;

public class STSplashActivity extends STBaseActivity {

    private Button mButtonTestNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_splash);

        mButtonTestNetwork = (Button) findViewById(R.id.button_test_network);
        mButtonTestNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}