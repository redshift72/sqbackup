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

public class Visita extends ActionBarActivity implements OnClickListener, OnFocusChangeListener {
	/*
	 * per la gestione del cliente
	 */
	int linearlayout = R.id.linearlayout_visita;
	ObjectManager object = new ObjectManager(this, LocalDB.visita.TABLE_NAME, 0, linearlayout);
	Class<NuovaVisita> nuovaClasse = NuovaVisita.class;
	
	String[] select = {
			LocalDB.visita._ID,
			LocalDB.visita.COLUMN_NAME_CLIENTE_FK,
			LocalDB.visita.COLUMN_NAME_DATA,
			LocalDB.visita.COLUMN_NAME_Q_CHECK_RTQ,
			LocalDB.visita.COLUMN_NAME_Q_CHECK,
			LocalDB.visita.COLUMN_NAME_DISS,
			LocalDB.visita.COLUMN_NAME_DISS_2,
			LocalDB.visita.COLUMN_NAME_DISS_8,
			LocalDB.visita.COLUMN_NAME_DISS_INFO,
			LocalDB.visita.COLUMN_NAME_DISS_RICH_TECNICHE,
			LocalDB.visita.COLUMN_NAME_ATTREZZATURA,
			LocalDB.visita.COLUMN_NAME_RUOLI_DBO,
			LocalDB.visita.COLUMN_NAME_CC_AZIONI_RICHIAMO,
			LocalDB.visita.COLUMN_NAME_RR_STAMPA_CORRETTIVI,
			LocalDB.visita.COLUMN_NAME_CSS,
			LocalDB.visita.COLUMN_NAME_ODL,
			LocalDB.visita.COLUMN_NAME_FORMAZIONE,
			LocalDB.visita.COLUMN_NAME_RELAZIONE,
			LocalDB.visita.COLUMN_NAME_NOTE,
	    };
	String sortOrder = LocalDB.Cliente._ID + " ASC";
	
    String pkg;

	// CAMPI DELLA TUPLA
	int idTupla = -1;
	int cliente_fk = -1;
	String data = null;
	String qCheckRtq = null;
	String qCheck = null;
	String diss = null;
	String diss2 = null;
	String diss8 = null;
	String dissInfo = null;
	String dissRichTecniche = null;
	String attrezzatura = null;
	String ruoliDBO = null;
	String ccAzioniRichiamo = null;
	String rrStampaCorrispettivi = null;
	String css = null;
	String odl = null;
	String formazione = null;
	String relazione = null;
	String note = null;

	// MARCHE DEL CLIENTE
	String marcaClienteIn = null;
	String[] listaMarche = null;
	String[] audi = null;
	String[] vw = null;
	String[] skoda = null;
	String[] seat = null;
	String[] vic = null;
	
	// Q check rtq
	String qCheckRtqIn = null;
	String[] listaCheckRtq = null;
	String[] audiCheckRtq = null;
	String[] vwCheckRtq = null;
	String[] skodaCheckRtq = null;
	String[] seatCheckRtq = null;
	String[] vicCheckRtq = null;
	
	// Q check
	String qCheckIn = null;
	String[] listaCheck = null;
	String[] audiCheck = null;
	String[] vwCheck = null;
	String[] skodaCheck = null;
	String[] seatCheck = null;
	String[] vicCheck = null;
	
