package co.edu.udea.compumovil.gr04_20172.lab2;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Add_Apartment_Activity extends AppCompatActivity implements View.OnClickListener {


    DbHelper dbHelper;
    SQLiteDatabase db;
    EditText eType, ePrice, eArea, eRooms, eUbication, eShortDescription, eLargeDescription;
    Button btnAdd;
    ImageView imageView;
    static final int REQUEST_IMAGE_GET = 101;
    Bitmap bitmap;
    ByteArrayOutputStream baos;
    byte[] blob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__apartment);
        imageView = (ImageView) findViewById(R.id.imageButton);
        imageView.setOnClickListener(this);
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

                if (bitmap==null || textType.equals("") || textPrice.equals("") || textArea.equals("") || textRooms.equals("") || textShort.equals("") || textLarge.equals("") || texUbication.equals("")) {
                    Toast.makeText(getApplicationContext(), "Datos Incompletos", Toast.LENGTH_SHORT).show();

                } else{
                    baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,75,baos);
                    blob =baos.toByteArray();
                    ContentValues values1= new ContentValues();
                    ContentValues values= new ContentValues();
                    values.put(ApartmentsDB.ColumnApartment.ubicationApartment, texUbication);
                    values.put(ApartmentsDB.ColumnApartment.typeApartment, textType);
                    values.put(ApartmentsDB.ColumnApartment.priceApartment, textPrice);
                    values.put(ApartmentsDB.ColumnApartment.areaApartment, textArea);
                    values.put(ApartmentsDB.ColumnApartment.roomsApartment, textRooms);
                    values.put(ApartmentsDB.ColumnApartment.ShortDescriptionApartment, textShort);
                    values.put(ApartmentsDB.ColumnApartment.LargeDescriptionApartment, textLarge);

                    //Log.d("hola","pase por aqui");
                    db.insertWithOnConflict(ApartmentsDB.entityApartment, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                    String consulta="select "+ ApartmentsDB.ColumnApartment.ubicationApartment +" from " +ApartmentsDB.entityApartment+ " where " + ApartmentsDB.ColumnApartment.typeApartment+"="+ "\"" + textType + "\"" +
                            " and "+ApartmentsDB.ColumnApartment.priceApartment+"=" + "\"" + textPrice + "\"" + " and "+ApartmentsDB.ColumnApartment.roomsApartment+"=" + "\"" + textRooms + "\"" +
                            " and "+ApartmentsDB.ColumnApartment.areaApartment+"=" + "\"" + textArea + "\"" + " and "+ApartmentsDB.ColumnApartment.ubicationApartment+"=" + "\"" +
                            texUbication + "\"";
                    Cursor cursor=db.rawQuery(consulta,null);
                    if(cursor.moveToNext()){
                        values1.put(ApartmentsDB.ColumnResource.photo, blob);
                        values1.put(ApartmentsDB.ColumnResource.ubicationApartment, texUbication);
                        db.insertWithOnConflict(ApartmentsDB.entityResource,null,values1,SQLiteDatabase.CONFLICT_IGNORE);
                    }

                    String consulta1 = "select " + ApartmentsDB.ColumnResource.photo + " from " + ApartmentsDB.entityResource;
                    Cursor cursor1 = db.rawQuery(consulta1, null);
                    if(cursor1.getCount()>0)
                    {
                        //Toast.makeText(getApplicationContext(),"la imagen si guardo", Toast.LENGTH_SHORT).show();
                        Log.d("Excelente", "Hay datos en el cursor");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "la imagen no entro", Toast.LENGTH_SHORT).show();
                        //Log.d("Ups","Don bochi sin elementos");
                    }
                    /*while (cursor.moveToNext()) {
                        Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.ubicationApartment)), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnApartment.priceApartment)), Toast.LENGTH_LONG).show();
                    }*/
                    Toast.makeText(getApplicationContext(), "Apartamento agregado", Toast.LENGTH_SHORT).show();
                    Intent intentNavigation = new Intent(Add_Apartment_Activity.this, Navigation_Drawer.class);
                    startActivity(intentNavigation);
                    finish();
                }
                break;

            case R.id.imageButton:
                this.selectPicture();
                break;
        }

    }

    public void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            // Log.d(TAG, String.valueOf(bitmap));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
