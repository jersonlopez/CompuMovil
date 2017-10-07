package co.edu.udea.compumovil.gr04_20172.lab3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class UpdateDB extends Service {
    TimerTask timerTask;
    public UpdateDB() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_LONG).show();
        Log.d("EJEMPLOONBOOT", "Servicio creado");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Servicio iniciado...");

        Timer timer = new Timer();
        timerTask=new TimerTask() {
            @Override
            public void run() {
                // Aquí pones la lógica de lo que vas a pedir cada tanto tiempo
                //Ó también puedes llamar un metodo con to esa lógica (Estetica) XD
                Toast.makeText(getBaseContext(),"Me ejecuté", Toast.LENGTH_SHORT).show();

            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        return START_NOT_STICKY;
    }

   /*

        final ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        final ActivityManager activityManager =
                (ActivityManager) getSystemService(ACTIVITY_SERVICE);


        Timer timer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                activityManager.getMemoryInfo(memoryInfo);
                String availMem = memoryInfo.availMem / 1048576 + "MB";

                Log.d(TAG, availMem);

                Intent localIntent = new Intent(Constants.ACTION_RUN_SERVICE)
                        .putExtra(Constants.EXTRA_MEMORY, availMem);

                // Emitir el intent a la actividad
                LocalBroadcastManager.
                        getInstance(MemoryService.this).sendBroadcast(localIntent);
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);

        return START_NOT_STICKY;
    }*/
}
