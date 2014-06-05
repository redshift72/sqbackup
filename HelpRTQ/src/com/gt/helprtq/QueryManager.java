package com.gt.helprtq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

public class QueryManager {
    private SchemaManager sm;
    private SQLiteDatabase db;
    
    private String dbStato = "DB da verificare";
    
	/*      Queste sono per inserire una riga su una tabella

	values.put(LocalDB.Cliente.COLUMN_NAME_NOME, "Cliente "+i);
	values.put(LocalDB.Cliente.COLUMN_NAME_TELEFONO, "tel number. " + i);
	values.put(LocalDB.Cliente.COLUMN_NAME_INDIRIZZO, "indirizzo "+ i);
	
	QueryManager q = new QueryManager(getApplicationContext());
	q.openDB(true);
	Long numRow = q.insertRow(LocalDB.Cliente.TABLE_NAME, values);
	q.closeDB();
	 */
		

	/*      Queste sono per leggere una tabella
	 * 
	String[] select = {
			LocalDB.Cliente._ID,
			LocalDB.Cliente.COLUMN_NAME_NOME,
			LocalDB.Cliente.COLUMN_NAME_INDIRIZZO,
			LocalDB.Cliente.COLUMN_NAME_TELEFONO,
	    };

	
	// How you want the results sorted in the resulting Cursor
	String where = LocalDB.Cliente.COLUMN_NAME_NOME + " = ? " +
	        " AND " + LocalDB.Cliente.COLUMN_NAME_TELEFONO + " = ?";
	String[] whereArgs = {"Giuseppe", "3487069822"};
	String groupBy = null, having = null;
	String sortOrder = LocalDB.Cliente._ID + " ASC";
	
	QueryManager q = new QueryManager(getApplicationContext());
	q.openDB(true);
	Cursor c = q.getRows(LocalDB.Cliente.TABLE_NAME, select, null, null, null, null, sortOrder);
	
	for (int i = 0; i c!=null && < c.getCount(); i++) {
		id = c.getInt( c.getColumnIndexOrThrow(LocalDB.Cliente._ID) );
		nome = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_NOME) );
		indirizzo = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_INDIRIZZO) );
		telefono = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_TELEFONO) );
		...
	*/

		
	/*      Queste per eliminare delle righe

	// Define 'where' part of query.
	String where = LocalDB.Cliente._ID + " = ?";
	// Specify arguments in placeholder order.
	String[] whereArgs = { String.valueOf(id) };
	
	QueryManager q = new QueryManager(getApplicationContext());
	q.openDB(true);
	q.deleteRow(LocalDB.Cliente.TABLE_NAME, where, whereArgs);
    q.closeDB();

	*/        

		
	/*		Queste per aggiornare delle righe

	// New value for one column
	ContentValues set = new ContentValues();
	values.put(LocalDB.Cliente.COLUMN_NAME_NOME, nuovoNome);

	// Which row to update, based on the ID
	String where = LocalDB.Cliente.COLUMN_NAME_NOME + " LIKE ?";
	String[] whereArgs = { String.valueOf(vecchioNome) };

	QueryManager q = new QueryManager(getApplicationContext());
	q.openDB(true);
	int numRow = q.updateRow(LocalDB.DB.TABLE_NAME, set, where, whereArgs);
    q.closeDB();
	*/      

    public QueryManager(Context context) {
		sm = new SchemaManager(context);
	}
	
	public void openDB(boolean write) {
        Utility.log("openDB");
		if (write==false) {
	        Utility.log("--- getReadableDatabase");
	        // Gets the data repository in read mode
			db = sm.getReadableDatabase();
		} else {
	        Utility.log("--- getWritableDatabase");
	        // Gets the data repository in write mode
			db = sm.getWritableDatabase();
		}
    }
	
	public void closeDB() {
        Utility.log("closeDB");
	    if (db != null) {
	        Utility.log("--- DB closed");
	    	db.close();
	    }
	}
	
	public Long insertRow(String tableName, ContentValues values){
        Utility.log("insertRow");
        Utility.log("  ("+tableName+"): " + values);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
        		tableName,
        		"null",
                values);   
        
