package com.criminalintent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.criminalintent.database.CrimeBaseHelper;
import com.criminalintent.database.CrimeCursorWrapper;
import com.criminalintent.database.CrimeDbSchema;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class ObjectLab {

    //Declaro un crimelab estatico
    private static ObjectLab sObjectLab;

    //Declaro un contexto
    private Context mContext;

    //Declaro una BD
    private SQLiteDatabase mDatabase;

    //Defino un constructor
    private ObjectLab(Context context){

        //Código para abrir la base de datos.
        this.mContext = context.getApplicationContext();

        //En la llamada al metodo getWritableDatabase, CrimeBaseHelper hace:
        //1ºAbre /data/data/com.criminalintent/databases/crimeBase.db
        //2º Si es la primera vez que se crea la base de datos llama a onCreate(SQLiteDataBase)
        //3º Si no es la primera vez, comprueba el numero de versión en la base de datos
        //si el número de versión en CrimeOpenHelper es mayor, llama a onUpgrade y lo actualiza
        this.mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    //Metodo para devolver una lista de objects
    public List<Object>getList(String nameTable){

        //Instanciamos una lista vacia
        List<Object> elements = new ArrayList<>();

        //Instanciamos un cursor para desplazarnos por la tabla
        CrimeCursorWrapper cursor = queryCrimes(nameTable,null,null);

        try {

            //Nos desplazamos al comienzo de la tabla
            cursor.moveToFirst();

            //Si no estamos en la ultima fila recorremos la tabla
            while (!cursor.isAfterLast()) {

                //Añado un crime y me desplazo al siguiente
                elements.add(nameTable.equals("crimes") ? cursor.getCrime() :
                             nameTable.equals("users")  ? cursor.getUser()  :
                                                          cursor.getCrime());
                cursor.moveToNext();

            }

        }finally {cursor.close();}
        return elements;

    }

    //Metodo para obtener un object
    public Object getObject(UUID id, String nameTable){

        //Instancio un wrapper y obtengo los resultados de la condicion
        CrimeCursorWrapper cursor = queryCrimes(nameTable,
                                                nameTable.equals("crimes")?CrimeDbSchema.CrimeTable.Cols.UUID + " = ?":
                                                nameTable.equals("users") ?CrimeDbSchema.UserTable.Cols.UUID + " = ?" :
                                                                           CrimeDbSchema.UserTable.Cols.UUID + " = ?",
                                                new String[] {id.toString()}
        );

        try{

            //Devuelve el numero de columnas si es 0 devulve un nulo
            if(cursor.getCount() == 0){return null;}

            //Desplazamos el cursor a la primera fila
            cursor.moveToFirst();

            //Llamamos al cursor para devolver un object
            return (nameTable.equals("crimes")? cursor.getCrime() :
                    nameTable.equals("users")?  cursor.getUser() :
                                                cursor.getUser());

        }finally {cursor.close();}

    }

    //Metodo para obtener un crimelab
    public static ObjectLab get(Context context){
        return sObjectLab = sObjectLab == null ? new ObjectLab(context) : sObjectLab;
    }

    //Metodo que se encarga de pasar un Crime o User a una instancia de ContentValues
    private static ContentValues getContentValues(CrimePOJO crime, UserPOJO user){

        //Instancio un object contentvalues
        ContentValues values = new ContentValues();

        //En funcion de los parametros rellenamos values de una forma u otra
        if(crime != null && user == null){

            values.put(CrimeDbSchema.CrimeTable.Cols.UUID, crime.getId().toString());
            values.put(CrimeDbSchema.CrimeTable.Cols.TITLE,crime.getTitle());
            values.put(CrimeDbSchema.CrimeTable.Cols.DATE,crime.getDate().getTime());
            values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED,crime.isSolved() ? 1 : 0);
            values.put(CrimeDbSchema.CrimeTable.Cols.SUSPECT, crime.getSuspect());

        } else if (user != null && crime == null){

            values.put(CrimeDbSchema.UserTable.Cols.UUID, user.getIdUser().toString());
            values.put(CrimeDbSchema.UserTable.Cols.TYPEUSER,
                        user.getTypeUser() == TypeUser.TYPE_CLIENT ?    TypeUser.TYPE_CLIENT.name() :
                        user.getTypeUser() == TypeUser.TYPE_ADMIN ?     TypeUser.TYPE_ADMIN.name() :
                        user.getTypeUser() == TypeUser.TYPE_ORG ?       TypeUser.TYPE_ORG.name() :
                        user.getTypeUser() == TypeUser.TYPE_ORG_ADMIN ? TypeUser.TYPE_ORG_ADMIN.name() :
                                                                        TypeUser.TYPE_CLIENT.name() );

            values.put(CrimeDbSchema.UserTable.Cols.NAME, user.getNameUser());
            values.put(CrimeDbSchema.UserTable.Cols.EMAIL, user.getEmailUser());
            values.put(CrimeDbSchema.UserTable.Cols.PASSWORD, user.getEmailUser());
            values.put(CrimeDbSchema.UserTable.Cols.PHOTO, user.getPhotoUser());

        }

        return values;

    }

    //Metodo para añadir un Crime o User
    public void addObject(CrimePOJO crime, UserPOJO user){

        //Mediante ContentValues añadimos filas en la BD
        if(crime != null && user == null){
            this.mDatabase.insert(CrimeDbSchema.CrimeTable.NAME,null,getContentValues(crime,null));
        }
        else if(user != null && crime == null){
            this.mDatabase.insert(CrimeDbSchema.UserTable.NAME,null,getContentValues(null,user));
        }

    }

    //Metodo para actualizar un crimen o user
    public void updateObject(CrimePOJO crime, UserPOJO user){

        //Declaro un content values y un string nulo
        ContentValues values = null;
        String uuidString = null;

        //En funcion de los datos proporcionados obtenemos unos datos u otros
        if(crime != null && user == null){

            //Obtengo los valores del crime
            values = getContentValues(crime,null);

            //Obtengo el ID
            uuidString = crime.getId().toString();

            //Actualizo los datos
            this.mDatabase.update(CrimeDbSchema.CrimeTable.NAME,
                                  values,
                            CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                                  new String[]{uuidString});

        }else if(crime == null && user != null){

            //Obtengo los valores del crime
            values = getContentValues(null,user);

            //Obtengo el ID
            uuidString = user.getIdUser().toString();

            //Actualizo los datos
            this.mDatabase.update(CrimeDbSchema.UserTable.Cols.UUID + " = ?",
                    values,
                    CrimeDbSchema.UserTable.Cols.UUID + " = ?",
                    new String[]{uuidString});

        }

    }

    //Metodo para borrar un crime o user
    public void deleteObject(CrimePOJO crime, UserPOJO user){

        //Declaro un string nulo
        String uuidString = null;

        //En funcion de los datos hacemos una cosa u otra
        if(crime != null && user == null) {

            //Obtengo el id del crime
            uuidString = crime.getId().toString();

            //Llamo a un metodo de la DB y borro el crime
            this.mDatabase.delete(CrimeDbSchema.CrimeTable.NAME,
                    CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                    new String[]{uuidString});

        } else if(crime == null && user != null){

            //Obtengo el id del user
            uuidString = user.getIdUser().toString();

            //Llamo a un metodo de la DB y borro el user
            this.mDatabase.delete(CrimeDbSchema.UserTable.NAME,
                    CrimeDbSchema.UserTable.Cols.UUID + " = ?",
                    new String[]{uuidString});

        }

    }

    //Metodo crear un cursor y recorer la tabla
    private CrimeCursorWrapper queryCrimes(String nameTable,String whereClause, String[] whereArgs){

        //Declaro un cursor y lo asocio a una tabla
        Cursor cursor = mDatabase.query(
                nameTable.equals("crimes") ? CrimeDbSchema.CrimeTable.NAME :
                nameTable.equals("users")  ? CrimeDbSchema.UserTable.NAME :
                                             CrimeDbSchema.UserTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null

        );

        //Devuelvo el CrimeCursor con un cursor asociado a la tabla
        return new CrimeCursorWrapper(cursor);

    }

    //Metodo para proporcionar una ruta de almacenamiento para la foto
    public File getPhotoFile(CrimePOJO crime){
        return new File(this.mContext.getFilesDir(),crime.getPhotoFilename());
    }

}
