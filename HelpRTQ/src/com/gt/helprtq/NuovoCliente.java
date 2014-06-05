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

public class NuovoCliente extends ActionBarActivity {
	int idTupla = -1;
	
	String nome = null;
	String indirizzo = null;
	String codice = null;
	String marche= null;
	String telefono = null;

	String[] listaMarche = null;
	String[] audi = null;
	String[] vw = null;
	String[] skoda = null;
	String[] seat = null;
	String[] vic = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuovo_cliente);
        
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuovo_cliente, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_nuovo_cliente,
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

        idTupla = intent.getIntExtra(pkg+".idCliente", -1);
        this.idTupla = idTupla;
        Utility.log("idCliente " + idTupla);
        
        if (idTupla != -1) {
        	nome = intent.getStringExtra(pkg+".nomeCliente");          
            indirizzo = intent.getStringExtra(pkg+".indirizzoCliente");          
            telefono = intent.getStringExtra(pkg+".telefonoCliente");          
            codice = intent.getStringExtra(pkg+".codiceCliente");          
            marche = intent.getStringExtra(pkg+".marcheCliente");          
                                    
            if (nome == null) nome = ""; 
            if (telefono == null) telefono = ""; 
            if (indirizzo == null) indirizzo = ""; 
            if (codice == null) codice = ""; 
            //1:audi - 2:VW - 3:Skoda - 4:SEAT - 5:VIC
            if (marche == null || marche.length() != 19) marche = "1,0;2,0;3,0;4,0;5,0"; 

            EditText etNome=(EditText) findViewById(R.id.etClienteNome);        
            EditText etTelefono=(EditText) findViewById(R.id.etClienteTelefono);        
            EditText etIndirizzo=(EditText) findViewById(R.id.etClienteIndirizzo);
            EditText etCodice=(EditText) findViewById(R.id.etclienteCodiceOrganizzato);
            ToggleButton tbAudi=(ToggleButton) findViewById(R.id.tb_audi);
            ToggleButton tbVW=(ToggleButton) findViewById(R.id.tb_vw);
            ToggleButton tbSkoda=(ToggleButton) findViewById(R.id.tb_skoda);
            ToggleButton tbSEAT=(ToggleButton) findViewById(R.id.tb_seat);
            ToggleButton tbVIC=(ToggleButton) findViewById(R.id.tb_vic);
            
            etNome.setText(nome);
            etTelefono.setText(telefono);
            etIndirizzo.setText(indirizzo);
            etCodice.setText(codice);
            
            listaMarche = marche.split(";");
            audi = listaMarche[0].split(",");
            vw = listaMarche[1].split(",");
            skoda = listaMarche[2].split(",");
            seat = listaMarche[3].split(",");
            vic = listaMarche[4].split(",");
            
            tbAudi.setChecked(false);
            if (audi[1].equals("1")) tbAudi.setChecked(true);
            tbVW.setChecked(false);
            if (vw[1].equals("1")) tbVW.setChecked(true);
            tbSkoda.setChecked(false);
            if (skoda[1].equals("1")) tbSkoda.setChecked(true);
            tbSEAT.setChecked(false);
            if (seat[1].equals("1")) tbSEAT.setChecked(true);
            tbVIC.setChecked(false);
            if (vic[1].equals("1")) tbVIC.setChecked(true);
        } else {
            if (nome == null) nome = ""; 
            if (telefono == null) telefono = ""; 
            if (indirizzo == null) indirizzo = ""; 
            if (codice == null) codice = ""; 
            //1:audi - 2:VW - 3:Skoda - 4:SEAT - 5:VIC
            if (marche == null || marche.length() != 19) marche = "1,0;2,0;3,0;4,0;5,0"; 

            listaMarche = marche.split(";");
            audi = listaMarche[0].split(",");
            vw = listaMarche[1].split(",");
            skoda = listaMarche[2].split(",");
            seat = listaMarche[3].split(",");
            vic = listaMarche[4].split(",");

            ToggleButton tbAudi=(ToggleButton) findViewById(R.id.tb_audi);
            ToggleButton tbVW=(ToggleButton) findViewById(R.id.tb_vw);
            ToggleButton tbSkoda=(ToggleButton) findViewById(R.id.tb_skoda);
            ToggleButton tbSEAT=(ToggleButton) findViewById(R.id.tb_seat);
            ToggleButton tbVIC=(ToggleButton) findViewById(R.id.tb_vic);
            
            tbAudi.setChecked(false);
            if (audi[1].equals("1")) tbAudi.setChecked(true);
            tbVW.setChecked(false);
            if (vw[1].equals("1")) tbVW.setChecked(true);
            tbSkoda.setChecked(false);
            if (skoda[1].equals("1")) tbSkoda.setChecked(true);
            tbSEAT.setChecked(false);
            if (seat[1].equals("1")) tbSEAT.setChecked(true);
            tbVIC.setChecked(false);
            if (vic[1].equals("1")) tbVIC.setChecked(true);
        	
        }
	}

	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
		ToggleButton tb = (ToggleButton) view;
	    boolean on = tb.isChecked();

	    if (tb.getId() == R.id.tb_audi) {
		    if (on) {
		        // Enable vibrate
		    	audi[1] = "1";
		    } else {
		        // Disable vibrate
		    	audi[1] = "0";
		    }
	    }
	    if (tb.getId() == R.id.tb_vw) {
		    if (on) {
		        // Enable vibrate
		    	vw[1] = "1";
		    } else {
		        // Disable vibrate
		    	vw[1] = "0";
		    }
	    }
	    if (tb.getId() == R.id.tb_skoda) {
		    if (on) {
		        // Enable vibrate
		    	skoda[1] = "1";
		    } else {
		        // Disable vibrate
		    	skoda[1] = "0";
		    }
	    }
	    if (tb.getId() == R.id.tb_seat) {
		    if (on) {
		        // Enable vibrate
		    	seat[1] = "1";
		    } else {
		        // Disable vibrate
		    	seat[1] = "0";
		    }
	    }
	    if (tb.getId() == R.id.tb_vic) {
		    if (on) {
		        // Enable vibrate
		    	vic[1] = "1";
		    } else {
		        // Disable vibrate
		    	vic[1] = "0";
		    }
	    }
	}
	
	public void salva (View arg0) {
        Utility.log("salvaCliente INI");
		EditText etCN = (EditText) findViewById(R.id.etClienteNome);
		EditText etCT = (EditText) findViewById(R.id.etClienteTelefono);
		EditText etCI = (EditText) findViewById(R.id.etClienteIndirizzo);
		EditText etCCod = (EditText) findViewById(R.id.etclienteCodiceOrganizzato);

        nome = etCN.getText().toString();
        telefono = etCT.getText().toString();
        indirizzo = etCI.getText().toString();
        codice = etCCod.getText().toString();
        marche = audi[0] + "," + audi[1] + ";" +
                 vw[0] + "," + vw[1] + ";" +
                 skoda[0] + "," + skoda[1] + ";" +
                 seat[0] + "," + seat[1] + ";" +
                 vic[0] + "," + vic[1];

        String badValue[] = {null, ""};
        //boolean procedi=true;
        
        nome = Utility.checkTextField(nome, badValue, "", -1);
        telefono = Utility.checkTextField(telefono, badValue, "", -1);
        indirizzo = Utility.checkTextField(indirizzo, badValue, "", -1);
        codice = Utility.checkTextField(codice, badValue, "", -1);
        marche = Utility.checkTextField(marche, badValue, "1,0;2,0;3,0;4,0;5,0", 19);

        Utility.log("--- nome " + nome);
		Utility.log("--- telefono " + telefono);
		Utility.log("--- indirizzo " + indirizzo);
		Utility.log("--- codice " + codice);
		Utility.log("--- marche " + marche);
        
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
        values.put(LocalDB.Cliente.COLUMN_NAME_NOME, nome);
        values.put(LocalDB.Cliente.COLUMN_NAME_TELEFONO, telefono);
        values.put(LocalDB.Cliente.COLUMN_NAME_INDIRIZZO, indirizzo);
        values.put(LocalDB.Cliente.COLUMN_NAME_CODICE_ORGANIZZATO, codice);
        values.put(LocalDB.Cliente.COLUMN_NAME_MARCHE, marche);
        
		Utility.log("idCliente " + idTupla);

        if (idTupla == -1) {
			/*      
			 * 		Queste sono per inserire una riga su una tabella  
			 */ 
			QueryManager q = new QueryManager(getApplicationContext());
			q.openDB(true);
	        long numRow = q.insertRow(LocalDB.Cliente.TABLE_NAME, values);
	        q.closeDB();
	        
	        idTupla = (int) numRow;
        } else {

        	// Which row to update, based on the ID
        	String selection = LocalDB.Cliente._ID + " = ?";
        	String[] selectionArgs = { String.valueOf(idTupla) };

        	QueryManager q = new QueryManager(getApplicationContext());
        	q.openDB(true);
        	q.updateRow(LocalDB.Cliente.TABLE_NAME, values, selection, selectionArgs);
            q.closeDB();
        }

        this.finish();

        Utility.log("salvaCliente END");
	}
}
