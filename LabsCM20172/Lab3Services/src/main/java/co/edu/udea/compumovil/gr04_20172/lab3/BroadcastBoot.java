package co.edu.udea.compumovil.gr04_20172.lab3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("###", "inicie el servicio");
        Intent service = new Intent(context,  UpdateDB.class);
        context.startService(service);
    }
}
