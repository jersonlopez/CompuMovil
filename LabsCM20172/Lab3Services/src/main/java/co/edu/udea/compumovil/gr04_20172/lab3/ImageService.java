package co.edu.udea.compumovil.gr04_20172.lab3;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by jersonlopez on 8/10/17.
 */

public class ImageService extends IntentService {

    // LINK DEL SERIVDOR
    private final String HOST_CODE = "https://ensuenoservices-jersonlopez.c9users.io";

    // COMPLEMENTOS
    private final String URL_CONTAINER_DOWN_COMPLEMENT = ":8080/api/Containers/all/download/";

    private final String URL_CONTAINER_DOWN = HOST_CODE.concat(URL_CONTAINER_DOWN_COMPLEMENT);

    String ubi, textUbication;
    byte[] blob;
    SQLiteDatabase db;

    public ImageService(String name) {
        super(name);
    }

    public ImageService() {
        super("ImageService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        this.textUbication=intent.getExtras().getString("ubicacion");
        DbHelper dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
        Log.d("@@@@", textUbication);
        ubi = textUbication.replace(" ", "");
        ubi = ubi.replace("#", "");
        ubi = ubi.replace("-", "");
        String url = URL_CONTAINER_DOWN.concat(ubi).concat("img.png");

        try {
            Bitmap bitmap = Glide.with(ImageService.this)
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 75, baos);
            blob = baos.toByteArray();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ContentValues values1 = new ContentValues();
        values1.put(ApartmentsDB.ColumnResource.photo, blob);
        values1.put(ApartmentsDB.ColumnResource.ubicationApartment, textUbication);
        db.insertWithOnConflict(ApartmentsDB.entityResource, null, values1, SQLiteDatabase.CONFLICT_IGNORE);

    }
}
