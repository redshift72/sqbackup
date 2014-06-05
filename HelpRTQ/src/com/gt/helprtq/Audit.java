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

public class Audit extends ActionBarActivity implements OnClickListener, OnFocusChangeListener {
	/*
	 * per la gestione del cliente
	 */
	ObjectManager audit = new ObjectManager(this, LocalDB.Audit.TABLE_NAME, 0, R.id.linearlayout_audit);
	Class<NuovoAudit>  nuovaClasse = NuovoAudit.class; 
			
	int linearlayout = R.id.linearlayout_audit;
	String[] select = {
			LocalDB.Audit._ID,
			LocalDB.Audit.COLUMN_NAME_CLIENTE_FK,
			LocalDB.Audit.COLUMN_NAME_VISITA_FK,
			LocalDB.Audit.COLUMN_NAME_DATA_MAIL,
			LocalDB.Audit.COLUMN_NAME_ESITO,
	    };
	String sortOrder = LocalDB.Cliente._ID + " ASC";
	
    String pkg;
    
	// CAMPI DELLA TUPLA
	int idTupla = -1;
	int cliente_fk = -1;
	int visita_fk = -1;
	String data_mail = null;
	String esito = null;

	int visita_fkIn = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audit);

    	// imposto le variabili
		pkg=getPackageName();
		audit.setSelect(select);
		audit.setSortOrder(sortOrder);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.audit, menu);
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
		if (id == R.id.modifica_audit) {
			modifica();
			return true;
		}
		if (id == R.id.nuovo_audit) {
			nuovo();
			return true;
		}
		if (id == R.id.elimina_audit) {
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
			View rootView = inflater.inflate(R.layout.fragment_audit,
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

       
        Utility.log("onResume() -> idVettura " + idTupla);
        Utility.log("onResume() -> idCliente " + cliente_fk);
        Utility.log("onResume() -> idVisita " + visita_fk);
        
        disegnaSchermo();
        
        Utility.log("onResume END");
	}


	@Override
	public void onClick(View arg0) {
		modifica();      
	}


	@Override
	public void onFocusChange(View arg0, boolean arg1) {

		if (audit.getBooClearScreen()==false) {
	        TextView tv = (TextView) arg0;
			// TODO Auto-generated method stub
			if (arg1 == true) {
				audit.setId(tv.getId());
				audit.setPos((Integer) tv.getTag());
				
	            tv.setBackgroundColor(Color.GREEN);
			} else {
				audit.setId(-1);
	            tv.setBackgroundColor(Color.GRAY);
			}
			idTupla = audit.getId();
        }
			
	}

	private void modifica() {
		// TODO Auto-generated method stub
        Utility.log("modifica INI");
        Utility.log("---id " + audit.getId());

        if (audit.getId() != -1) {
	        Intent intent = new Intent(this, nuovaClasse);
	        Cursor c = audit.updateObject(nuovaClasse, intent);
			intent.putExtra(pkg+".idCliente", cliente_fk);          
			intent.putExtra(pkg+".idAudit", audit.getId());          
    		intent.putExtra(pkg+".idVisita", visita_fkIn);          
	
    		Utility.log("Vettura.modifica() -> visita_fkIn " + visita_fkIn);

    		for (int i = 0; c!=null && i < c.getCount(); i++) {
	    		audit.setId(getFields(c));
	    		
	            setIntentParameter(intent);
	    	}
	
	    	startActivity(intent);
	    	//pulisciSchermo();
        } else {
	    	CharSequence text = "Nessun audit selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
        	
        }
        Utility.log("modifica END");
	}


	private void nuovo() {
		// TODO Auto-generated method stub
        Utility.log("nuovo INI");

		Intent intent = new Intent(this, nuovaClasse);
		intent.putExtra(pkg+".idCliente", cliente_fk);          
        Utility.log("---idCliente " + cliente_fk);
    	startActivity(intent);
    	
    	//pulisciSchermo();
        Utility.log("nuovo END");
	}

	private void elimina() {
		// TODO Auto-generated method stub
        if (audit.getId() != -1) {
            audit.removeObject();
            disegnaSchermo();
        } else {
	    	CharSequence text = "Nessun audit selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
        }
	}

	private void disegnaSchermo() {
		// TODO Auto-generated method stub
		pulisciSchermo();
		
		String where = LocalDB.Audit.COLUMN_NAME_CLIENTE_FK + " = ? ";
		String[] whereArgs;
		if (visita_fk!=-1) {
			where += " AND (" + LocalDB.Audit.COLUMN_NAME_VISITA_FK + " = ? OR " + LocalDB.vettura.COLUMN_NAME_VISITA_FK + " is null)";
			String[] tmpwhereArgs = { String.valueOf(this.cliente_fk), String.valueOf(this.visita_fk) };
			whereArgs = tmpwhereArgs;
		} else {
			where += " AND " + LocalDB.Audit.COLUMN_NAME_VISITA_FK + " is null";
			String[] tmpwhereArgs = { String.valueOf(this.cliente_fk) };
			whereArgs = tmpwhereArgs;
		}
		if (cliente_fk == -1) {
			where = null; 
			whereArgs = null;
		}

		Cursor c = audit.drawScreen(where, whereArgs);
		for (int i = 0; i < audit.getIdMax(); i++) {
			//select
			idTupla = getFields(c);

		    // get a reference for the TableLayout
		    LinearLayout ll = (LinearLayout) this.findViewById(audit.getIdLayout());
		      
		    TextView tv;
		    Spanned text2Html;
		    
			// creo l'oggetto
    	    tv = new TextView(this);
    	    
    	    if (i==0) audit.setFirstTV(tv); // mi tengo in memoria il primo per poi dargli il FOCUS sempre
    	    
    	    tv.setId(audit.getBaseId()+idTupla);
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

		if (audit.getIdMax() > 0) audit.requestFocus2First();
	}

	private void pulisciSchermo() {
		// TODO Auto-generated method stub
		audit.setBooClearScreen(true); 
		audit.clearScreen();
		audit.setBooClearScreen(false); 
	}

	public int getFields(Cursor c) {
        Utility.log("getFields INI");
		// TODO Auto-generated method stub
		idTupla = c.getInt( c.getColumnIndexOrThrow(LocalDB.Audit._ID) );
		cliente_fk = c.getInt( c.getColumnIndexOrThrow(LocalDB.Audit.COLUMN_NAME_CLIENTE_FK) );
		visita_fk = c.getInt( c.getColumnIndexOrThrow(LocalDB.Audit.COLUMN_NAME_VISITA_FK) );
		data_mail = c.getString( c.getColumnIndexOrThrow(LocalDB.Audit.COLUMN_NAME_DATA_MAIL) );
		esito = c.getString( c.getColumnIndexOrThrow(LocalDB.Audit.COLUMN_NAME_ESITO) );

        if (data_mail == null) data_mail = ""; 
        if (esito == null) esito = ""; 
		
        Utility.log("getFields END");
		return idTupla;
	}

	public Spanned getLayoutRow() {
		// TODO Auto-generated method stub
        String htmlSource = "" + idTupla + " <b>" + Utility.convertDateField(data_mail) + "</b> " + " " + Utility.convertBooleanField("Esito", esito);
        
        Spanned text2Html =  Html.fromHtml(htmlSource);
        
        return text2Html;
	}

	public void setIntentParameter(Intent intent) {
		// TODO Auto-generated method stub
		intent.putExtra(pkg+".idAudit", idTupla);          
		intent.putExtra(pkg+".cliente_fk", cliente_fk);          
		intent.putExtra(pkg+".visita_fk", visita_fk);          
		intent.putExtra(pkg+".data_mail", data_mail);          
		intent.putExtra(pkg+".esito", esito);          
	}
}
