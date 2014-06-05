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

public class NuovaVettura extends ActionBarActivity {
	int idTupla = -1;
    int cliente_fk = -1;
    int visita_fk = -1;
	String telaio = null;
	String esito_batteria = null;
	String esito_pneumatici = null;
	String esito_docu = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuova_vettura);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuova_vettura, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_nuova_vettura,
					container, false);
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

        idTupla = intent.getIntExtra(pkg+".idVettura", -1);
        this.idTupla = idTupla;

        cliente_fk = intent.getIntExtra(pkg+".idCliente", -1);
        visita_fk = intent.getIntExtra(pkg+".idVisita", -1);
        
        Utility.log("onResume() -> idVettura " + idTupla);
        Utility.log("onResume() -> idCliente " + cliente_fk);
        Utility.log("onResume() -> idVisita " + visita_fk);
        
        if (idTupla != -1) {
    		// TODO Auto-generated method stub
            telaio = intent.getStringExtra(pkg+".telaio");          
            esito_batteria = intent.getStringExtra(pkg+".esito_batteria");          
            esito_pneumatici = intent.getStringExtra(pkg+".esito_pneumatici");          
            esito_docu = intent.getStringExtra(pkg+".esito_docu");          
                                    
            if (telaio == null) telaio = ""; 
            if (esito_batteria == null) esito_batteria = ""; 
            if (esito_pneumatici == null) esito_pneumatici = ""; 
            if (esito_docu == null) esito_docu = ""; 

            EditText etTelaio =(EditText) findViewById(R.id.et_ve_telaio);
            ToggleButton etEsitoB =(ToggleButton) findViewById(R.id.tb_ve_esito_batteria);
            ToggleButton etEsitoP =(ToggleButton) findViewById(R.id.tb_ve_esito_pneumatici);
            ToggleButton etEsitoD =(ToggleButton) findViewById(R.id.tb_ve_esito_docu);
            
            etTelaio.setText(telaio);
            etEsitoB.setChecked(false);
            if (esito_batteria.equals("1")) etEsitoB.setChecked(true);
            etEsitoP.setChecked(false);
            if (esito_pneumatici.equals("1")) etEsitoP.setChecked(true);
            etEsitoD.setChecked(false);
            if (esito_docu.equals("1")) etEsitoD.setChecked(true);
        }
	}

	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
		ToggleButton tb = (ToggleButton) view;
	    boolean on = tb.isChecked();
	    
		if (tb.getId() == R.id.tb_ve_esito_batteria) {
		    if (on) {
		        // Enable vibrate
		    	esito_batteria = "1";
		    } else {
		        // Disable vibrate
		    	esito_batteria = "0";
		    }
		}
		if (tb.getId() == R.id.tb_ve_esito_pneumatici) {
		    if (on) {
		        // Enable vibrate
		    	esito_pneumatici = "1";
		    } else {
		        // Disable vibrate
		    	esito_pneumatici = "0";
		    }
		}
		if (tb.getId() == R.id.tb_ve_esito_docu) {
		    if (on) {
		        // Enable vibrate
		    	esito_docu = "1";
		    } else {
		        // Disable vibrate
		    	esito_docu = "0";
		    }
		}
	}
	

	public void salva (View arg0) {
        Utility.log("salva INI");
        EditText etTelaio =(EditText) findViewById(R.id.et_ve_telaio);
        
    	// CAMPI DELLA TUPLA
    	telaio = etTelaio.getText().toString();
        
        String badValue[] = {null};
        //boolean procedi=true;
        
        telaio = Utility.checkTextField(telaio, badValue, "", -1);
        esito_batteria = Utility.checkTextField(esito_batteria, badValue, "0", 1);
        esito_pneumatici = Utility.checkTextField(esito_pneumatici, badValue, "0", 1);
        esito_docu = Utility.checkTextField(esito_docu, badValue, "0", 1);

        Utility.log("--- telaio " + telaio);
        Utility.log("--- esito_batteria " + esito_batteria);
        Utility.log("--- esito_pneumatici " + esito_pneumatici);
        Utility.log("--- esito_docu " + esito_docu);
       
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
        values.put(LocalDB.vettura.COLUMN_NAME_TELAIO, telaio);
        values.put(LocalDB.vettura.COLUMN_NAME_ESITO_BATTERIA, esito_batteria);
        values.put(LocalDB.vettura.COLUMN_NAME_ESITO_PNEUMATICI, esito_pneumatici);
        values.put(LocalDB.vettura.COLUMN_NAME_ESITO_DOCU, esito_docu);
        
        Utility.log("idPT " + idTupla);

		if (this.cliente_fk != -1) {
            values.put(LocalDB.vettura.COLUMN_NAME_CLIENTE_FK, this.cliente_fk);
        }
        if (this.visita_fk != -1) {
            values.put(LocalDB.vettura.COLUMN_NAME_VISITA_FK, this.visita_fk);
        }
        
        Utility.log("salva() -> cliente_fk " + cliente_fk);
        Utility.log("salva() -> visita_fk " + visita_fk);

        if (idTupla == -1) {
			/*      
			 * 		Queste sono per inserire una riga su una tabella  
			 */ 
			QueryManager q = new QueryManager(getApplicationContext());
			q.openDB(true);
	        long numRow = q.insertRow(LocalDB.vettura.TABLE_NAME, values);
	        q.closeDB();
	        
	        idTupla = (int) numRow;
        } else {

        	// Which row to update, based on the ID
        	String selection = LocalDB.Cliente._ID + " = ?";
        	String[] selectionArgs = { String.valueOf(idTupla) };

        	QueryManager q = new QueryManager(getApplicationContext());
        	q.openDB(true);
        	q.updateRow(LocalDB.vettura.TABLE_NAME, values, selection, selectionArgs);
            q.closeDB();
        }

        this.finish();

        Utility.log("salva END");
	}
}
