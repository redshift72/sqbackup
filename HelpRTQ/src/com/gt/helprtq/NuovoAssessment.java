package com.gt.helprtq;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ToggleButton;

public class NuovoAssessment extends ActionBarActivity {
	int idTupla = -1;
    int cliente_fk = -1;
    int visita_fk = -1;
	String data = null;
	String rs = null;
	String rc = null;
	String consegnato = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuovo_assessment);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuovo_assessment, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_nuovo_assessment, container, false);
			return rootView;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		Intent intent=getIntent(); // l'intent di questa activity
        
        String pkg=getPackageName();

    	// CAMPI DELLA TUPLA
    	int idTupla;

        idTupla = intent.getIntExtra(pkg+".idAssessment", -1);
        this.idTupla = idTupla;

        cliente_fk = intent.getIntExtra(pkg+".idCliente", -1);
        visita_fk = intent.getIntExtra(pkg+".idVisita", -1);
        
        Utility.log("idAssessment " + idTupla);
        Utility.log("idCliente " + cliente_fk);
        Utility.log("idVisita " + visita_fk);
        
        if (idTupla != -1) {
    		// TODO Auto-generated method stub
            data = intent.getStringExtra(pkg+".data");          
            rc = intent.getStringExtra(pkg+".rc");          
            rs = intent.getStringExtra(pkg+".rs");          
            consegnato = intent.getStringExtra(pkg+".consegnato");          
                                    
            if (data == null) data = ""; 
            if (rc == null) rc = ""; 
            if (rs == null) rs = ""; 
            if (consegnato == null) consegnato = ""; 

            EditText etData=(EditText) findViewById(R.id.etData);
            EditText etRC=(EditText) findViewById(R.id.etRC);
            EditText etRS=(EditText) findViewById(R.id.etRS);
            ToggleButton etConsegnato=(ToggleButton) findViewById(R.id.tb_as_consegnato);
            
            etData.setText(data);
            etRC.setText(rc);
            etRS.setText(rs);
            etConsegnato.setChecked(false);
            if (consegnato.equals("1")) etConsegnato.setChecked(true);
        }
	}
	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
		ToggleButton tb = (ToggleButton) view;
	    boolean on = tb.isChecked();
	    
	    if (on) {
	        // Enable vibrate
	    	consegnato = "1";
	    } else {
	        // Disable vibrate
	    	consegnato = "0";
	    }
	}
	
	public void salva (View arg0) {
        Utility.log("salva INI");
        EditText etData=(EditText) findViewById(R.id.etData);
        EditText etRC=(EditText) findViewById(R.id.etRC);
        EditText etRS=(EditText) findViewById(R.id.etRS);

        data = etData.getText().toString();
        rc = etRC.getText().toString();
        rs = etRS.getText().toString();

        String badValue[] = {null};
        boolean procedi=true;
        
        procedi = Utility.checkDateField(data, badValue, getApplicationContext());
        rc = Utility.checkTextField(rc, badValue, "", -1);
        rs = Utility.checkTextField(rs, badValue, "", -1);
        consegnato = Utility.checkTextField(consegnato, badValue, "0", 1);

		Utility.log("--- data " + data);
		Utility.log("--- rc " + rc);
		Utility.log("--- rs " + rs);
		Utility.log("--- consegnato " + consegnato);
        
		if (procedi == true) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
	        values.put(LocalDB.Assessment.COLUMN_NAME_DATA, data);
	        values.put(LocalDB.Assessment.COLUMN_NAME_RC, rc);
	        values.put(LocalDB.Assessment.COLUMN_NAME_RS, rs);
	        values.put(LocalDB.Assessment.COLUMN_NAME_CONSEGNATO, consegnato);
	        
			Utility.log("idAssessment " + idTupla);
	
			if (this.cliente_fk != -1) {
	            values.put(LocalDB.Assessment.COLUMN_NAME_CLIENTE_FK, this.cliente_fk);
	        }
	        if (this.visita_fk != -1) {
	            values.put(LocalDB.Assessment.COLUMN_NAME_VISITA_FK, this.visita_fk);
	        }
	        
			Utility.log("cliente_fk " + cliente_fk);
			Utility.log("visita_fk " + visita_fk);
	
	        if (idTupla == -1) {
				/*      
				 * 		Queste sono per inserire una riga su una tabella  
				 */ 
				QueryManager q = new QueryManager(getApplicationContext());
				q.openDB(true);
		        long numRow = q.insertRow(LocalDB.Assessment.TABLE_NAME, values);
		        q.closeDB();
		        
		        idTupla = (int) numRow;
	        } else {
	
	        	// Which row to update, based on the ID
	        	String selection = LocalDB.Cliente._ID + " = ?";
	        	String[] selectionArgs = { String.valueOf(idTupla) };
	
	        	QueryManager q = new QueryManager(getApplicationContext());
	        	q.openDB(true);
	        	q.updateRow(LocalDB.Assessment.TABLE_NAME, values, selection, selectionArgs);
	            q.closeDB();
	        }
	
	        this.finish();
		}

        Utility.log("salva END");
	}
}
