package co.edu.udea.compumovil.gr04_20172.lab1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageHelper;
import android.widget.Button;
import android.widget.TextView;


public class Starting_Activity extends AppCompatActivity{


    TextView lName;
    TextView lLastname;
    TextView lBorn;
    TextView lDirection;
    TextView lEmail;
    TextView lPhone;
    TextView lCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        lName = (TextView) findViewById(R.id.textViewNombre);
        lLastname = (TextView) findViewById(R.id.textViewApellido);
        lBorn = (TextView) findViewById(R.id.dateText);
        lDirection = (TextView) findViewById(R.id.textViewAddress);
        lEmail = (TextView)findViewById(R.id.textViewEmail);
        lPhone = (TextView) findViewById(R.id.textViewPhone);
        lCity = (TextView) findViewById(R.id.textViewCity);
        String defaul = "no hay datos";

        SharedPreferences sharedPr = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
        lName.setText(sharedPr.getString("nombre",defaul ));
        lLastname.setText(sharedPr.getString("apellidos",defaul ));
        lBorn.setText(sharedPr.getString("fechanacimiento",defaul ));
        lDirection.setText(sharedPr.getString("direccion",defaul ));
        lEmail.setText(sharedPr.getString("correo",defaul ));
        lPhone.setText(sharedPr.getString("telefono",defaul ));
        lCity.setText(sharedPr.getString("ciudad",defaul ));


    }

}
