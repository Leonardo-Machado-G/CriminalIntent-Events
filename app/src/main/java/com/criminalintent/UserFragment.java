//Asocio la clase al paquete
package com.criminalintent;

//Importo las librerias necesarias
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.UUID;

//Declaro la clase y heredo
public class UserFragment extends Fragment {

    //Instancio constantes para el intercambio de datos
    private static final String ARG_USERFRAGMENT_ID = "user_fragment_id";

    //Declaro los widget que  vamos a necesitar
    private EditText m_IDUser;
    private EditText m_NameUser;
    private EditText m_EmailUser;
    private EditText m_PasswordUser;
    private CheckBox m_ClientCheckBox;
    private CheckBox m_OrgCheckBox;
    private CheckBox m_AdminCheckBox;
    private CheckBox m_OrgAdminCheckBox;
    private Button m_SaveButton;

    //Declaro un user
    private UserPOJO m_User;

    //Metodo para instanciar un fragment con un ID
    public static UserFragment newInstance(UUID userID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_USERFRAGMENT_ID,userID);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Metodo que se ejecuta segun el ciclo de vida de un fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Obtenemos el ID del usuario
        UUID userID = (UUID)getArguments().getSerializable(ARG_USERFRAGMENT_ID);

        //Recorro la lista de usuarios
        for(int i = 0 ; i < ObjectLab.get(getActivity()).getList("users").size() ; i++){

            //Si encuentro el User lo asocio a su variable
            if(((UserPOJO)ObjectLab.get(getActivity()).getList("users").get(i)).getIdUser().equals(userID)){
                this.m_User = (UserPOJO)ObjectLab.get(getActivity()).getList("users").get(i);
                break;
            }

        }

    }

    //Metodo que se ejecuta segun el ciclo de vida de un fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Instancio una view y la asocio a un layout
        View view = inflater.inflate(R.layout.edit_user_activity, container,false);

        //Serializo todos los widget
        this.m_IDUser = view.findViewById(R.id.user_text_view_content_edit);
        this.m_NameUser = view.findViewById(R.id.name_text_view_content_edit);
        this.m_EmailUser = view.findViewById(R.id.email_text_view_content_edit);
        this.m_PasswordUser = view.findViewById(R.id.password_text_view_content_edit);
        this.m_ClientCheckBox = view.findViewById(R.id.client_checkbox_edit);
        this.m_OrgCheckBox = view.findViewById(R.id.organizer_checkbox_edit);
        this.m_AdminCheckBox = view.findViewById(R.id.administrator_checkbox_edit);
        this.m_OrgAdminCheckBox = view.findViewById(R.id.organizer_administrator_checkbox_edit);
        this.m_SaveButton = view.findViewById(R.id.save_button_edit);

        //Insertamos los datos en los widget
        this.m_IDUser.setText(this.m_User.getIdUser().toString());
        this.m_NameUser.setText(this.m_User.getNameUser());
        this.m_EmailUser.setText(this.m_User.getEmailUser());
        this.m_PasswordUser.setText(this.m_User.getPasswordUser());
        this.m_ClientCheckBox.setChecked(this.m_User.getTypeUser() == TypeUser.TYPE_CLIENT ? true: false);
        this.m_OrgCheckBox.setChecked(this.m_User.getTypeUser() == TypeUser.TYPE_ORG ? true: false);
        this.m_AdminCheckBox.setChecked(this.m_User.getTypeUser() == TypeUser.TYPE_ADMIN ? true: false);
        this.m_OrgAdminCheckBox.setChecked(this.m_User.getTypeUser() == TypeUser.TYPE_ORG_ADMIN ? true: false);

        //Definimos el comportamiento de los widget
        this.m_IDUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserFragment.this.m_User.setIdUser(UUID.fromString(charSequence.toString()));
            }
            @Override
            public void afterTextChanged(Editable editable) {}

        });
        this.m_NameUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserFragment.this.m_User.setNameUser(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        this.m_EmailUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserFragment.this.m_User.setEmailUser(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        this.m_PasswordUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserFragment.this.m_User.setPasswordUser(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        this.m_ClientCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFragment.this.m_ClientCheckBox.setChecked(true);
                UserFragment.this.m_AdminCheckBox.setChecked(false);
                UserFragment.this.m_OrgCheckBox.setChecked(false);
                UserFragment.this.m_OrgAdminCheckBox.setChecked(false);
                UserFragment.this.m_User.setTypeUser(TypeUser.TYPE_CLIENT);
            }
        });
        this.m_OrgCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFragment.this.m_ClientCheckBox.setChecked(false);
                UserFragment.this.m_AdminCheckBox.setChecked(false);
                UserFragment.this.m_OrgCheckBox.setChecked(true);
                UserFragment.this.m_OrgAdminCheckBox.setChecked(false);
                UserFragment.this.m_User.setTypeUser(TypeUser.TYPE_ORG);
            }
        });
        this.m_AdminCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFragment.this.m_ClientCheckBox.setChecked(false);
                UserFragment.this.m_AdminCheckBox.setChecked(true);
                UserFragment.this.m_OrgCheckBox.setChecked(false);
                UserFragment.this.m_OrgAdminCheckBox.setChecked(false);
                UserFragment.this.m_User.setTypeUser(TypeUser.TYPE_ADMIN);
            }
        });
        this.m_OrgAdminCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFragment.this.m_ClientCheckBox.setChecked(false);
                UserFragment.this.m_AdminCheckBox.setChecked(false);
                UserFragment.this.m_OrgCheckBox.setChecked(false);
                UserFragment.this.m_OrgAdminCheckBox.setChecked(true);
                UserFragment.this.m_User.setTypeUser(TypeUser.TYPE_ORG_ADMIN);
            }
        });
        this.m_SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectLab.get(getActivity()).updateObject(null,UserFragment.this.m_User);
                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}
