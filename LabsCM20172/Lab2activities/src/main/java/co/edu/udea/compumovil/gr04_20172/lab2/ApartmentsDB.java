package co.edu.udea.compumovil.gr04_20172.lab2;

import android.provider.BaseColumns;

import java.sql.Blob;

/**
 * Created by jerson.lopez on 20/09/17.
 */

public class ApartmentsDB {
    public static final String dbName = "Ensueno.db"; //Nombre de la DB
    public static final int dbVersion = 1; //Versión de la DB

    public static final String entityUser = "User"; //Tabla usuario
    public static final String entityApartment = "Apartment"; //Tabla apartamento
    public static final String entityResource = "Resource"; //Tabla Resource

    public class ColumnUser { //Columnas de la tabla User
        public static final String email = BaseColumns._ID; // correo primary key
        public static final String userName  = "userName";
        public static final String userLastName = "userlastName";
        public static final String birthdate = "birthdate";
        public static final String address = "address";
        public static final String password = "password";
        public static final String numberPhone = "numberPhone";
        public static final String gender = "gender";
        public static final String city = "city";
        public static final String photo = "photo";
    }

    public class ColumnApartment { //Columnas de la tabla Apartment
        public static final String ubicationApartment = BaseColumns._ID; // dirección primary key
        public static final String typeApartment = "typeApartment";
        public static final String priceApartment = "priceApartment";
        public static final String areaApartment = "areaApartment";
        public static final String roomsApartment = "roomsApartment";
        public static final String ShortDescriptionApartment = "ShorDescriptionApartment";
        public static final String LargeDescriptionApartment = "LargeDescriptionApartment";
    }

    public class ColumnResource {
        public static final String id = BaseColumns._ID;
        public static final String photo = "photo";
        public static final String ubicationApartment = "ubication";
    }
}
