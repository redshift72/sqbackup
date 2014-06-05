package com.gt.helprtq;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ObjectManager {
	private ActionBarActivity main;
	private String tableName;
	private int baseId;
	private int idLayout;
	private String[] select = null;
	private String where = null;
	private String[] whereArgs = null;
	private String sortOrder = null;
	
	
	private TextView firstTV;
	private int idMax, id = -1;
	private Integer pos;
	private boolean booClearScreen;
	
	ObjectManager(ActionBarActivity main, String tableName, int baseId, int idLayout) {
		this.main = (ActionBarActivity) main;
		this.tableName = tableName;
		this.baseId = baseId;
		this.idLayout = idLayout;
	}
	
	public int getIdMax() {
		return idMax;
	}

	public void setIdMax(int idMax) {
		this.idMax = idMax;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}
	
	public boolean getBooClearScreen() {
		return booClearScreen;
	}

	public void setBooClearScreen(boolean booClearScreen) {
		this.booClearScreen = booClearScreen;
	}

	public String[] getSelect() {
		return select;
	}

	public void setSelect(String[] select) {
		this.select = select;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String[] getWhereArgs() {
		return whereArgs;
	}

	public void setWhereArgs(String[] whereArgs) {
		this.whereArgs = whereArgs;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getBaseId() {
		return baseId;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}

	public TextView getFirstTV() {
		return firstTV;
	}

	public void setFirstTV(TextView firstTV) {
		this.firstTV = firstTV;
	}

	public int getIdLayout() {
		return idLayout;
	}

	public Cursor drawScreen(String where, String[] whereArgs) {
		// TODO Auto-generated method stub
		QueryManager q = new QueryManager(main.getApplicationContext());
		q.openDB(true);
		Cursor c = q.getRows(tableName, select, where, whereArgs, null, null, sortOrder);
		
		idMax = c.getCount();
        q.closeDB();
        
        return c;
		
	}

	public void clearScreen() {
		// TODO Auto-generated method stub
		LinearLayout ll = (LinearLayout) main.findViewById(idLayout);
        
        if (ll==null) Utility.log("ll NULLO?!??!??!");

		Utility.log("--- idMax: " + getIdMax());
        for (int pos=0; pos < getIdMax(); pos++) {
    		Utility.log("--- pos: " + pos);
        	ll.removeViewAt(0);
        }
		
	}

	public boolean IsRemovable(String[] tableName, String[][] select, String[] where, String[][] whereArgs) {
		boolean result = true;
		
		if (getId() != -1) {
			QueryManager q;
			Cursor c;
			int i = 0;
			
			for (i=0; i < tableName.length; i++) {
				q = new QueryManager(main.getApplicationContext());
				q.openDB(true);
				c = q.getRows(tableName[i], select[i], where[i], whereArgs[i], null, null, null);
				
				if (c.getCount() > 0) {
					result = false;
				}
						
		        q.closeDB();
			}
			
			
		}
		
		return result;
	}

	public void removeObject() {
		// TODO Auto-generated method stub
		if (getId() != -1) {

			// Define 'where' part of query.
			String where = LocalDB.Cliente._ID + " = ?";
			// Specify arguments in placeholder order.
			String[] whereArgs = { String.valueOf(getId()) };
			
			QueryManager q = new QueryManager(main.getApplicationContext());
			q.openDB(true);
			q.deleteRow(tableName, where, whereArgs);
	        q.closeDB();
//	        
//	        LinearLayout ll = (LinearLayout) main.findViewById (idLayout);
//
//	        TextView tv;
//	        Utility.log("---findViewById(id) "+ getId());
//        	tv = (TextView) main.findViewById(getId());
//	        Utility.log("---tv.setTag(-1) "+ -1);
//	        int x = -1;
//        	tv.setTag(x);
//	        for (int i = getPos()+1, k=getId()+1; i < ll.getChildCount(); i++, k++) {
//		        Utility.log("---findViewById(k) "+ k);
//	        	tv = (TextView) main.findViewById(k);
//		        Utility.log("---tv.setTag(i-1) "+ (i-1));
//	        	tv.setTag(i-1);
//	        }
//	        
//	        int tmpMax = getId();
//	        tmpMax--;
//	        setIdMax(tmpMax);
//	        
//	        ll.removeViewAt(getPos());
//	        Utility.log("---ll.removeViewAt(pos) "+ getPos());
//	        Utility.log("---tmpMax "+ tmpMax);
    	}
		
	}

	public Cursor updateObject(Class<?> classe, Intent intent) {
		// TODO Auto-generated method stub
		Cursor c = null;
    	if (getId() != -1) {
    		
	
	    	// How you want the results sorted in the resulting Cursor
	    	String where = LocalDB.Cliente._ID + " = ? ";
	    	String[] whereArgs = { String.valueOf(getId()) };
	    	String groupBy = null, having = null;
	    	String sortOrder = null;
	    	
	    	QueryManager q = new QueryManager(main.getApplicationContext());
	    	q.openDB(true);
	    	c = q.getRows(tableName, select, where, whereArgs, groupBy, having, sortOrder);
	    	q.closeDB();
    	}
    	
    	return c;
	}

	public void requestFocus2First() {
		// TODO Auto-generated method stub
		this.firstTV.requestFocus();
	}

}
