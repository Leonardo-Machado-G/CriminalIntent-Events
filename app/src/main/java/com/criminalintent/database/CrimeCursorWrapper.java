package com.criminalintent.database;
import android.database.Cursor;
import android.database.CursorWrapper;
import com.criminalintent.Crime;
import com.criminalintent.TypeUser;
import com.criminalintent.User;

import java.util.Date;
import java.util.UUID;

//Clase creada para empaquetar los métodos de tratamiento del cursor
//CursorWrapper nos permite envolver la clase Cursor y añadirle nuevos metodos
public class CrimeCursorWrapper extends CursorWrapper {

    //Definos el constructor
    public CrimeCursorWrapper(Cursor cursor) {super(cursor);}

    //Añadimos el metodo getCrime que extraerá los datos de las columnas
    public Crime getCrime(){

        //Obtenemos el contenido de las columnas
        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SUSPECT));

        //Ahora vamos a crear el objeto Crime a devolver
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);
        return crime;

    }

    //Metodo que extraera los datos de la columna
    public User getUser(){

        //Obtenemos el contenido de las columnas
        String typeUserString = getString(getColumnIndex(CrimeDbSchema.UserTable.Cols.TYPEUSER));

        //El funcion del string devuelto establecemos uno u otro typeUser
        TypeUser typeUser = typeUserString.equals(TypeUser.TYPE_CLIENT.name()) ? TypeUser.TYPE_CLIENT :
                            typeUserString.equals(TypeUser.TYPE_ADMIN.name()) ? TypeUser.TYPE_ADMIN :
                            typeUserString.equals(TypeUser.TYPE_ORG.name()) ? TypeUser.TYPE_ORG :
                            typeUserString.equals(TypeUser.TYPE_ORG_ADMIN.name()) ? TypeUser.TYPE_ORG_ADMIN :
                            TypeUser.TYPE_CLIENT;

        //Cremoas un objeto user con los datos obtenidos
        User user = new User(typeUser,
                             getString(getColumnIndex(CrimeDbSchema.UserTable.Cols.NAME)),
                             getString(getColumnIndex(CrimeDbSchema.UserTable.Cols.EMAIL)),
                             getString(getColumnIndex(CrimeDbSchema.UserTable.Cols.PASSWORD)),
                             getLong(getColumnIndex(CrimeDbSchema.UserTable.Cols.PHOTO)));

        //Establecemos un nuevo UUID
        user.setIdUser(UUID.fromString(getString(getColumnIndex(CrimeDbSchema.UserTable.Cols.UUID))));

        //Retornamos el usuario
        return user;

    }

}
