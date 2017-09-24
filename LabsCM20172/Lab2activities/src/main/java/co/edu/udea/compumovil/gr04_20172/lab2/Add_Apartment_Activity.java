package co.edu.udea.compumovil.gr04_20172.lab2;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Add_Apartment_Activity extends AppCompatActivity implements View.OnClickListener {


    DbHelper dbHelper;
    SQLiteDatabase db;
    EditText eType, ePrice, eArea, eRooms, eUbication, eShortDescription, eLargeDescription;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__apartment);
        eType = (EditText) findViewById(R.id.editTextType);
        ePrice = (EditText) findViewById(R.id.editTextValue);
        eArea = (EditText) findViewById(R.id.editTextArea);
        eRooms = (EditText) findViewById(R.id.editTextRoom);
        eUbication = (EditText) findViewById(R.id.editTextUbication);
        eShortDescription = (EditText) findViewById(R.id.editTextShortDescription);
        eLargeDescription = (EditText) findViewById(R.id.editTextLargeDescription);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(this);
        dbHelper = new DbHelper(getBaseContext()); //Instancia de DbHelper
        db = dbHelper.getWritableDatabase(); //Obtenemos la instancia de la BD

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonAdd:

                String textType = eType.getText().toString();
                String textPrice = ePrice.getText().toString();
                String textArea = eArea.getText().toString();
                String textRooms = eRooms.getText().toString();
                String textShort = eShortDescription.getText().toString();
                String textLarge = eLargeDescription.getText().toString();
                String texUbication = eUbication.getText().toString();

                if (textType.equals("") || textPrice.equals("") || textArea.equals("") || textRooms.equals("") || textShort.equals("") || textLarge.equals("") || texUbication.equals("")) {
                    Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

                } else{
                    ContentValues values = new ContentValues();
                    values.put(ApartmentsDB.ColumnApartment.ubicationApartment, texUbication);
                    values.put(ApartmentsDB.ColumnApartment.photoApartment, "hola");
                    values.put(ApartmentsDB.ColumnApartment.typeApartment, textType);
                    values.put(ApartmentsDB.ColumnApartment.priceApartment, textPrice);
                    values.put(ApartmentsDB.ColumnApartment.areaApartment, textArea);
                    values.put(ApartmentsDB.ColumnApartment.roomsApartment, textRooms);
                    values.put(ApartmentsDB.ColumnApartment.ShortDescriptionApartment, textShort);
                    values.put(ApartmentsDB.ColumnApartment.LargeDescriptionApartment, textLarge);
                    //Log.d("hola","pase por aqui");
                    db.insertWithOnConflict(ApartmentsDB.entityApartment, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                    //String cosulta= "select email, city from "  + ApartmentsDB.entityUser;
                    String consulta = "select " + ApartmentsDB.ColumnApartment.ubicationApartment + ", " + ApartmentsDB.ColumnApartment.priceApartment + " from " + ApartmentsDB.entityApartment;
                    Cursor cursor = db.rawQuery(consulta, null);
                    //Toast.makeText(getApplicationContext(), cursor.getCount() + " nacimiento: " + textBorn, Toast.LENGTH_LONG).show();
                    while (cursor.moveToNext()) {
                        //Log.d(TAG,cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.email)));
                        //Log.d(TAG,cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.city)));
                        Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.ubicationApartment)), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.priceApartment)), Toast.LENGTH_LONG).show();


                        //eEmail.setText(cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.email)));
                        //eCity.setText(cursor.getString(1));
                    }
                    Toast.makeText(getApplicationContext(), "Apartamento agregado", Toast.LENGTH_SHORT).show();
                    Intent intentNavigation = new Intent(Add_Apartment_Activity.this, Navigation_Drawer.class);
                    startActivity(intentNavigation);
                    finish();
                }
                break;
        }



    }
}
