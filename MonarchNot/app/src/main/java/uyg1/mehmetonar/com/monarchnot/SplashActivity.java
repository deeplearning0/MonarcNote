package uyg1.mehmetonar.com.monarchnot;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.net.InetAddress;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout layout;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        layout = (LinearLayout) findViewById(R.id.splashLayout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        handler = new Handler();

    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNetworkConnected() || isInternetAvailable()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(layout, R.string.net_fail_message, Snackbar.LENGTH_INDEFINITE).setAction(R.string.open_net, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                    startActivityForResult(intent, 1);

                                }
                            });
                    snackbar.show();
                }

            }
        }, 2500);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("http://mehmetonar.com/");

            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        Log.d("RESULT", "" + requestCode);
        if (requestCode == 1) {
            onStart();
        }
    }
}
