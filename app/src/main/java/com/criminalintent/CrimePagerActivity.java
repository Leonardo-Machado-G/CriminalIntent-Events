package com.criminalintent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks{

    //Declaro una lista, viewpager y una variable para obtener el id del crime
    private static final String EXTRA_CRIME_ID = "crime_id";
    private ViewPager2 mViewPager;
    private List<CrimePOJO> mCrimes;

    //Declaro dos buttons para el desplazamiento de la viewpager
    private Button buttonStart;
    private Button buttonFinal;

    //Definimos un UUID inicial del que partimos
    private UUID crimeId;

    //Metodo que se ejecuta segun el ciclo de vida del fragment
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Asocio a la view un layout
        setContentView(R.layout.activity_crime_pager);

        //Instancio una lista de crimes
        this.mCrimes = new ArrayList<>();

        //Definimos un UUID obtengo del intent
        this.crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        //Asociamos los widgets locales a sus view mediante su ID
        this.buttonStart = (Button) findViewById(R.id.button_start);
        this.buttonFinal = (Button) findViewById(R.id.button_final);

        //Definimos el listener del buttonstart
        this.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Defino que hacer al hacer click
                CrimePagerActivity.this.mViewPager.setCurrentItem(0);

            }

        });

        //Definimos el listener del buttonfinal
        this.buttonFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Utilizamos el button final para cambiar la pagina actual
                CrimePagerActivity.this.mViewPager
                        .setCurrentItem(ObjectLab.get(CrimePagerActivity.this)
                                                .getList("crimes")
                                                .size() -1);

            }

        });

        //Traemos la lista de elemento Crime contenida en CrimeLab
        //Recorremos la lista y transformamos los object a crime
        for(int i = 0; i < ObjectLab.get(this).getList("crimes").size(); i++){
            this.mCrimes.add((CrimePOJO) ObjectLab.get(this).getList("crimes").get(i));
        }

        //Creamos el objeto mViewPager que mostrara los crimenes
        this.mViewPager = (ViewPager2) findViewById(R.id.activity_crime_pager_view_pager);

        //Seteamos el adaptador necesario para leer los objetos Crime de la lista
        this.mViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override

            //Metodo llamado para crear los fragments en funcion de la posicion
            public Fragment createFragment(int position) {
                return CrimeFragment.newInstance(mCrimes.get(position).getId());
            }

            //Defino la cantidad de views
            @Override
            public int getItemCount() {return mCrimes.size();}

        });

        //Defino un comportamiento al cambiar la pagina
        this.mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                //Instancio una lista de crime
                List<CrimePOJO> crimes = new ArrayList<>();

                //Recorro la lista y añado los object
                for(int i = 0; i < ObjectLab.get(CrimePagerActivity.this).getList("crimes").size(); i++){
                    crimes.add((CrimePOJO) ObjectLab.get(CrimePagerActivity.this).getList("crimes").get(i));
                }

                //Cambio el ID actual
                CrimePagerActivity.this.crimeId = crimes
                        .get(CrimePagerActivity.this.mViewPager.getCurrentItem())
                        .getId();

                //Defino un index para obtener el indice actual en el que me encuentro
                int indexContact = CrimePagerActivity.this.mViewPager.getCurrentItem();

                //Desativo el widget de button en funcion de donde me encuentre
                if(indexContact == 0){

                    buttonStart.setEnabled(false);
                    buttonFinal.setEnabled(true);

                } else if (indexContact == ObjectLab.get(CrimePagerActivity.this).getList("crimes").size() -1){

                    buttonStart.setEnabled(true);
                    buttonFinal.setEnabled(false);

                } else {

                    buttonStart.setEnabled(true);
                    buttonFinal.setEnabled(true);

                }

            }

        });

        //Establezco el primer elemento de la lista al iniciar el viewpager
        for(int i = 0; i < mCrimes.size(); i++){

            if (this.mCrimes.get(i).getId().equals(crimeId)) {

                //Desactivamos el button
                if(i == 0){buttonStart.setEnabled(false);}

                //Cambiamos el actual item
                this.mViewPager.setCurrentItem(i);

            }

        }

    }

    //Metodo para ejecutar la fragmentactivity con un ID en el intent
    public static Intent newIntent(Context packageContext, UUID crimeId){
        return new Intent(packageContext,CrimePagerActivity.class).putExtra(EXTRA_CRIME_ID,crimeId);
    }

    //Defino el menu que se va a cargar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_crime_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Metodo que define detecta el item seleccionado del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Defino un swich para definir el comportamiento del menu
        switch (item.getItemId()){

            case R.id.delete_crime:

                //Borro el crimen seleccionado
                ObjectLab.get(this).deleteObject((CrimePOJO) ObjectLab.get(this).getObject(this.crimeId,"crimes"), null);

                //Retrocedemos en la activity
                finish();
                return true;

            case R.id.return_button:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    //Metodo para actualizar el fragment heredado
    @Override
    public void onCrimeUpdated(CrimePOJO crime) {

    }

}
