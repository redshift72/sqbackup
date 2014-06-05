package com.gt.helprtq;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ToggleButton;

public class NuovoAudit extends ActionBarActivity {
	int idTupla = -1;
    int cliente_fk = -1;
    int visita_fk = -1;
    String data_mail = null;
    String esito = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuovo_audit);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuovo_audit, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_nuovo_audit,
					container, false);
			return rootView;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();

		Intent intent=getIntent(); // l'intent di questa activity
        
        String pkg=getPackageName();

        int idTupla;

        idTupla = intent.getIntExtra(pkg+".idAudit", -1);
        this.idTupla = idTupla;
        
        cliente_fk = intent.getIntExtra(pkg+".idCliente", -1);
        visita_fk = intent.getIntExtra(pkg+".idVisita", -1);
        
        Utility.log("idAudit " + idTupla);
        Utility.log("idCliente " + cliente_fk);
        Utility.log("idVisita " + visita_fk);
        
        if (idTupla != -1) {
    		// TODO Auto-generated method stub
            data_mail = intent.getStringExtra(pkg+".data_mail");          
            esito = intent.getStringExtra(pkg+".esito");          
                                    
            if (data_mail == null) data_mail = ""; 
            if (esito == null) esito = ""; 

            EditText etMail=(EditText) findViewById(R.id.etDataMail);
            ToggleButton etEsito=(ToggleButton) findViewById(R.id.tbEsito);
            
            etMail.setText(data_mail);
            etEsito.setChecked(false);
            if (esito.equals("1")) etEsito.setChecked(true);
        }
	}
	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	    if (on) {
	        // Enable vibrate
		    esito = "1";
	    } else {
	        // Disable vibrate
		    esito = "0";
	    }
	}
	
	public void salva (View arg0) {
        Utility.log("salva INI");
        
        EditText etMail=(EditText) findViewById(R.id.etDataMail);
        //EditText etEsito=(EditText) findViewById(R.id.etEsito);

        data_mail = etMail.getText().toString();
        //esito = etEsito.getText().toString();

        String badValue[] = {null};
        boolean procedi=true;
        
        procedi = Utility.checkDateField(data_mail, badValue, getApplicationContext());
        esito = Utility.checkTextField(esito, badValue, "0", 1);

		Utility.log("--- data_mail " + data_mail);
		Utility.log("--- esito " + esito);
        
		if (procedi == true) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
	        values.put(LocalDB.Audit.COLUMN_NAME_DATA_MAIL, data_mail);
	        values.put(LocalDB.Audit.COLUMN_NAME_ESITO, esito);
	
			Utility.log("idAudit " + idTupla);
	
			if (this.cliente_fk != -1) {
	            values.put(LocalDB.Audit.COLUMN_NAME_CLIENTE_FK, this.cliente_fk);
	        }
	        if (this.visita_fk != -1) {
	            values.put(LocalDB.Audit.COLUMN_NAME_VISITA_FK, this.visita_fk);
	        }
	        
			Utility.log("cliente_fk " + cliente_fk);
			Utility.log("visita_fk " + visita_fk);
	
	        if (idTupla == -1) {
				/*      
				 * 		Queste sono per inserire una riga su una tabella  
				 */ 
				QueryManager q = new QueryManager(getApplicationContext());
				q.openDB(true);
		        long numRow = q.insertRow(LocalDB.Audit.TABLE_NAME, values);
		        q.closeDB();
		        
		        idTupla = (int) numRow;
	        } else {
	
	        	// Which row to update, based on the ID
	        	String selection = LocalDB.Cliente._ID + " = ?";
	        	String[] selectionArgs = { String.valueOf(idTupla) };
	
	        	QueryManager q = new QueryManager(getApplicationContext());
	        	q.openDB(true);
	        	q.updateRow(LocalDB.Audit.TABLE_NAME, values, selection, selectionArgs);
	            q.closeDB();
	        }
	
	        this.finish();
		}

        Utility.log("salva END");
	}
}