	// Q check
	String cssIn = null;
	String[] listaCss = null;
	String[] audiCss = null;
	String[] vwCss = null;
	String[] skodaCss = null;
	String[] seatCss = null;
	String[] vicCss = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visita);

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
		getMenuInflater().inflate(R.menu.visita, menu);
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
		if (id == R.id.visita_modifica) {
			modifica();
			return true;
		}
		if (id == R.id.visita_nuovo) {
			nuovo();
			return true;
		}
		if (id == R.id.visita_elimina) {
			elimina();
			return true;
		}
		if (id == R.id.visita_audit) {
			gestisciAudit();
			return true;
		}
		if (id == R.id.visita_assessment) {
			gestisciAssessment();
			return true;
		}
		if (id == R.id.visita_pt) {
			gestisciPT();
			return true;
		}
		if (id == R.id.visita_vettura) {
			gestisciVetture();
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
			View rootView = inflater.inflate(R.layout.fragment_visita,
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
        this.marcaClienteIn = intent.getStringExtra(pkg+".marcheCliente");
        Utility.log("--- idCliente " + this.cliente_fk);
        
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
    		intent.putExtra(pkg+".idPT", object.getId());          
    		intent.putExtra(pkg+".marcheCliente", marcaClienteIn);          
    		
            for (int i = 0; c!=null && i < c.getCount(); i++) {
        		object.setId(getFields(c));
        		
                setIntentParameter(intent);
        	}

        	startActivity(intent);
        	//pulisciSchermo();
        } else {
	    	CharSequence text = "Nessun PT selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
        	
            Utility.log("--- Nessun PT selezionato");
        }

        Utility.log("modifica END");
	}


	private void nuovo() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, nuovaClasse);
		intent.putExtra(pkg+".idCliente", cliente_fk);          
		intent.putExtra(pkg+".marcheCliente", marcaClienteIn);          
    	startActivity(intent);
    	
    	//pulisciSchermo();
	}

	private void elimina() {
		// TODO Auto-generated method stub
        if (object.getId() != -1) {

			String[] tableList = {LocalDB.Assessment.TABLE_NAME, LocalDB.Audit.TABLE_NAME, 
					              LocalDB.pt.TABLE_NAME, LocalDB.vettura.TABLE_NAME};

			String[][] select = {{LocalDB.Assessment._ID,},
								 {LocalDB.Audit._ID,}, 
								 {LocalDB.pt._ID,}, 
								 {LocalDB.vettura._ID,}, 
								 };
			
			String[] where = {LocalDB.Assessment.COLUMN_NAME_VISITA_FK + " = ?",
							  LocalDB.Audit.COLUMN_NAME_CLIENTE_FK + " = ?",
							  LocalDB.pt.COLUMN_NAME_CLIENTE_FK + " = ?",
							  LocalDB.vettura.COLUMN_NAME_CLIENTE_FK + " = ?",
							 };
			
			String[][] whereArgs = { {String.valueOf(object.getId()),},
									 {String.valueOf(object.getId()),},
									 {String.valueOf(object.getId()),},
									 {String.valueOf(object.getId()),},
								   };
			
			if (object.IsRemovable(tableList, select, where, whereArgs) == true) {
				object.removeObject();
			  disegnaSchermo();
			} else {
				CharSequence text = "Non posso eliminare la visita perchè ha altri oggetti collegati";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getApplicationContext(), text, duration);
				toast.setGravity(Gravity.TOP, 0, 180);
				toast.show();        	
			}
        } else {
	    	CharSequence text = "Nessuna visita selezionata";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
        }
	}

	private void gestisciAudit() {
    	Intent intent = new Intent(this, Audit.class);
		intent.putExtra(pkg+".idCliente", cliente_fk);          
		intent.putExtra(pkg+".marcheCliente", marcaClienteIn);          
		intent.putExtra(pkg+".idVisita", object.getId());          

    	startActivity(intent);
	}

	private void gestisciAssessment() {
    	Intent intent = new Intent(this, Assessment.class);
		intent.putExtra(pkg+".idCliente", cliente_fk);          
		intent.putExtra(pkg+".marcheCliente", marcaClienteIn);          
		intent.putExtra(pkg+".idVisita", object.getId());          

    	startActivity(intent);
	}

	private void gestisciPT() {
    	Intent intent = new Intent(this, PT.class);
		intent.putExtra(pkg+".idCliente", cliente_fk);          
		intent.putExtra(pkg+".marcheCliente", marcaClienteIn);          
		intent.putExtra(pkg+".idVisita", object.getId());          

    	startActivity(intent);
	}
	
	private void gestisciVetture() {
    	Intent intent = new Intent(this, Vettura.class);
		intent.putExtra(pkg+".idCliente", cliente_fk);          
		intent.putExtra(pkg+".marcheCliente", marcaClienteIn);          
		intent.putExtra(pkg+".idVisita", object.getId());          

    	startActivity(intent);
	}
	
	private void disegnaSchermo() {
		// TODO Auto-generated method stub
		pulisciSchermo();
		
		String where = LocalDB.visita.COLUMN_NAME_CLIENTE_FK + " = ? ";
		String[] whereArgs = { String.valueOf(this.cliente_fk) };
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
		idTupla = c.getInt( c.getColumnIndexOrThrow(LocalDB.visita._ID) );
		cliente_fk = c.getInt( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_CLIENTE_FK) );
		data = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_DATA) );
		qCheckRtq = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_Q_CHECK_RTQ) );
		qCheck = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_Q_CHECK) );
		diss = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_DISS) );
		diss2 = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_DISS_2) );
		diss8 = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_DISS_8) );
		dissInfo = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_DISS_INFO) );
		dissRichTecniche = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_DISS_RICH_TECNICHE) );
		attrezzatura = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_ATTREZZATURA) );
		ruoliDBO = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_RUOLI_DBO) );
		ccAzioniRichiamo = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_CC_AZIONI_RICHIAMO) );
		rrStampaCorrispettivi = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_RR_STAMPA_CORRETTIVI) );
		css = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_CSS) );
		odl = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_ODL) );
		formazione = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_FORMAZIONE) );
		relazione =  c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_RELAZIONE) );
		note = c.getString( c.getColumnIndexOrThrow(LocalDB.visita.COLUMN_NAME_NOTE) );

        String badValue[] = {null};
		
        Utility.checkDateField(data, badValue, getApplicationContext());

        qCheckRtq = Utility.checkTextField(qCheckRtq, badValue, "1,0;2,0;3,0;4,0;5,0", 19);
        qCheck = Utility.checkTextField(qCheck, badValue, "1,0;2,0;3,0;4,0;5,0", 19);
        diss = Utility.checkTextField(diss, badValue, "", -1);
        diss2 = Utility.checkTextField(diss2, badValue, "0", 1);
        diss8 = Utility.checkTextField(diss8, badValue, "0", 1);
        dissInfo = Utility.checkTextField(dissInfo, badValue, "", -1);
        dissRichTecniche = Utility.checkTextField(dissRichTecniche, badValue, "", -1);
        attrezzatura = Utility.checkTextField(attrezzatura, badValue, "", -1);
        ruoliDBO = Utility.checkTextField(ruoliDBO, badValue, "0", 1);
        ccAzioniRichiamo = Utility.checkTextField(ccAzioniRichiamo, badValue, "0", 1);
        rrStampaCorrispettivi = Utility.checkTextField(rrStampaCorrispettivi, badValue, "0", 1);
        css = Utility.checkTextField(css, badValue, "1,0;2,0;3,0;4,0;5,0", -1);
        odl = Utility.checkTextField(odl, badValue, "", -1);
        formazione = Utility.checkTextField(formazione, badValue, "", -1);
        relazione = Utility.checkTextField(relazione, badValue, "0", 1);
        note = Utility.checkTextField(note, badValue, "", -1);

        Utility.log("--- id " + object.getId());
		Utility.log("--- cliente_fk " + cliente_fk);
		Utility.log("--- data " + data);
		Utility.log("--- qCheckRtq " + qCheckRtq);
		Utility.log("--- qCheck " + qCheck);
		Utility.log("--- diss " + diss);
		Utility.log("--- diss8 " + diss8);
		Utility.log("--- dissInfo " + dissInfo);
		Utility.log("--- dissRichTecniche " + dissRichTecniche);
		Utility.log("--- attrezzatura " + attrezzatura);
		Utility.log("--- ruoliDBO " + ruoliDBO);
		Utility.log("--- ccAzioniRichiamo " + ccAzioniRichiamo);
		Utility.log("--- rrStampaCorrispettivi " + rrStampaCorrispettivi);
		Utility.log("--- css " + css);
		Utility.log("--- odl " + odl);
		Utility.log("--- formazione " + formazione);
		Utility.log("--- relazione " + relazione);
		Utility.log("--- note " + note);
		
        Utility.log("getFields END");
		return idTupla;
	}

	public Spanned getLayoutRow() {
		// TODO Auto-generated method stub
        String htmlSource = "" + idTupla + " <b>" + Utility.convertDateField(data) + "</b> " + 
        					"<br>" + note;
        
        Spanned text2Html =  Html.fromHtml(htmlSource);
        
        return text2Html;
	}

	public void setIntentParameter(Intent intent) {
		// TODO Auto-generated method stub
		intent.putExtra(pkg+".idVisita", idTupla);          
		intent.putExtra(pkg+".cliente_fk", cliente_fk);          
		intent.putExtra(pkg+".visita_fk", idTupla);          
		intent.putExtra(pkg+".data", data);          
		intent.putExtra(pkg+".qCheckRtq", qCheckRtq);          
		intent.putExtra(pkg+".qCheck", qCheck);          
		intent.putExtra(pkg+".diss", diss);          
		intent.putExtra(pkg+".diss2", diss2);          
		intent.putExtra(pkg+".diss8", diss8);          
		intent.putExtra(pkg+".dissInfo", dissInfo);          
		intent.putExtra(pkg+".dissRichTecniche", dissRichTecniche);          
		intent.putExtra(pkg+".attrezzatura", attrezzatura);          
		intent.putExtra(pkg+".ruoliDBO", ruoliDBO);          
		intent.putExtra(pkg+".ccAzioniRichiamo", ccAzioniRichiamo);          
		intent.putExtra(pkg+".rrStampaCorrispettivi", rrStampaCorrispettivi);          
		intent.putExtra(pkg+".css", css);          
		intent.putExtra(pkg+".odl", odl);          
		intent.putExtra(pkg+".formazione", formazione);
		intent.putExtra(pkg+".relazione", relazione);
		intent.putExtra(pkg+".note", note);          
	}
}