        return newRowId;
	}
	
	public Cursor getRows(String tableName, String[] select, String where, String[] whereArgs, String groupBy, String having, String sortOrder){
        int i;
        if (select != null) i = select.length; else i=0;

        Utility.log("getRows");
        for (int k=0; k<i; k++) { Utility.log("  ("+tableName+"): select " + select[k]); }
        Utility.log("  ("+tableName+"): where " + where);
        
        if (whereArgs != null) i = whereArgs.length; else i=0;

        for (int k=0; k<i; k++) { Utility.log("  ("+tableName+"): whereArgs " + whereArgs[k]); }
        Utility.log("  ("+tableName+"): groupBy " + groupBy);
        Utility.log("  ("+tableName+"): having " + having);
        Utility.log("  ("+tableName+"): sortOrder " + sortOrder);
		
        // Gets the data repository in write mode

		//db.beginTransaction();
		Cursor c = db.query(
				tableName,  							  // The table to query
				select,                               // The columns to return
			    where,                                	  // The columns for the WHERE clause
			    whereArgs,                            	  // The values for the WHERE clause
			    groupBy,                                  // don't group the rows
			    having,                                   // don't filter by row groups
			    sortOrder                                 // The sort order
			    );
		//db.endTransaction();
		
        Utility.log("  ("+tableName+"): moveToFirst ");
		//mDBSchema.onCreate(db);
		c.moveToFirst();
		
		return c;		
	}
	
	public void deleteRow(String tableName, String where, String[] whereArgs) {
        int i;
        if (whereArgs != null) i = whereArgs.length; else i=0;

        Utility.log("deleteRow");
        Utility.log("  ("+tableName+"): selection " + where);
        for (int k=0; k<i; k++) { Utility.log("  ("+tableName+"): selectionArgs " + whereArgs[k]); }
		
		//db.beginTransaction();
		i = db.delete(tableName, where, whereArgs);
        Utility.log("  ("+tableName+"): number row deleted " + i);
		//db.endTransaction();
	}
	
	public int updateRow(String tableName, ContentValues set, String where, String[] whereArgs) {
        int i;
        if (whereArgs != null) i = whereArgs.length; else i=0;

        Utility.log("updateRow");
        Utility.log("  ("+tableName+"): set " + set);
        Utility.log("  ("+tableName+"): selection " + where);
        for (int k=0; k<i; k++) { Utility.log("  ("+tableName+"): selectionArgs " + whereArgs[k]); }
		
		//db.beginTransaction();
		i = db.update(
				tableName,
				set,
				where,
				whereArgs);
		//db.endTransaction();

        Utility.log("  ("+tableName+"): number row updated " + i);
        Utility.log("  ("+tableName+"): number row updated " + db.inTransaction());
		
		return i;
	}
	
    public String verifySchema(Context context) {
		Utility.log("verifySchema - IN");
		ContentValues values;
		
		try {
		    SchemaManager sm = new SchemaManager(context);;
		    SQLiteDatabase db = sm.getWritableDatabase();;

		    // Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
					LocalDB.DB._ID,
					LocalDB.DB.COLUMN_NAME_VERSIONE,
					LocalDB.DB.COLUMN_NAME_STATO,
			    };

			// How you want the results sorted in the resulting Cursor
			String sortOrder = LocalDB.DB.COLUMN_NAME_VERSIONE + " DESC";
			Cursor c = db.query(
					LocalDB.DB.TABLE_NAME,  							  // The table to query
				    projection,                               // The columns to return
				    null,                                	  // The columns for the WHERE clause
				    null,                            	  // The values for the WHERE clause
				    null,                                  // don't group the rows
				    null,                                   // don't filter by row groups
				    sortOrder                                 // The sort order
				    );
			c.moveToFirst();

			this.dbStato = "DB tabella chiave letta";
	        return this.dbStato;
		} catch (CursorIndexOutOfBoundsException e) {
			// TODO: handle exception
	        this.dbStato = "DB tabella stato è vuota";
			Utility.log(this.dbStato);

			// Create a new map of values, where column names are the keys
	        values = new ContentValues();
	        values.put(LocalDB.DB.COLUMN_NAME_VERSIONE, LocalDB.DATABASE_VERSION);
	        values.put(LocalDB.DB.COLUMN_NAME_STATO, dbStato);
	        
	        // Insert the new row, returning the primary key value of the new row
	        db.insert(
	        		LocalDB.DB.TABLE_NAME,
	        		"null",
	                values);   

	        this.dbStato = "DB creato";
	        return this.dbStato;
		} catch (RuntimeException e) {
			// TODO: handle exception
			this.dbStato = "DB da creare";
			Utility.log(this.dbStato);
			
			sm.onCreate(db);

			// Create a new map of values, where column names are the keys
	        values = new ContentValues();
	        values.put(LocalDB.DB.COLUMN_NAME_VERSIONE, LocalDB.DATABASE_VERSION);
	        values.put(LocalDB.DB.COLUMN_NAME_STATO, dbStato);
	        
	        // Insert the new row, returning the primary key value of the new row
	        db.insert(
	        		LocalDB.DB.TABLE_NAME,
	        		"null",
	                values);   

	        this.dbStato = "DB creato";
	        return this.dbStato;
		} finally {
			Utility.log("verifySchema - OUT");
		}
    }
    
	
}
