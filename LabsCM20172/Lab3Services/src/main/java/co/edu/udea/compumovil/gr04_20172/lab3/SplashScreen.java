package co.edu.udea.compumovil.gr04_20172.lab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    private static final String CUSTOM_ACTION = "co.edu.udea.compumovil.gr04_20172.lab3.BOOT";

    SharedPreferences mPreferences;
    String email="";
    String password="";
    Boolean session=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mPreferences = getSharedPreferences("DATOS", 0);
        email = mPreferences.getString("email", null);
        password = mPreferences.getString("password", null);
        session = mPreferences.getBoolean("session", true);

        if(session) {
            if (email != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this, Navigation_Drawer.class);
                        startActivity(intent);
                        finish();
                    }
                }, 3000);
            }else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), Navigation_Drawer.class);
                        startActivity(intent);
                        finish();
                    }
                }, 3000);
            }
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
        startService();
    }

    public void startService() {

        Intent intent = new Intent();
        intent.setAction(CUSTOM_ACTION);
        sendBroadcast(intent);
    }
}
