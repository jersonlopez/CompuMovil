package co.edu.udea.compumovil.gr04_20172.lab3;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.Timer;
import java.util.TimerTask;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class UpdateDB extends IntentService {


    public UpdateDB() {
        super("UpdateDB");
    }

    /*TimerTask timerTask;
    public UpdateDB() {
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_LONG).show();
        Log.d("###", "Servicio creado");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("TAG", "Servicio iniciado...");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Log.d("TAG", "Running");
            }
        }, 0, 15000);
    }






    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Servicio iniciado...");

        Timer timer = new Timer();
        timerTask=new TimerTask() {
            @Override
            public void run() {
                // Aquí pones la lógica de lo que vas a pedir cada tanto tiempo
                //Ó también puedes llamar un metodo con to esa lógica (Estetica) XD
                //Toast.makeText(getBaseContext(),"Me ejecuté", Toast.LENGTH_SHORT).show();

            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        return START_NOT_STICKY;
    }*/

}
