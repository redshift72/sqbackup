package com.gt.helprtq;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Vettura extends ActionBarActivity implements OnClickListener, OnFocusChangeListener {
	/*
	 * per la gestione del cliente
	 */
	ObjectManager object = new ObjectManager(this, LocalDB.vettura.TABLE_NAME, 0, R.id.linearlayout_vettura);
	Class<NuovaVettura> nuovaClasse = NuovaVettura.class;
	
	int linearlayout = R.id.linearlayout_vettura;
	String[] select = {
			LocalDB.vettura._ID,
			LocalDB.vettura.COLUMN_NAME_CLIENTE_FK,
			LocalDB.vettura.COLUMN_NAME_VISITA_FK,
			LocalDB.vettura.COLUMN_NAME_TELAIO,
			LocalDB.vettura.COLUMN_NAME_ESITO_BATTERIA,
			LocalDB.vettura.COLUMN_NAME_ESITO_PNEUMATICI,
			LocalDB.vettura.COLUMN_NAME_ESITO_DOCU,
	    };
	String sortOrder = LocalDB.Cliente._ID + " ASC";
	
    String pkg;
    
	// CAMPI DELLA TUPLA
	int idTupla = -1;
	int cliente_fk = -1;
	int visita_fk = -1;
	String telaio = null;
	String esito_batteria = null;
	String esito_pneumatici = null;
	String esito_docu = null;


	int visita_fkIn = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vettura);

    	// imposto le variabili
		pkg=getPackageName();
		object.setSelect(select);
		object.setSortOrder(sortOrder);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vettura, menu);
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
		if (id == R.id.ve_modifica) {
			modifica();
			return true;
		}
		if (id == R.id.ve_nuovo) {
			nuovo();
			return true;
		}
		if (id == R.id.ve_elimina) {
			elimina();
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
			View rootView = inflater.inflate(R.layout.fragment_vettura,
					container, false);
			return rootView;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		
        Utility.log("onResume INI");

		Intent intent=getIntent(); // l'intent di questa activity
        this.cliente_fk = intent.getIntExtra(pkg+".idCliente", -1);
        this.visita_fk = intent.getIntExtra(pkg+".idVisita", -1);

        visita_fkIn = visita_fk; 
        
        Utility.log("Vettura.onResume() -> visita_fkIn " + visita_fkIn);
        
        disegnaSchermo();
        
        Utility.log("onResume END");
	}

	@Override
	public void onClick(View arg0) {
		modifica();      
	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
        Utility.log("onFocusChange INI");

        if (object.getBooClearScreen()==false) {
	        TextView tv = (TextView) arg0;
			// TODO Auto-generated method stub
			if (arg1 == true) {
				object.setId(tv.getId());
				object.setPos((Integer) tv.getTag());
				
		        Utility.log("---id "+ object.getId());
		        Utility.log("---pos "+ object.getPos());
	            tv.setBackgroundColor(Color.GREEN);
			} else {
				object.setId(-1);
	            tv.setBackgroundColor(Color.GRAY);
			}
			idTupla = object.getId();
        }
			
        Utility.log("onFocusChange END");
	}

	private void modifica() {
		// TODO Auto-generated method stub
        Utility.log("modifica INI");
        Utility.log("---id " + object.getId());

        if (object.getId() != -1) {
            Intent intent = new Intent(this, nuovaClasse);
            Cursor c = object.updateObject(nuovaClasse, intent);
    		intent.putExtra(pkg+".idCliente", cliente_fk);          
    		intent.putExtra(pkg+".idVettura", object.getId());          
    		intent.putExtra(pkg+".idVisita", visita_fkIn);          

    		Utility.log("Vettura.modifica() -> visita_fkIn " + visita_fkIn);

        	for (int i = 0; c!=null && i < c.getCount(); i++) {
        		object.setId(getFields(c));
        		
                setIntentParameter(intent);
        	}

        	startActivity(intent);
        	//pulisciSchermo();
        } else {
	    	CharSequence text = "Nessuna vettura selezionata";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
        	
            Utility.log("--- Nessuna vettura selezionata");
        }
        
        Utility.log("modifica END");
	}


	private void nuovo() {
		// TODO Auto-generated method stub
        Utility.log("nuovo INI");
		Intent intent = new Intent(this, nuovaClasse);
		intent.putExtra(pkg+".idCliente", cliente_fk);          
    	startActivity(intent);
    	
    	//pulisciSchermo();
        Utility.log("nuovo INI");
	}

	private void elimina() {
		// TODO Auto-generated method stub
        if (object.getId() != -1) {
            object.removeObject();
            disegnaSchermo();
        } else {
	    	CharSequence text = "Nessuna vettura selezionata";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
        }
	}

	private void disegnaSchermo() {
		// TODO Auto-generated method stub
		pulisciSchermo();
		
		String where = LocalDB.vettura.COLUMN_NAME_CLIENTE_FK + " = ? ";
		String[] whereArgs;
		if (visita_fk!=-1) {
			where += " AND (" + LocalDB.vettura.COLUMN_NAME_VISITA_FK + " = ? OR " + LocalDB.vettura.COLUMN_NAME_VISITA_FK + " is null)";
			String[] tmpwhereArgs = { String.valueOf(this.cliente_fk), String.valueOf(this.visita_fk) };
			whereArgs = tmpwhereArgs;
		} else {
			where += " AND " + LocalDB.vettura.COLUMN_NAME_VISITA_FK + " is null";
			String[] tmpwhereArgs = { String.valueOf(this.cliente_fk) };
			whereArgs = tmpwhereArgs;
		}
		if (cliente_fk == -1) {
			where = null; 
			whereArgs = null;
		}

		Cursor c = object.drawScreen(where, whereArgs);
		for (int i = 0; i < object.getIdMax(); i++) {
			//select
			idTupla = getFields(c);

		    // get a reference for the TableLayout
		    LinearLayout ll = (LinearLayout) this.findViewById(object.getIdLayout());
		      
		    TextView tv;
		    Spanned text2Html;
		    
			// creo l'oggetto
    	    tv = new TextView(this);
    	    
    	    if (i==0) object.setFirstTV(tv); // mi tengo in memoria il primo per poi dargli il FOCUS sempre
    	    
    	    tv.setId(object.getBaseId()+idTupla);
    	    tv.setHeight(180);
    	    
    	    text2Html = getLayoutRow();

    	    tv.setText(text2Html);
            tv.setTextSize(20);
            tv.setPadding(10, 10, 10, 10);
            tv.setTextColor(Color.BLUE);
            tv.setBackgroundColor(Color.GRAY);
            tv.setGravity(Gravity.LEFT);
            tv.setTextIsSelectable(true);
            tv.setFocusable(true);
            tv.setFocusableInTouchMode(true);
            tv.setClickable(true);
            tv.setOnClickListener(this);
            tv.setOnFocusChangeListener(this);
            tv.setTag(i);

    	    // add the TableRow to the TableLayout
    	    ll.addView(tv);
    	    
    	    c.moveToNext();
        }

		if (object.getIdMax() > 0) object.requestFocus2First();
	}

	private void pulisciSchermo() {
		// TODO Auto-generated method stub
		Utility.log("pulisciCliente INI");
		object.setBooClearScreen(true); 
        
		object.clearScreen();
		
		object.setBooClearScreen(false); 
		Utility.log("pulisciCliente END");
	}

	public int getFields(Cursor c) {
        Utility.log("getFields INI");
		// TODO Auto-generated method stub
		idTupla = c.getInt( c.getColumnIndexOrThrow(LocalDB.vettura._ID) );
		cliente_fk = c.getInt( c.getColumnIndexOrThrow(LocalDB.vettura.COLUMN_NAME_CLIENTE_FK) );
		visita_fk = c.getInt( c.getColumnIndexOrThrow(LocalDB.vettura.COLUMN_NAME_VISITA_FK) );

		
		telaio = c.getString( c.getColumnIndexOrThrow(LocalDB.vettura.COLUMN_NAME_TELAIO) );
		esito_batteria = c.getString( c.getColumnIndexOrThrow(LocalDB.vettura.COLUMN_NAME_ESITO_BATTERIA) );
		esito_pneumatici = c.getString( c.getColumnIndexOrThrow(LocalDB.vettura.COLUMN_NAME_ESITO_PNEUMATICI) );
		esito_docu = c.getString( c.getColumnIndexOrThrow(LocalDB.vettura.COLUMN_NAME_ESITO_DOCU) );

        if (telaio == null) telaio = ""; 
        if (esito_batteria == null) esito_batteria = ""; 
        if (esito_pneumatici == null) esito_pneumatici = ""; 
        if (esito_docu == null) esito_docu = ""; 

        Utility.log("--- id " + object.getId());
		Utility.log("--- cliente_fk " + cliente_fk);
		Utility.log("--- visita_fk " + visita_fk);
		Utility.log("--- telaio " + telaio);
		Utility.log("--- esito_batteria " + esito_batteria);
		Utility.log("--- esito_pneumatici " + esito_pneumatici);
		Utility.log("--- esito_docu " + esito_docu);
		
        Utility.log("getFields END");
		return idTupla;
	}

	public Spanned getLayoutRow() {
		// TODO Auto-generated method stub
        String htmlSource = "" + idTupla + " <b>" + telaio + "</b> " +
        					"<br>" + Utility.convertBooleanField("esito batteria", esito_batteria) + 
        					", " + Utility.convertBooleanField("esito pneumatici ", esito_pneumatici ) + 
        					", " + Utility.convertBooleanField("esito documentazione ", esito_docu );
        
        Spanned text2Html =  Html.fromHtml(htmlSource);
        
        return text2Html;
	}

	public void setIntentParameter(Intent intent) {
		// TODO Auto-generated method stub
		intent.putExtra(pkg+".idVettura", idTupla);          
		intent.putExtra(pkg+".cliente_fk", cliente_fk);          
		intent.putExtra(pkg+".visita_fk", visita_fk);          
		intent.putExtra(pkg+".telaio", telaio);          
		intent.putExtra(pkg+".esito_batteria", esito_batteria);          
		intent.putExtra(pkg+".esito_pneumatici", esito_pneumatici);          
		intent.putExtra(pkg+".esito_docu", esito_docu);          
	}

}
