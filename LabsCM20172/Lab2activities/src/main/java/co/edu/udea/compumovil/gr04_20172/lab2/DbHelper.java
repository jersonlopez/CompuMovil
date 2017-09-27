package co.edu.udea.compumovil.gr04_20172.lab2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by jerson.lopez on 19/09/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME = "Ensueno.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tabla user

        String sqlUser = String
                .format("create table %s (%s text primary key, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s blob)",
                        ApartmentsDB.entityUser,
                        ApartmentsDB.ColumnUser.email,
                        ApartmentsDB.ColumnUser.userName,
                        ApartmentsDB.ColumnUser.userLastName,
                        ApartmentsDB.ColumnUser.birthdate,
                        ApartmentsDB.ColumnUser.address,
                        ApartmentsDB.ColumnUser.password,
                        ApartmentsDB.ColumnUser.numberPhone,
                        ApartmentsDB.ColumnUser.gender,
                        ApartmentsDB.ColumnUser.city,
                        ApartmentsDB.ColumnUser.photo
                        );
                //Sentencia para crear tabla
        Log.d(TAG, "onCreate with SQL: " + sqlUser);
        db.execSQL(sqlUser); //Ejecución de la sentencia

        //Tabla de Apartment

        String sqlApartment = String
                .format("create table %s (%s text primary key, %s text, %s text, %s text, %s text, %s text, %s text)",
                        ApartmentsDB.entityApartment,
                        ApartmentsDB.ColumnApartment.ubicationApartment,
                        ApartmentsDB.ColumnApartment.typeApartment,
                        ApartmentsDB.ColumnApartment.priceApartment,
                        ApartmentsDB.ColumnApartment.areaApartment,
                        ApartmentsDB.ColumnApartment.roomsApartment,
                        ApartmentsDB.ColumnApartment.ShortDescriptionApartment,
                        ApartmentsDB.ColumnApartment.LargeDescriptionApartment
                );
        //Sentencia para crear tabla
        Log.d(TAG, "onCreate with SQL: " + sqlApartment);
        db.execSQL(sqlApartment); //Ejecución de la sentencia

        //Tabala Resource

        String sqlResource = String
                .format("create table %s (%s integer primary key AUTOINCREMENT not null, %s text, %s blob, FOREIGN KEY (%s) REFERENCES %s(%s))",
                        ApartmentsDB.entityResource,
                        ApartmentsDB.ColumnResource.id,
                        ApartmentsDB.ColumnResource.ubicationApartment,
                        ApartmentsDB.ColumnResource.photo,
                        ApartmentsDB.ColumnResource.ubicationApartment, ApartmentsDB.entityApartment, ApartmentsDB.ColumnApartment.ubicationApartment
                );
        //Sentencia para crear tabla
        Log.d(TAG, "onCreate with SQL: " + sqlResource);
        db.execSQL(sqlResource); //Ejecución de la sentencia
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + ApartmentsDB.entityUser);//Borrar tabla
        db.execSQL("drop table if exists " + ApartmentsDB.entityApartment);//Borrar tabla
        db.execSQL("drop table if exists " + ApartmentsDB.entityResource);//Borrar tabla
        onCreate(db);//Crear tabla de nuevo
    }

    }

