//Asocio el paquete al fragment
package com.criminalintent;

//Importo las librerias necesarias
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

//Declaro la clase y heredo
public class ConfigurationFragment extends Fragment {

    //Declaro los widget necesarios
    private EditText m_NameSurnameUser;
    private EditText m_PasswordUser;
    private EditText m_EmailUser;
    private Button m_ButtonSave;

    //Declaro un user para este fragment
    private UserPOJO userFragment;

    //Declaro una variable para intercambiar informacion
    public static String ARG_CONFIGURATION ="configuration_fragment";

    //Metodo para instanciar un fragment con un ID
    public static ConfigurationFragment newInstance(UUID userId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONFIGURATION,userId);
        ConfigurationFragment fragment = new ConfigurationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Metodo que se ejecuta segun el ciclo de vida del fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ///Obtengo el ID del user del fragment
        UUID userID = (UUID)getArguments().getSerializable(ARG_CONFIGURATION);

        //Recorro la lista de users y obtengo el correspondiente a su ID
        for(int i = 0; i < ObjectLab.get(getActivity()).getList("users").size() ; i++){
            if(((UserPOJO)ObjectLab.get(getActivity()).getList("users").get(i)).getIdUser().equals(userID)){
                this.userFragment = (UserPOJO)ObjectLab.get(getActivity()).getList("users").get(i);
            }
        }

        //Instancio una view y la asocio a su layout
        View view = inflater.inflate(R.layout.modify_user_activity,container,false);

        //Serializo los widget
        this.m_EmailUser = (EditText) view.findViewById(R.id.email_text_view_content);
        this.m_NameSurnameUser = (EditText) view.findViewById(R.id.name_surname_text_view_content);
        this.m_PasswordUser = (EditText) view.findViewById(R.id.password_text_view_content);
        this.m_ButtonSave = (Button) view.findViewById(R.id.button_save_profile_user);

        //Cambiamos los textos de los widget
        this.m_EmailUser.setText(this.userFragment.getEmailUser());
        this.m_NameSurnameUser.setText(this.userFragment.getNameUser());
        this.m_PasswordUser.setText(this.userFragment.getPasswordUser());

        //Añado un comportamiento a los widget
        this.m_EmailUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //Metodo que actua segun cambia el texto y proporciona el texto
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Actualizo el user
                ConfigurationFragment.this.userFragment.setEmailUser(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {}

        });
        this.m_NameSurnameUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //Metodo que actua segun cambia el texto y proporciona el texto
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Actualizo el user
                ConfigurationFragment.this.userFragment.setNameUser(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {}

        });
        this.m_PasswordUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //Metodo que actua segun cambia el texto y proporciona el texto
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Actualizo el user
                ConfigurationFragment.this.userFragment.setPasswordUser(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {}

        });
        this.m_ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectLab.get(getActivity()).updateObject(null,ConfigurationFragment.this.userFragment);
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            }

        });

        //Retorno la view
        return view;

    }

}
