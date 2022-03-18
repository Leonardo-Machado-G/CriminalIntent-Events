//Asocio el paquete al fragment
package com.criminalintent;

//Importo las librerias necesarias
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Declaro la clase y heredo
public class WelcomeFragment extends Fragment {

    //Declaro los widget necesarios
    private TextView m_User_Text;
    private UUID m_UserID;

    //Declaro una variable para intercambiar informacion
    public static String ARG_WELCOME ="welcome_fragment";

    //Metodo para instanciar un fragment con un ID
    public static WelcomeFragment newInstance(UUID userId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_WELCOME,userId);
        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Metodo que se ejecuta segun el ciclo de vida de un fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Instancio una view y la asocio a su layout
        View view = inflater.inflate(R.layout.welcome_fragment,container,false);

        //Serializo los widget
        this.m_User_Text = view.findViewById(R.id.welcome_user_text_view);

        ///Obtengo el ID del user del fragment
        this.m_UserID = (UUID)getArguments().getSerializable(ARG_WELCOME);

        //Recorremos la lista
        for(int i = 0; i < ObjectLab.get(getActivity()).getList("users").size() ; i++){

            //Si el usuario coincide con el UUID accedemos
            if(((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i)).getIdUser().equals(this.m_UserID)){

                //En funcion del tipo de usuario cambiamos el textview
                if(((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i)).getTypeUser() == TypeUser.TYPE_ADMIN){

                    this.m_User_Text.setText(getString(R.string.administrator_text_view));

                } else if(((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i)).getTypeUser() == TypeUser.TYPE_CLIENT){

                    this.m_User_Text.setText(getString(R.string.client_text_view));

                } else if(((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i)).getTypeUser() == TypeUser.TYPE_ORG){

                    this.m_User_Text.setText(getString(R.string.organizer_text_view));

                } else if(((UserPOJO) ObjectLab.get(getActivity()).getList("users").get(i)).getTypeUser() == TypeUser.TYPE_ORG_ADMIN){

                    this.m_User_Text.setText(getString(R.string.organizer_text_view) + "-" + getString(R.string.administrator_text_view));

                }

            }

        }

        //Retorno la view
        return view;

    }

}
