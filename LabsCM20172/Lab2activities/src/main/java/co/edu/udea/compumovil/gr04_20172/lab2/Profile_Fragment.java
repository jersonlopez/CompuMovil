package co.edu.udea.compumovil.gr04_20172.lab2;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {


    SQLiteDatabase db;
    Context context;
    String email;
    Cursor cursor;
    TextView nameProfile=null, lastnameProfile=null, emailProfile=null, genderProfile=null, phoneProfile=null;
    ImageView photo;
    Bitmap bitmap;


    public Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbHelper dbHelper = new DbHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        email = getActivity().getIntent().getStringExtra("email");
        //Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();
        String consulta = "select " + ApartmentsDB.ColumnUser.email + ", " + ApartmentsDB.ColumnUser.userName +", "+ApartmentsDB.ColumnUser.userLastName
                +", "+ApartmentsDB.ColumnUser.gender+", "+ApartmentsDB.ColumnUser.numberPhone+ ", " + ApartmentsDB.ColumnUser.photo+" from " + ApartmentsDB.entityUser +" where " +ApartmentsDB.ColumnUser.email + "="+ "\"" +
                email + "\"" ;
        //Toast.makeText(getActivity(),consulta, Toast.LENGTH_SHORT).show();
        cursor = db.rawQuery(consulta,null);

        if (cursor.moveToNext()){
           // Toast.makeText(getActivity(),cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.gender)),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "Vacio", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_, container,false);
        nameProfile = v.findViewById(R.id.textViewNameProfile);
        lastnameProfile = v.findViewById(R.id.textViewLastnameProfile);
        emailProfile = v.findViewById(R.id.textViewEmailProfile);
        genderProfile = v.findViewById(R.id.textViewGender);
        phoneProfile = v.findViewById(R.id.textViewNumberphoneProfile);
        photo = v.findViewById(R.id.imageViewProfile);
        nameProfile.setText(cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.userName)).toString());
        lastnameProfile.setText(cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.userLastName)).toString());
        emailProfile.setText(cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.email)).toString());
        genderProfile.setText(cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.gender)).toString());
        phoneProfile.setText(cursor.getString(cursor.getColumnIndex(ApartmentsDB.ColumnUser.numberPhone)).toString());
        byte[] blob = cursor.getBlob(cursor.getColumnIndex(ApartmentsDB.ColumnUser.photo));
        bitmap = BitmapFactory.decodeByteArray(blob,0,blob.length);
        photo.setImageBitmap(bitmap);
        //Toast.makeText(getActivity(), "acabe en el perfil", Toast.LENGTH_SHORT).show();
        return v;
    }

}
