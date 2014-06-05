package com.gt.helprtq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SchemaManager extends SQLiteOpenHelper {
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String[] SQL_CREATE_ENTRIES = {
		    "CREATE TABLE " + LocalDB.DB.TABLE_NAME + " (" +
							  LocalDB.DB._ID + INTEGER_TYPE + " PRIMARY KEY," +

							  LocalDB.DB.COLUMN_NAME_VERSIONE + INTEGER_TYPE + COMMA_SEP +
							  LocalDB.DB.COLUMN_NAME_STATO + TEXT_TYPE +
							  " ) ",
			"CREATE TABLE " + LocalDB.Cliente.TABLE_NAME + " (" +
				    		  LocalDB.Cliente._ID + INTEGER_TYPE +" PRIMARY KEY," +

				    		  LocalDB.Cliente.COLUMN_NAME_NOME + TEXT_TYPE + COMMA_SEP +
				    		  LocalDB.Cliente.COLUMN_NAME_INDIRIZZO + TEXT_TYPE + COMMA_SEP +
				    		  LocalDB.Cliente.COLUMN_NAME_CODICE_ORGANIZZATO + TEXT_TYPE + COMMA_SEP +
				    		  LocalDB.Cliente.COLUMN_NAME_MARCHE + TEXT_TYPE + COMMA_SEP +
				    		  LocalDB.Cliente.COLUMN_NAME_TELEFONO + TEXT_TYPE + 
				    		  " ) ",  
  			"CREATE TABLE " + LocalDB.Audit.TABLE_NAME + " (" +
				    		  LocalDB.Audit._ID + INTEGER_TYPE +" PRIMARY KEY," +
				    		  LocalDB.Audit.COLUMN_NAME_CLIENTE_FK+ INTEGER_TYPE + COMMA_SEP +
				    		  LocalDB.Audit.COLUMN_NAME_VISITA_FK + INTEGER_TYPE + COMMA_SEP +
		
				    		  LocalDB.Audit.COLUMN_NAME_DATA_MAIL + TEXT_TYPE + COMMA_SEP +
				    		  LocalDB.Audit.COLUMN_NAME_ESITO + TEXT_TYPE + 
				    		  " ) ",
			"CREATE TABLE " + LocalDB.Assessment.TABLE_NAME + " (" +
				    		  LocalDB.Assessment._ID + INTEGER_TYPE +" PRIMARY KEY," +
				    		  LocalDB.Assessment.COLUMN_NAME_CLIENTE_FK+ INTEGER_TYPE + COMMA_SEP +
				    		  LocalDB.Assessment.COLUMN_NAME_VISITA_FK + INTEGER_TYPE + COMMA_SEP +
			
				    		  LocalDB.Assessment.COLUMN_NAME_DATA + TEXT_TYPE + COMMA_SEP +
				    		  LocalDB.Assessment.COLUMN_NAME_CONSEGNATO + TEXT_TYPE + COMMA_SEP +
				    		  LocalDB.Assessment.COLUMN_NAME_RC + TEXT_TYPE + COMMA_SEP +
				    		  LocalDB.Assessment.COLUMN_NAME_RS + TEXT_TYPE + 
				    		  " ) ",
  			"CREATE TABLE " + LocalDB.pt.TABLE_NAME + " (" +
			  	    		  LocalDB.pt._ID + INTEGER_TYPE +" PRIMARY KEY," +
			  	    		  LocalDB.pt.COLUMN_NAME_CLIENTE_FK+ INTEGER_TYPE + COMMA_SEP +
			  	    		  LocalDB.pt.COLUMN_NAME_VISITA_FK + INTEGER_TYPE + COMMA_SEP +
			
			  	    		  LocalDB.pt.COLUMN_NAME_DATA + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.pt.COLUMN_NAME_MARCA + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.pt.COLUMN_NAME_ESITO + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.pt.COLUMN_NAME_DISCUSSO+ TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.pt.COLUMN_NAME_CORRETTIVO + TEXT_TYPE + 
			  	    		  " ) ",  
  			"CREATE TABLE " + LocalDB.marche.TABLE_NAME + " (" +
			  	    		  LocalDB.marche._ID + INTEGER_TYPE +" PRIMARY KEY," +
			  	    		  LocalDB.marche.COLUMN_NAME_MARCA + TEXT_TYPE + 
			  	    		  " ) ",  
  			"CREATE TABLE " + LocalDB.vettura.TABLE_NAME + " (" +
			  	    		  LocalDB.vettura._ID + INTEGER_TYPE +" PRIMARY KEY," +
			  	    		  LocalDB.vettura.COLUMN_NAME_CLIENTE_FK+ INTEGER_TYPE + COMMA_SEP +
			  	    		  LocalDB.vettura.COLUMN_NAME_VISITA_FK + INTEGER_TYPE + COMMA_SEP +
			
			  	    		  LocalDB.vettura.COLUMN_NAME_TELAIO + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.vettura.COLUMN_NAME_ESITO_BATTERIA + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.vettura.COLUMN_NAME_ESITO_PNEUMATICI + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.vettura.COLUMN_NAME_ESITO_DOCU + TEXT_TYPE + 
			  	    		  " ) ",
  			"CREATE TABLE " + LocalDB.visita.TABLE_NAME + " (" +
			  	    		  LocalDB.visita._ID + INTEGER_TYPE +" PRIMARY KEY," +
			  	    		  LocalDB.visita.COLUMN_NAME_CLIENTE_FK+ INTEGER_TYPE + COMMA_SEP +
			
			  	    		  LocalDB.visita.COLUMN_NAME_DATA + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_Q_CHECK + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_Q_CHECK_RTQ + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_DISS + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_DISS_2 + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_DISS_8 + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_DISS_RICH_TECNICHE + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_DISS_INFO + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_ATTREZZATURA + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_RUOLI_DBO + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_CC_AZIONI_RICHIAMO + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_RR_STAMPA_CORRETTIVI + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_CSS + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_ODL + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_FORMAZIONE + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_RELAZIONE + TEXT_TYPE + COMMA_SEP +
			  	    		  LocalDB.visita.COLUMN_NAME_NOTE + TEXT_TYPE + 
			  	    		  " ) ",  
	};

	private static final String SQL_DELETE_ENTRIES[] = {
			"DROP TABLE IF EXISTS " + LocalDB.DB.TABLE_NAME,
			"DROP TABLE IF EXISTS " + LocalDB.Cliente.TABLE_NAME,
	};
	
	// If you change the database schema, you must increment the database version.
    public static final String DATABASE_NAME = LocalDB.DATABASE_NAME;
    public static final int DATABASE_VERSION = LocalDB.DATABASE_VERSION;

    public SchemaManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
    }

    public void onDelete(SQLiteDatabase db) {
        int i = SQL_DELETE_ENTRIES.length;
        
    	Utility.log("onDelete (" + i +")");

    	for (int k=0; k<i; k++) {
            Utility.log("---" + SQL_DELETE_ENTRIES[k]);
            db.execSQL(SQL_DELETE_ENTRIES[k]);
        }
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        int i = SQL_CREATE_ENTRIES.length;

        Utility.log("onCreate (" + i +")");
        onDelete(db);

    	for (int k=0; k<i; k++) {
            Utility.print("---" + SQL_CREATE_ENTRIES[k]);
            db.execSQL(SQL_CREATE_ENTRIES[k]);
        }

    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Utility.log("onUpgrade");
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onDelete(db);
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
