package com.criminalintent.database;

//Clase contenedora de constantes que definen nombres de URI, tablas y columnas
public class CrimeDbSchema {

    //Clase interna que define la estructura de nuestra tabla
    public static final class CrimeTable{

        //Especificamos el nombre de la table dentro de la BD
        public static final String NAME = "crimes";

        //Describimos las columnas que forman parte de la tabla
        public static final class Cols{

            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";

        }

    }

    //Clase interna que define la estructura de nuestra tabla de usuarios
    public static final class UserTable{

        //Especificamos el nombre de la table dentro de la BD
        public static final String NAME = "users";

        //Describimos las columnas que forman parte de la tabla
        public static final class Cols{

            public static final String UUID = "uuidUser";
            public static final String NAME = "nameUser";
            public static final String EMAIL = "emailUser";
            public static final String PASSWORD = "passwordUser";
            public static final String PHOTO = "photoUser";
            public static final String TYPEUSER = "typeUser";

        }

    }

}
