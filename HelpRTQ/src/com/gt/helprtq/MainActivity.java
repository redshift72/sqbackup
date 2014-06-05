package com.gt.helprtq;

import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener, OnFocusChangeListener {
	/*
	 * per la gestione del cliente
	 */
	ObjectManager cliente = new ObjectManager(this, LocalDB.Cliente.TABLE_NAME, 0, R.id.linearlayout);
	Class<NuovoCliente> nuovaClasse = NuovoCliente.class;
	
	String[] select = {
			LocalDB.Cliente._ID,
			LocalDB.Cliente.COLUMN_NAME_NOME,
			LocalDB.Cliente.COLUMN_NAME_INDIRIZZO,
			LocalDB.Cliente.COLUMN_NAME_TELEFONO,
			LocalDB.Cliente.COLUMN_NAME_CODICE_ORGANIZZATO,
			LocalDB.Cliente.COLUMN_NAME_MARCHE,
	    };
	String sortOrder = LocalDB.Cliente._ID + " ASC";
	
    String pkg;
    
	// CAMPI DELLA TUPLA
	int idTupla = -1;
	int cliente_fk = -1;
	int visita_fk = -1;
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

	String[] marcheDaInviare = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
    	// imposto le variabili
		Utility.setBooWrite(false);
		pkg=getPackageName();
		cliente.setSelect(select);
		cliente.setSortOrder(sortOrder);

		// verifico SE il DB è a posto
		QueryManager q = new QueryManager(getApplicationContext());
		q.verifySchema(getApplicationContext());
		
        if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle presses on the action bar items
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			openSettings();
			return true;
		}
		if (id == R.id.modifica) {
			modifica();
			return true;
		}
		if (id == R.id.nuovo) {
			nuovo();
			return true;
		}
		if (id == R.id.elimina) {
			elimina();
			return true;
		}
		if (id == R.id.audit) {
			startAudit();
			return true;
		}
		if (id == R.id.assessment) {
			startAssessment();
			return true;
		}
		if (id == R.id.pt) {
			startPT();
			return true;
		}
		if (id == R.id.auto) {
			startAuto();
			return true;
		}
		if (id == R.id.visita) {
			startVisita();
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	protected void onDestroy() {
		backupDB();
		
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
    	disegnaSchermo();
	}

	@Override
	public void onClick(View arg0) {
		modifica();      
	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {

        if (cliente.getBooClearScreen()==false) {
	        TextView tv = (TextView) arg0;
			// TODO Auto-generated method stub
			if (arg1 == true) {
				cliente.setId(tv.getId());
				cliente.setPos((Integer) tv.getTag());
				
	            tv.setBackgroundColor(Color.GREEN);
			} else {
				cliente.setId(-1);
	            tv.setBackgroundColor(Color.GRAY);
			}
			idTupla = cliente.getId();
			marche = marcheDaInviare[cliente.getPos()];
        }

	}

	private void startAudit() {
		// TODO Auto-generated method stub
		if (cliente.getId() != -1) {
	    	Intent intent = new Intent(this, Audit.class);
			intent.putExtra(pkg+".idCliente", cliente.getId());          
			intent.putExtra(pkg+".marcheCliente", marche);          
	        Utility.log("startAudit() -> idCliente " + cliente.getId());
	    	startActivity(intent);
		} else {
	    	CharSequence text = "Nessun cliente selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
		}
	}

	private void startAssessment() {
		// TODO Auto-generated method stub
		if (cliente.getId() != -1) {
	    	Intent intent = new Intent(this, Assessment.class);
			intent.putExtra(pkg+".idCliente", cliente.getId());          
			intent.putExtra(pkg+".marcheCliente", marche);          
	        Utility.log("startAssessment() -> idCliente " + cliente.getId());
	    	startActivity(intent);
		} else {
	    	CharSequence text = "Nessun cliente selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
		}
	}

	private void startPT() {
		// TODO Auto-generated method stub
		if (cliente.getId() != -1) {
	    	Intent intent = new Intent(this, PT.class);
			intent.putExtra(pkg+".idCliente", cliente.getId());          
			intent.putExtra(pkg+".marcheCliente", marche);          
	        Utility.log("startPT() -> idCliente " + cliente.getId());
	    	startActivity(intent);
		} else {
	    	CharSequence text = "Nessun cliente selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
		}
	}

	private void startAuto() {
		// TODO Auto-generated method stub
		if (cliente.getId() != -1) {
	    	Intent intent = new Intent(this, Vettura.class);
			intent.putExtra(pkg+".idCliente", cliente.getId());          
			intent.putExtra(pkg+".marcheCliente", marche);          
	        Utility.log("startAuto() -> idCliente " + cliente.getId());
	    	startActivity(intent);
		} else {
	    	CharSequence text = "Nessun cliente selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
		}
	}

	private void startVisita() {
		// TODO Auto-generated method stub
		if (cliente.getId() != -1) {
	    	Intent intent = new Intent(this, Visita.class);
			intent.putExtra(pkg+".idCliente", cliente.getId());          
			intent.putExtra(pkg+".marcheCliente", marche);          
	        Utility.log("startAuto() -> idCliente " + cliente.getId());
	    	startActivity(intent);
		} else {
	    	CharSequence text = "Nessun cliente selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
		}
	}
	
	private void modifica() {
		// TODO Auto-generated method stub
        Utility.print("modifica INI");

        if (cliente.getId() != -1) {
	        Utility.log("---id " + cliente.getId());

	        Intent intent = new Intent(this, nuovaClasse);
	        Cursor c = cliente.updateObject(nuovaClasse, intent);

	    	for (int i = 0; c!=null && i < c.getCount(); i++) {
	    		cliente.setId(getFields(c));
	    		
	            setIntentParameter(intent);
	    	}

	    	startActivity(intent);
	    	//pulisciSchermo();
		} else {
	    	CharSequence text = "Nessun cliente selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
		}
        
        Utility.log("modifica END");
	}

	private void nuovo() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, nuovaClasse);
    	startActivity(intent);
    	
    	//pulisciSchermo();
	}

	private void elimina() {
		// TODO Auto-generated method stub
		if (cliente.getId() != -1) {
			String[] tableList = {LocalDB.Assessment.TABLE_NAME, LocalDB.visita.TABLE_NAME,  
                    			  LocalDB.Audit.TABLE_NAME, LocalDB.pt.TABLE_NAME, LocalDB.vettura.TABLE_NAME};

			String[][] select = {{LocalDB.Assessment._ID,},
								 {LocalDB.visita._ID,}, 
								 {LocalDB.Audit._ID,}, 
								 {LocalDB.pt._ID,}, 
								 {LocalDB.vettura._ID,}, 
								 };

			String[] where = {LocalDB.Assessment.COLUMN_NAME_CLIENTE_FK + " = ?",
							  LocalDB.visita.COLUMN_NAME_CLIENTE_FK + " = ?",
							  LocalDB.Audit.COLUMN_NAME_CLIENTE_FK + " = ?",
							  LocalDB.pt.COLUMN_NAME_CLIENTE_FK + " = ?",
							  LocalDB.vettura.COLUMN_NAME_CLIENTE_FK + " = ?",
							 };

			String[][] whereArgs = { {String.valueOf(cliente.getId()),},
									 {String.valueOf(cliente.getId()),},
									 {String.valueOf(cliente.getId()),},
									 {String.valueOf(cliente.getId()),},
									 {String.valueOf(cliente.getId()),},
								   };

			if (cliente.IsRemovable(tableList, select, where, whereArgs) == true) {
		        cliente.removeObject();
		        disegnaSchermo();
			} else {
		    	CharSequence text = "Non posso eliminare il cliente perchè ha altri oggetti collegati";
	        	int duration = Toast.LENGTH_SHORT;
	        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	        	toast.setGravity(Gravity.TOP, 0, 180);
	        	toast.show();        	
			}
		} else {
	    	CharSequence text = "Nessun cliente selezionato";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.setGravity(Gravity.TOP, 0, 180);
        	toast.show();        	
		}
	}

	private void disegnaSchermo() {
		// TODO Auto-generated method stub
		pulisciSchermo();
		
		Cursor c = cliente.drawScreen(null, null);
		marcheDaInviare = new String[cliente.getIdMax()];
		for (int i = 0; i < cliente.getIdMax(); i++) {
			//select
			idTupla = getFields(c);
    	    marcheDaInviare[i] = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_MARCHE) );

		    // get a reference for the TableLayout
		    LinearLayout ll = (LinearLayout) this.findViewById(cliente.getIdLayout());
		      
		    TextView tv;
		    Spanned text2Html;
		    
			// creo l'oggetto
    	    tv = new TextView(this);
    	    
    	    if (i==0) cliente.setFirstTV(tv); // mi tengo in memoria il primo per poi dargli il FOCUS sempre
    	    
    	    tv.setId(cliente.getBaseId()+idTupla);
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

		if (cliente.getIdMax() > 0) cliente.requestFocus2First();
	}

	private void pulisciSchermo() {
		// TODO Auto-generated method stub
		cliente.setBooClearScreen(true); 
		cliente.clearScreen();
		cliente.setBooClearScreen(false); 
	}

	private void backupDB() {
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        // vedo con quale tipo di rete sto navigando
//        NetworkInfo networkInfoTest = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
//        boolean isWifiConn = networkInfoTest.isConnected();
//
//        if (isWifiConn) {
        	BackupManager bm = new BackupManager(this);
        	bm.dataChanged();
//        } else {
//	    	CharSequence text = "Meglio se lo fai con la WIFI ;-)";
//        	int duration = Toast.LENGTH_SHORT;
//        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
//        	toast.setGravity(Gravity.TOP, 0, 180);
//        	toast.show();        	
//        }
	}

	private void restoreDB() {
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        // vedo con quale tipo di rete sto navigando
//        NetworkInfo networkInfoTest = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
//        boolean isWifiConn = networkInfoTest.isConnected();
//
//        if (isWifiConn) {
        	BackupManager bm = new BackupManager(this);
        	RestoreObserver ro = new RestoreObserver() {
			};
	    	bm.requestRestore(ro);
	    	disegnaSchermo();
//        } else {
//	    	CharSequence text = "Meglio se lo fai con la WIFI ;-)";
//        	int duration = Toast.LENGTH_SHORT;
//        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
//        	toast.setGravity(Gravity.TOP, 0, 180);
//        	toast.show();        	
//        }
	}

	private void openSettings() {
		// TODO Auto-generated method stub
		restoreDB();
	}

	public int getFields(Cursor c) {
        Utility.log("getFields INI");
		// TODO Auto-generated method stub
		idTupla = c.getInt( c.getColumnIndexOrThrow(LocalDB.Cliente._ID) );
		nome = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_NOME) );
		indirizzo = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_INDIRIZZO) );
		codice = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_CODICE_ORGANIZZATO) );
		marche = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_MARCHE) );
		telefono = c.getString( c.getColumnIndexOrThrow(LocalDB.Cliente.COLUMN_NAME_TELEFONO) );

        if (nome == null) nome = ""; 
        if (telefono == null) telefono = ""; 
        if (indirizzo == null) indirizzo = ""; 
        if (codice == null) codice = ""; 
        //1:audi - 2:VW - 3:Skoda - 4:SEAT - 5:VIC
        if (marche == null || marche.length() != 19) marche = "1,0;2,0;3,0;4,0;5,0"; 

        Utility.log("--- id " + cliente.getId());
        Utility.log("--- nomeCliente " + nome);
        Utility.log("--- indirizzoCliente " + indirizzo);
        Utility.log("--- telefonoCliente " + telefono);
        Utility.log("--- codiceCliente " + codice);
        Utility.log("--- marcheCliente " + marche + " - " + marche.length());
		
		listaMarche = marche.split(";");
		audi = listaMarche[0].split(",");
		vw = listaMarche[1].split(",");
		skoda = listaMarche[2].split(",");
		seat = listaMarche[3].split(",");
		vic = listaMarche[4].split(",");
        Utility.log("--- audi " + audi[1]);
        Utility.log("--- vw " + vw[1]);
        Utility.log("--- skoda " + skoda[1]);
        Utility.log("--- seat " + seat[1]);
        Utility.log("--- vic " + vic[1]);
		
        Utility.log("getFields END");
		return idTupla;
	}

	public Spanned getLayoutRow() {
		// TODO Auto-generated method stub
        String htmlSource = "" + codice + " <b>" + nome + "</b> " + " " + telefono + 
				       		"<br>" + indirizzo + " - (<tt><small>";
        
        String[] m = new String[5];
        
        m[0] = Utility.convertBooleanField("AU", audi[1]);
        m[1] = Utility.convertBooleanField("VW", vw[1]);
        m[2] = Utility.convertBooleanField("SK", skoda[1]);
        m[3] = Utility.convertBooleanField("SE", seat[1]);
        m[4] = Utility.convertBooleanField("VC", vic[1]);
        
        String token = m[0];
        String tokenNext;
        htmlSource += token;
        for (int i=1; i < 5; i++) {
        	tokenNext = m[i];
        	if (token.equals("") == false && tokenNext.equals("") == false) {
        		htmlSource += ".";
        	}
        	if (tokenNext.equals("") == false) { 
        		token = tokenNext;
        	}
        	htmlSource += tokenNext;
        	 
        }
        
        htmlSource += "</tt></small>)";
        
        Spanned text2Html =  Html.fromHtml(htmlSource);
        
        return text2Html;
	}

	public void setIntentParameter(Intent intent) {
		// TODO Auto-generated method stub
		intent.putExtra(pkg+".idCliente", idTupla);          
		intent.putExtra(pkg+".nomeCliente", nome);          
		intent.putExtra(pkg+".indirizzoCliente", indirizzo);          
		intent.putExtra(pkg+".telefonoCliente", telefono);          
		intent.putExtra(pkg+".codiceCliente", codice);          
		intent.putExtra(pkg+".marcheCliente", marche);          
	}
}
