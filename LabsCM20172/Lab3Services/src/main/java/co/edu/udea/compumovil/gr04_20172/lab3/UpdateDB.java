package co.edu.udea.compumovil.gr04_20172.lab3;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class UpdateDB extends Service {

    // LINK DEL SERIVDOR
    private final String HOST_CODE = "https://ensuenoservices-jersonlopez.c9users.io";

    // COMPLEMENTOS
    private final String URL_APARTMENTS_COMPLEMENT = ":8080/api/Apartments/";
    private final String URL_CONTAINER_DOWN_COMPLEMENT = ":8080/api/Containers/all/download/";

    private final String URL_APARTMENTS = HOST_CODE.concat(URL_APARTMENTS_COMPLEMENT);
    private final String URL_CONTAINER_DOWN = HOST_CODE.concat(URL_CONTAINER_DOWN_COMPLEMENT);


    Bitmap bitmap = null;
    byte[] blob;
    DbHelper dbHelper;
    SQLiteDatabase db;
    String ubi, foto, url;



   /* public UpdateDB() {
        super("UpdateDB");
    }*/

    TimerTask timerTask;
    public UpdateDB() {
    }

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

    /*@Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("TAG", "Servicio iniciado...");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getApartment();

                Log.d("TAG", "Running");
            }
        }, 0, 18000);
    }*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Servicio iniciado...");

        Timer timer = new Timer();
        timerTask=new TimerTask() {
            @Override
            public void run() {
                Log.d("@@@@", "voy a descargar apartamentos");
                // Aquí pones la lógica de lo que vas a pedir cada tanto tiempo
                //Ó también puedes llamar un metodo con to esa lógica (Estetica) XD
                getApartment();
                //Toast.makeText(getBaseContext(),"Me ejecuté", Toast.LENGTH_SHORT).show();

            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 180000);
        return START_NOT_STICKY;
    }

    private void getApartment() {
        dbHelper = new DbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_APARTMENTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Apartment[] apartment = new Gson().fromJson(response.toString(), Apartment[].class);

                        if (apartment != null) {
                            for (int i = 0; i < apartment.length; i++) {
                                String textLarge = apartment[i].getLargedescriptionapartment();
                                String textType = apartment[i].getTypeapartment();
                                String textPrice = apartment[i].getPriceapartment();
                                String textArea = apartment[i].getAreaapartment();
                                String textShort = apartment[i].getShortdescriptionapartment();
                                String textUbication = apartment[i].getId();
                                String textRooms = apartment[i].getRoomsapartment();

                                Log.d("@@@", textType + "_" + textPrice + "-" + textArea + "_" + textUbication);
                                //Toast.makeText(getActivity(), textubication, Toast.LENGTH_SHORT).show();

                                ubi = textUbication.replace(" ", "");
                                ubi = ubi.replace("#", "");
                                ubi = ubi.replace("-", "");
                                foto = apartment[i].getPhotoapartment();
                                url = URL_CONTAINER_DOWN.concat(ubi).concat(foto);

                                ContentValues values2 = new ContentValues();
                                values2.put(ApartmentsDB.ColumnApartment.ubicationApartment, textUbication);
                                values2.put(ApartmentsDB.ColumnApartment.typeApartment, textType);
                                values2.put(ApartmentsDB.ColumnApartment.priceApartment, textPrice);
                                values2.put(ApartmentsDB.ColumnApartment.areaApartment, textArea);
                                values2.put(ApartmentsDB.ColumnApartment.roomsApartment, textRooms);
                                values2.put(ApartmentsDB.ColumnApartment.ShortDescriptionApartment, textShort);
                                values2.put(ApartmentsDB.ColumnApartment.LargeDescriptionApartment, textLarge);
                                db.insertWithOnConflict(ApartmentsDB.entityApartment, null, values2, SQLiteDatabase.CONFLICT_IGNORE);

                                Intent starServiceIntent = new Intent(getApplicationContext(), ImageService.class);
                                starServiceIntent.putExtra("ubicacion", textUbication);
                                getApplicationContext().startService(starServiceIntent);

                                /*String consulta = "select " + ApartmentsDB.ColumnApartment.ubicationApartment + " from " + ApartmentsDB.entityApartment + " where " + ApartmentsDB.ColumnApartment.typeApartment + "=" + "\"" + textType + "\"" +
                                        " and " + ApartmentsDB.ColumnApartment.priceApartment + "=" + "\"" + textPrice + "\"" + " and " + ApartmentsDB.ColumnApartment.roomsApartment + "=" + "\"" + textRooms + "\"" +
                                        " and " + ApartmentsDB.ColumnApartment.areaApartment + "=" + "\"" + textArea + "\"" + " and " + ApartmentsDB.ColumnApartment.ubicationApartment + "=" + "\"" +
                                        textUbication + "\"";
                                Cursor cursor3 = db.rawQuery(consulta, null);

                                cursor3.moveToFirst();
                                String ubication = cursor3.getString(cursor3.getColumnIndex(ApartmentsDB.ColumnApartment.ubicationApartment));

                                //Toast.makeText(getApplicationContext(), "ubi " + ubication, Toast.LENGTH_SHORT).show();
                                values1.put(ApartmentsDB.ColumnResource.photo, blob);
                                values1.put(ApartmentsDB.ColumnResource.ubicationApartment, textUbication);
                                db.insertWithOnConflict(ApartmentsDB.entityResource, null, values1, SQLiteDatabase.CONFLICT_IGNORE);*/

                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error consultando información", Toast.LENGTH_SHORT).show();
                        Log.d("nada2", error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}


