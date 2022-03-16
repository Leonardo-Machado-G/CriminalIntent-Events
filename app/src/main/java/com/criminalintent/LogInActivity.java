//Asociamos el paquete a la clase
package com.criminalintent;

//Importamos las librerias necesarias
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

//Declaramos la calse y heredamos
public class LogInActivity extends AppCompatActivity {

    //Metodo que se ejecuta segun el ciclo de vida de la activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Asociamos un layout a la view
        setContentView(R.layout.log_in_activity);



    }

}