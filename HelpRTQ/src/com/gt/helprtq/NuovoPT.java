package com.gt.helprtq;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class NuovoPT extends ActionBarActivity implements OnItemSelectedListener {
	int idTupla = -1;
    int cliente_fk = -1;
    int visita_fk = -1;

    String data = null;
	String marca = null;
	String esito = null;
	String discusso = null;
	String correttivo = null;
	
	String marcheCliente = null;
	
	String[] listaMarche = null;
	String[] audi = null;
	String[] vw = null;
	String[] skoda = null;
	String[] seat = null;
	String[] vic = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuovo_pt);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuovo_pt, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_nuovo_pt,
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

        idTupla = intent.getIntExtra(pkg+".idPT", -1);
        this.idTupla = idTupla;
        Utility.log("idPT " + idTupla);

        cliente_fk = intent.getIntExtra(pkg+".idCliente", -1);
        visita_fk = intent.getIntExtra(pkg+".idVisita", -1);
        marcheCliente = intent.getStringExtra(pkg+".marcheCliente");
        Utility.log("idPT " + idTupla);
        Utility.log("idCliente " + cliente_fk);
        Utility.log("idVisita " + visita_fk);
        Utility.log("marcheCliente " + marcheCliente);

        if (idTupla != -1) {
    		// TODO Auto-generated method stub
            data = intent.getStringExtra(pkg+".data");          
            marca = intent.getStringExtra(pkg+".marca");          
            esito = intent.getStringExtra(pkg+".esito");          
            discusso = intent.getStringExtra(pkg+".discusso");          
            correttivo = intent.getStringExtra(pkg+".correttivo");          
                                    
        }
        if (data == null) data = ""; 
        if (marca == null) marca = ""; 
        if (esito == null) esito = ""; 
        if (discusso == null) discusso = ""; 
        if (correttivo == null) correttivo = ""; 

        EditText etData=(EditText) findViewById(R.id.et_pt_data);
        ToggleButton etEsito=(ToggleButton) findViewById(R.id.tb_pt_esito);
        ToggleButton etDiscusso=(ToggleButton) findViewById(R.id.tb_pt_discusso);
        ToggleButton etCorrettivo=(ToggleButton) findViewById(R.id.tb_pt_correttivo);

        
        etData.setText(data);

        Spinner spinner =(Spinner) findViewById(R.id.spinner);
        //etMarca.setText(marca);
        listaMarche = marcheCliente.split(";");
        audi = listaMarche[0].split(",");
        vw = listaMarche[1].split(",");
        skoda = listaMarche[2].split(",");
        seat = listaMarche[3].split(",");
        vic = listaMarche[4].split(",");

        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("seleziona una voce");
        
        String tokenOk = "*";
        String tempMarca = null;
        int tot=0;
        int pos=0;
        
        tempMarca = "Audi";
        if (audi[1].equals("1")) {
        	tempMarca = "Audi" + tokenOk;
        }
    	spinnerArray.add(tempMarca); tot++;
    	if (marca.contains("Audi")) pos = tot;
    	
        tempMarca = "VW";
        if (vw[1].equals("1")) {
        	tempMarca = "VW" + tokenOk;
        }
    	spinnerArray.add(tempMarca); tot++;
    	if (marca.contains("VW")) pos = tot;
    	
        tempMarca = "Skoda";
        if (skoda[1].equals("1")) {
        	tempMarca = "Skoda" + tokenOk;
        }
    	spinnerArray.add(tempMarca); tot++;
    	if (marca.contains("Skoda")) pos = tot;
    	
        tempMarca = "SEAT";
        if (seat[1].equals("1")) {
        	tempMarca = "SEAT" + tokenOk;
        }
    	spinnerArray.add(tempMarca); tot++;
    	if (marca.contains("SEAT")) pos = tot;
    	
        tempMarca = "VIC";
        if (vic[1].equals("1")) {
        	tempMarca = "VIC" + tokenOk;
        }
    	spinnerArray.add(tempMarca); tot++;
    	if (marca.contains("VIC")) pos = tot;
    	
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerArrayAdapter);
        // Specifica chi gestisce lo spinner
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(pos);
        
        etEsito.setChecked(false);
        if (esito.equals("1")) etEsito.setChecked(true);
        etDiscusso.setChecked(false);
        if (discusso.equals("1")) etDiscusso.setChecked(true);
        etCorrettivo.setChecked(false);
        if (correttivo.equals("1")) etCorrettivo.setChecked(true);
	}
	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
		ToggleButton tb = (ToggleButton) view;
	    boolean on = tb.isChecked();
	    
		Utility.log("--- tb.getId(); " +  tb.getId());

		if (tb.getId() == R.id.tb_pt_correttivo) {
			Utility.log("--- correttivo " +  on);
		    if (on) {
		        // Enable vibrate
			    correttivo = "1";
		    } else {
		        // Disable vibrate
		    	correttivo = "0";
		    }
		}
		if (tb.getId() == R.id.tb_pt_discusso) {
			Utility.log("--- discusso " +  on);
		    if (on) {
		        // Enable vibrate
			    discusso = "1";
		    } else {
		        // Disable vibrate
		    	discusso = "0";
		    }
		}
		if (tb.getId() == R.id.tb_pt_esito) {
			Utility.log("--- esito " +  on);
		    if (on) {
		        // Enable vibrate
			    esito = "1";
		    } else {
		        // Disable vibrate
			    esito = "0";
		    }
		}
	}

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    	marca = parent.getItemAtPosition(pos).toString();
		Utility.log("--- marca " + marca);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
	
	public void salva (View arg0) {
        Utility.log("salva INI");
        EditText etData=(EditText) findViewById(R.id.et_pt_data);
        //EditText etMarca=(EditText) findViewById(R.id.et_pt_marca);

    	// CAMPI DELLA TUPLA
    	data = etData.getText().toString();
    	//marca = etMarca.getText().toString();
        

        String badValue[] = {null};
        boolean procedi=true;
        
        procedi = Utility.checkDateField(data, badValue, getApplicationContext());
        //marca = Utility.checkTextField(marca, badValue, "1,0;2,0;3,0;4,0;5,0", 19);
        esito = Utility.checkTextField(esito, badValue, "0", 1);
        discusso = Utility.checkTextField(discusso, badValue, "0", 1);
        correttivo = Utility.checkTextField(correttivo, badValue, "0", 1);

		Utility.log("--- data " + data);
		Utility.log("--- marca " + marca);
		Utility.log("--- esito " + esito);
		Utility.log("--- discusso " + discusso);
		Utility.log("--- correttivo " + correttivo);
       
		Utility.log("--- procedi " + procedi);
		if (procedi == true) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
	        values.put(LocalDB.pt.COLUMN_NAME_DATA, data);
	        values.put(LocalDB.pt.COLUMN_NAME_MARCA, marca);
	        values.put(LocalDB.pt.COLUMN_NAME_ESITO, esito);
	        values.put(LocalDB.pt.COLUMN_NAME_DISCUSSO, discusso);
	        values.put(LocalDB.pt.COLUMN_NAME_CORRETTIVO, correttivo);
	        
			Utility.log("idPT " + idTupla);
	
			if (this.cliente_fk != -1) {
	            values.put(LocalDB.pt.COLUMN_NAME_CLIENTE_FK, this.cliente_fk);
	        }
	        if (this.visita_fk != -1) {
	            values.put(LocalDB.pt.COLUMN_NAME_VISITA_FK, this.visita_fk);
	        }
	        
			Utility.log("cliente_fk " + cliente_fk);
			Utility.log("visita_fk " + visita_fk);
	
	        if (idTupla == -1) {
				/*      
				 * 		Queste sono per inserire una riga su una tabella  
				 */ 
				QueryManager q = new QueryManager(getApplicationContext());
				q.openDB(true);
		        long numRow = q.insertRow(LocalDB.pt.TABLE_NAME, values);
		        q.closeDB();
		        
		        idTupla = (int) numRow;
	        } else {
	
	        	// Which row to update, based on the ID
	        	String selection = LocalDB.Cliente._ID + " = ?";
	        	String[] selectionArgs = { String.valueOf(idTupla) };
	
	        	QueryManager q = new QueryManager(getApplicationContext());
	        	q.openDB(true);
	        	q.updateRow(LocalDB.pt.TABLE_NAME, values, selection, selectionArgs);
	            q.closeDB();
	        }
	
	        this.finish();
		}
	        
        Utility.log("salva END");
	}
}
