package com.gt.helprtq;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;

public class NuovaVisita extends ActionBarActivity implements OnItemSelectedListener{

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
		setContentView(R.layout.activity_nuova_visita);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuova_visita, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_nuova_visita,
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

        idTupla = intent.getIntExtra(pkg+".idVisita", -1);
        this.idTupla = idTupla;

        cliente_fk = intent.getIntExtra(pkg+".idCliente", -1);
        marcaClienteIn = intent.getStringExtra(pkg+".marcheCliente");

        if (idTupla != -1) {
	    	data = intent.getStringExtra(pkg+".data");
	    	qCheckRtq = intent.getStringExtra(pkg+".qCheckRtq");
	    	qCheck = intent.getStringExtra(pkg+".qCheck");
	    	diss = intent.getStringExtra(pkg+".diss");
	    	diss2 = intent.getStringExtra(pkg+".diss2");
	    	diss8 = intent.getStringExtra(pkg+".diss8");
	    	dissInfo = intent.getStringExtra(pkg+".dissInfo");
	    	dissRichTecniche = intent.getStringExtra(pkg+".dissRichTecniche");
	    	attrezzatura = intent.getStringExtra(pkg+".attrezzatura");
	    	ruoliDBO = intent.getStringExtra(pkg+".ruoliDBO");
	    	ccAzioniRichiamo = intent.getStringExtra(pkg+".ccAzioniRichiamo");
	    	rrStampaCorrispettivi = intent.getStringExtra(pkg+".rrStampaCorrispettivi");
	    	css = intent.getStringExtra(pkg+".css");
	    	odl = intent.getStringExtra(pkg+".odl");
	    	formazione = intent.getStringExtra(pkg+".formazione");
	    	relazione =  intent.getStringExtra(pkg+".relazione");
	    	note = intent.getStringExtra(pkg+".note");
        }

        
        if (data == null) data = ""; 
        if (qCheckRtq == null || qCheckRtq.length() != 19) qCheckRtq = "1,0;2,0;3,0;4,0;5,0"; 
        if (qCheck == null || qCheck.length() != 19) qCheck = "1,0;2,0;3,0;4,0;5,0"; 
        if (diss == null) diss = ""; 
        if (diss2 == null) diss2 = ""; 
        if (diss8 == null) diss8 = ""; 
        if (dissInfo == null) dissInfo = ""; 
        if (dissRichTecniche == null) dissRichTecniche = ""; 
        if (attrezzatura == null) attrezzatura = ""; 
        if (ruoliDBO == null) ruoliDBO = ""; 
        if (ccAzioniRichiamo == null) ccAzioniRichiamo = ""; 
        if (rrStampaCorrispettivi == null) rrStampaCorrispettivi = ""; 
        if (css == null) css = "1,0;2,0;3,0;4,0;5,0"; 
        if (odl == null) odl = ""; 
        if (formazione == null) formazione = ""; 
        if (relazione == null) relazione = "0"; 
        if (note == null) note = ""; 
        
      	// MARCHE
        listaMarche = marcaClienteIn.split(";");
    	audi = listaMarche[0].split(",");
    	vw = listaMarche[1].split(",");
    	skoda = listaMarche[2].split(",");
    	seat = listaMarche[3].split(",");
    	vic = listaMarche[4].split(",");
    	
    	// Q check rtq
    	qCheckRtqIn = qCheckRtq;
    	listaCheckRtq = qCheckRtqIn.split(";");
    	audiCheckRtq = listaCheckRtq[0].split(",");
    	vwCheckRtq = listaCheckRtq[1].split(",");
    	skodaCheckRtq = listaCheckRtq[2].split(",");
    	seatCheckRtq = listaCheckRtq[3].split(",");
    	vicCheckRtq = listaCheckRtq[4].split(",");
    	
    	// Q check
    	qCheckIn = qCheck;
    	listaCheck = qCheckIn.split(";");
    	audiCheck = listaCheck[0].split(",");
    	vwCheck = listaCheck[1].split(",");
    	skodaCheck = listaCheck[2].split(",");
    	seatCheck = listaCheck[3].split(",");
    	vicCheck = listaCheck[4].split(",");
    	
    	// Q check
    	cssIn = css;
    	listaCss = cssIn.split(";");
    	audiCss = listaCss[0].split(",");
    	vwCss = listaCss[1].split(",");
    	skodaCss = listaCss[2].split(",");
    	seatCss = listaCss[3].split(",");
    	vicCss = listaCss[4].split(",");

        EditText vData =(EditText) findViewById(R.id.visita_data);
        EditText vDiss =(EditText) findViewById(R.id.visita_diss);
        EditText vDissInfo =(EditText) findViewById(R.id.visita_dissInfo);
        EditText vDissReqTec =(EditText) findViewById(R.id.visita_dissRichiestaTecnica);
        EditText vAttrezzatura =(EditText) findViewById(R.id.visita_attrezzatura);
        EditText vOdl =(EditText) findViewById(R.id.visita_odlFatture);
        EditText vFormazione =(EditText) findViewById(R.id.visita_formazione);
        EditText vNote =(EditText) findViewById(R.id.visita_note);

        ToggleButton vQCRAu =(ToggleButton) findViewById(R.id.visita_qcheckRtqAudi);
        ToggleButton vQCRVw =(ToggleButton) findViewById(R.id.visita_qcheckRtqVW);
        ToggleButton vQCRSk =(ToggleButton) findViewById(R.id.visita_qcheckRtqSkoda);
        ToggleButton vQCRSe =(ToggleButton) findViewById(R.id.visita_qcheckRtqSeat);
        ToggleButton vQCRVc =(ToggleButton) findViewById(R.id.visita_qcheckRtqVic);
        ToggleButton vQCAu =(ToggleButton) findViewById(R.id.visita_qcheckAudi);
        ToggleButton vQCVw =(ToggleButton) findViewById(R.id.visita_qcheckVW);
        ToggleButton vQCSk =(ToggleButton) findViewById(R.id.visita_qcheckSkoda);
        ToggleButton vQCSe =(ToggleButton) findViewById(R.id.visita_qcheckSeat);
        ToggleButton vQCVc =(ToggleButton) findViewById(R.id.visita_qcheckVic);
        ToggleButton vDiss2 =(ToggleButton) findViewById(R.id.visita_diss2Settimane);
        ToggleButton vDiss8 =(ToggleButton) findViewById(R.id.visita_diss8Settimane);
        ToggleButton vRuoli =(ToggleButton) findViewById(R.id.visita_ruoliInBdo);
        ToggleButton vAzRich =(ToggleButton) findViewById(R.id.visita_ccAzioniDiRichiamo);
        ToggleButton vStCorr =(ToggleButton) findViewById(R.id.visita_rrStampaCorrettivo);
        ToggleButton vRelazione =(ToggleButton) findViewById(R.id.visita_relazione);
//        ToggleButton vcssAu =(ToggleButton) findViewById(R.id.visita_cssAudi);
//        ToggleButton vcssVw =(ToggleButton) findViewById(R.id.visita_cssVW);
//        ToggleButton vcssSk =(ToggleButton) findViewById(R.id.visita_cssSkoda);
//        ToggleButton vcssSe =(ToggleButton) findViewById(R.id.visita_cssSeat);
//        ToggleButton vcssVc =(ToggleButton) findViewById(R.id.visita_cssVic);

        vData.setText(data);
        vDiss.setText(diss);
        vDissInfo.setText(dissInfo);
        vDissReqTec.setText(dissRichTecniche);
        vAttrezzatura.setText(attrezzatura);
        vOdl.setText(odl);
        vFormazione.setText(formazione);
        vNote.setText(note);
        
        vQCRAu.setChecked(false);
        if (audiCheckRtq[1].equals("1")) vQCRAu.setChecked(true);
        vQCRVw.setChecked(false);
        if (vwCheckRtq[1].equals("1")) vQCRVw.setChecked(true);
        vQCRSk.setChecked(false);
        if (skodaCheckRtq[1].equals("1")) vQCRSk.setChecked(true);
        vQCRSe.setChecked(false);
        if (seatCheckRtq[1].equals("1")) vQCRSe.setChecked(true);
        vQCRVc.setChecked(false);
        if (vicCheckRtq[1].equals("1")) vQCRVc.setChecked(true);

        vQCAu.setChecked(false);
        if (audiCheck[1].equals("1")) vQCAu.setChecked(true);
        vQCVw.setChecked(false);
        if (vwCheck[1].equals("1")) vQCVw.setChecked(true);
        vQCSk.setChecked(false);
        if (skodaCheck[1].equals("1")) vQCSk.setChecked(true);
        vQCSe.setChecked(false);
        if (seatCheck[1].equals("1")) vQCSe.setChecked(true);
        vQCVc.setChecked(false);
        if (vicCheck[1].equals("1")) vQCVc.setChecked(true);

        vDiss2.setChecked(false);
        if (diss2.equals("1")) vDiss2.setChecked(true);
        vDiss8.setChecked(false);
        if (diss8.equals("1")) vDiss8.setChecked(true);
        
        vRuoli.setChecked(false);
        if (ruoliDBO.equals("1")) vRuoli.setChecked(true);
        vAzRich.setChecked(false);
        if (ccAzioniRichiamo.equals("1")) vAzRich.setChecked(true);
        vStCorr.setChecked(false);
        if (rrStampaCorrispettivi.equals("1")) vStCorr.setChecked(true);
        vRelazione.setChecked(false);
        if (relazione.equals("1")) vRelazione.setChecked(true);

        
        Spinner spinnerAu =(Spinner) findViewById(R.id.visita_cssAudi);
        Spinner spinnerVw =(Spinner) findViewById(R.id.visita_cssVW);
        Spinner spinnerSk =(Spinner) findViewById(R.id.visita_cssSkoda);
        Spinner spinnerSe =(Spinner) findViewById(R.id.visita_cssSeat);
        Spinner spinnerVc =(Spinner) findViewById(R.id.visita_cssVic);

        ArrayList<String> spinnerArrayAu = new ArrayList<String>();
        spinnerArrayAu.add("seleziona per Audi");
        ArrayList<String> spinnerArrayVw = new ArrayList<String>();
        spinnerArrayVw.add("seleziona per VW");
        ArrayList<String> spinnerArraySk = new ArrayList<String>();
        spinnerArraySk.add("seleziona per Skoda");
        ArrayList<String> spinnerArraySe = new ArrayList<String>();
        spinnerArraySe.add("seleziona per Seat");
        ArrayList<String> spinnerArrayVc = new ArrayList<String>();
        spinnerArrayVc.add("seleziona per VIC");
        
        //String tokenOk = "*";
        String tempToken = null;
        int tot=0;
        int pos=0;
        
        //top, sotto media, media Italia        
        tempToken = "top";
    	spinnerArrayAu.add(tempToken); 
    	spinnerArrayVw.add(tempToken); 
    	spinnerArraySk.add(tempToken); 
    	spinnerArraySe.add(tempToken); 
    	spinnerArrayVc.add(tempToken); tot++;
    	if (audiCss[1].equals("top")) pos = tot;
    	
        //top, sotto media, media Italia        
        tempToken = "media Italia";
    	spinnerArrayAu.add(tempToken); 
    	spinnerArrayVw.add(tempToken); 
    	spinnerArraySk.add(tempToken); 
    	spinnerArraySe.add(tempToken); 
    	spinnerArrayVc.add(tempToken); tot++;
    	if (audiCss[1].equals("media Italia")) pos = tot;
    	
        //top, sotto media, media Italia        
        tempToken = "sotto media";
    	spinnerArrayAu.add(tempToken); 
    	spinnerArrayVw.add(tempToken); 
    	spinnerArraySk.add(tempToken); 
    	spinnerArraySe.add(tempToken); 
    	spinnerArrayVc.add(tempToken); tot++;
    	if (vwCss[1].equals("sotto media")) pos = tot;
    	
    	
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> spinnerArrayAdapterAu = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayAu);
        ArrayAdapter<String> spinnerArrayAdapterVw = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayVw);
        ArrayAdapter<String> spinnerArrayAdapterSk = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArraySk);
        ArrayAdapter<String> spinnerArrayAdapterSe = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArraySe);
        ArrayAdapter<String> spinnerArrayAdapterVc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayVc);
        
        spinnerArrayAdapterAu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapterVw.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapterSk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapterSe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapterVc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerAu.setAdapter(spinnerArrayAdapterAu);
        // Specifica chi gestisce lo spinner
        spinnerAu.setOnItemSelectedListener(this);
        spinnerAu.setSelection(pos);
        
        // Apply the adapter to the spinner
        spinnerVw.setAdapter(spinnerArrayAdapterVw);
        // Specifica chi gestisce lo spinner
        spinnerVw.setOnItemSelectedListener(this);
        spinnerVw.setSelection(pos);
        
        // Apply the adapter to the spinner
        spinnerSk.setAdapter(spinnerArrayAdapterSk);
        // Specifica chi gestisce lo spinner
        spinnerSk.setOnItemSelectedListener(this);
        spinnerSk.setSelection(pos);
        
        // Apply the adapter to the spinner
        spinnerSe.setAdapter(spinnerArrayAdapterSe);
        // Specifica chi gestisce lo spinner
        spinnerSe.setOnItemSelectedListener(this);
        spinnerSe.setSelection(pos);
        
        // Apply the adapter to the spinner
        spinnerVc.setAdapter(spinnerArrayAdapterVc);
        // Specifica chi gestisce lo spinner
        spinnerVc.setOnItemSelectedListener(this);
        spinnerVc.setSelection(pos);

        if (audi[1].equals("0")) {
			vQCRAu.setEnabled(false);
	        vQCAu.setEnabled(false);
	        spinnerAu.setEnabled(false);
        }			
        if (vw[1].equals("0")) {
        	vQCRVw.setEnabled(false);
            vQCVw.setEnabled(false);
	        spinnerVw.setEnabled(false);
        }
        if (skoda[1].equals("0")) {
        	vQCRSk.setEnabled(false);
            vQCSk.setEnabled(false);
	        spinnerSk.setEnabled(false);
        }
        if (seat[1].equals("0")) {
        	vQCRSe.setEnabled(false);
            vQCSe.setEnabled(false);
	        spinnerSe.setEnabled(false);
        }
        if (vic[1].equals("0")) {
            vQCRVc.setEnabled(false);
            vQCVc.setEnabled(false);
	        spinnerVc.setEnabled(false);
        }
	}

	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
		ToggleButton tb = (ToggleButton) view;
	    boolean on = tb.isChecked();
	    
        if (tb.getId() == R.id.visita_qcheckRtqAudi) {
		    if (on) {
		        // Enable vibrate
		    	audiCheckRtq[1] = "1";
		    } else {
		        // Disable vibrate
		    	audiCheckRtq[1] = "0";
		    }
		}
		if (tb.getId() == R.id.visita_qcheckRtqVW) {
		    if (on) {
		        // Enable vibrate
		    	vwCheckRtq[1] = "1";
		    } else {
		        // Disable vibrate
		    	vwCheckRtq[1] = "0";
		    }
		}
		if (tb.getId() == R.id.visita_qcheckRtqSkoda) {
		    if (on) {
		        // Enable vibrate
		    	skodaCheckRtq[1] = "1";
		    } else {
		        // Disable vibrate
		    	skodaCheckRtq[1] = "0";
		    }
		}
        if (tb.getId() == R.id.visita_qcheckRtqSeat) {
		    if (on) {
		        // Enable vibrate
		    	seatCheckRtq[1] = "1";
		    } else {
		        // Disable vibrate
		    	seatCheckRtq[1] = "0";
		    }
		}
		if (tb.getId() == R.id.visita_qcheckRtqVic) {
		    if (on) {
		        // Enable vibrate
		    	vicCheckRtq[1] = "1";
		    } else {
		        // Disable vibrate
		    	vicCheckRtq[1] = "0";
		    }
		}
		if (tb.getId() == R.id.visita_qcheckAudi) {
		    if (on) {
		        // Enable vibrate
		    	audiCheck[1] = "1";
		    } else {
		        // Disable vibrate
		    	audiCheck[1] = "0";
		    }
		}
        if (tb.getId() == R.id.visita_qcheckVW) {
		    if (on) {
		        // Enable vibrate
		    	vwCheck[1] = "1";
		    } else {
		        // Disable vibrate
		    	vwCheck[1] = "0";
		    }
		}
		if (tb.getId() == R.id.visita_qcheckSkoda) {
		    if (on) {
		        // Enable vibrate
		    	skodaCheck[1] = "1";
		    } else {
		        // Disable vibrate
		    	skodaCheck[1] = "0";
		    }
		}
		if (tb.getId() == R.id.visita_qcheckSeat) {
		    if (on) {
		        // Enable vibrate
		    	seatCheck[1] = "1";
		    } else {
		        // Disable vibrate
		    	seatCheck[1] = "0";
		    }
		}
        if (tb.getId() == R.id.visita_qcheckVic) {
		    if (on) {
		        // Enable vibrate
		    	vicCheck[1] = "1";
		    } else {
		        // Disable vibrate
		    	vicCheck[1] = "0";
		    }
		}
		if (tb.getId() == R.id.visita_diss2Settimane) {
		    if (on) {
		        // Enable vibrate
		    	diss2 = "1";
		    } else {
		        // Disable vibrate
		    	diss2 = "0";
		    }
		}
		if (tb.getId() == R.id.visita_diss8Settimane) {
		    if (on) {
		        // Enable vibrate
		    	diss8 = "1";
		    } else {
		        // Disable vibrate
		    	diss8 = "0";
		    }
		}
        if (tb.getId() == R.id.visita_ruoliInBdo) {
		    if (on) {
		        // Enable vibrate
		    	ruoliDBO = "1";
		    } else {
		        // Disable vibrate
		    	ruoliDBO = "0";
		    }
		}
		if (tb.getId() == R.id.visita_ccAzioniDiRichiamo) {
		    if (on) {
		        // Enable vibrate
		    	ccAzioniRichiamo = "1";
		    } else {
		        // Disable vibrate
		    	ccAzioniRichiamo = "0";
		    }
		}
		if (tb.getId() == R.id.visita_rrStampaCorrettivo) {
		    if (on) {
		        // Enable vibrate
		    	rrStampaCorrispettivi = "1";
		    } else {
		        // Disable vibrate
		    	rrStampaCorrispettivi = "0";
		    }
		}
		if (tb.getId() == R.id.visita_relazione) {
		    if (on) {
		        // Enable vibrate
		    	relazione = "1";
		    } else {
		        // Disable vibrate
		    	relazione = "0";
		    }
		}
//        if (tb.getId() == R.id.visita_cssAudi) {
//		    if (on) {
//		        // Enable vibrate
//		    	audiCss[1] = "1";
//		    } else {
//		        // Disable vibrate
//		    	audiCss[1] = "0";
//		    }
//		}
//		if (tb.getId() == R.id.visita_cssVW) {
//		    if (on) {
//		        // Enable vibrate
//		    	vwCss[1] = "1";
//		    } else {
//		        // Disable vibrate
//		    	vwCss[1] = "0";
//		    }
//		}
//		if (tb.getId() == R.id.visita_cssSkoda) {
//		    if (on) {
//		        // Enable vibrate
//		    	skodaCss[1] = "1";
//		    } else {
//		        // Disable vibrate
//		    	skodaCss[1] = "0";
//		    }
//		}
//        if (tb.getId() == R.id.visita_cssSeat) {
//		    if (on) {
//		        // Enable vibrate
//		    	seatCss[1] = "1";
//		    } else {
//		        // Disable vibrate
//		    	skodaCss[1] = "0";
//		    }
//		}
//		if (tb.getId() == R.id.visita_cssVic) {
//		    if (on) {
//		        // Enable vibrate
//		    	vicCss[1] = "1";
//		    } else {
//		        // Disable vibrate
//		    	vicCss[1] = "0";
//		    }
//		}
	}
	
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    	int androidID = parent.getId();
        
    	if (androidID == R.id.visita_cssAudi) {
    		audiCss[1] = parent.getItemAtPosition(pos).toString();
    	}
    	if (androidID == R.id.visita_cssVW) {
    		vwCss[1] = parent.getItemAtPosition(pos).toString();
    	}
    	if (androidID == R.id.visita_cssSkoda) {
    		skodaCss[1] = parent.getItemAtPosition(pos).toString();
    	}
    	if (androidID == R.id.visita_cssSeat) {
    		seatCss[1] = parent.getItemAtPosition(pos).toString();
    	}
    	if (androidID == R.id.visita_cssVic) {
    		vicCss[1] = parent.getItemAtPosition(pos).toString();
    	}
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

	public void salva (View arg0) {
        Utility.log("salva INI");
        EditText vData =(EditText) findViewById(R.id.visita_data);
        EditText vDiss =(EditText) findViewById(R.id.visita_diss);
        EditText vDissInfo =(EditText) findViewById(R.id.visita_dissInfo);
        EditText vDissReqTec =(EditText) findViewById(R.id.visita_dissRichiestaTecnica);
        EditText vAttrezzatura =(EditText) findViewById(R.id.visita_attrezzatura);
        EditText vOdl =(EditText) findViewById(R.id.visita_odlFatture);
        EditText vFormazione =(EditText) findViewById(R.id.visita_formazione);
        EditText vNote =(EditText) findViewById(R.id.visita_note);
        
    	// CAMPI DELLA TUPLA
    	data = vData.getText().toString();
    	diss = vDiss.getText().toString();
    	dissInfo = vDissInfo.getText().toString();
    	dissRichTecniche = vDissReqTec.getText().toString();
    	attrezzatura = vAttrezzatura.getText().toString();
    	odl = vOdl.getText().toString();
    	formazione = vFormazione.getText().toString();
    	note = vNote.getText().toString();

        String badValue[] = {null};
        boolean procedi=true;
        
        procedi = Utility.checkDateField(data, badValue, getApplicationContext());
        diss = Utility.checkTextField(diss, badValue, "", -1);
        dissInfo = Utility.checkTextField(dissInfo, badValue, "", -1);
        dissRichTecniche = Utility.checkTextField(dissRichTecniche, badValue, "", -1);
        attrezzatura = Utility.checkTextField(attrezzatura, badValue, "", -1);
        odl = Utility.checkTextField(odl, badValue, "", -1);
        formazione = Utility.checkTextField(formazione, badValue, "", -1);
        note = Utility.checkTextField(note, badValue, "", -1);

        marcaClienteIn = audi[0] + "," + audi[1] + ";" +
                vw[0] + "," + vw[1] + ";" +
                skoda[0] + "," + skoda[1] + ";" +
                seat[0] + "," + seat[1] + ";" +
                vic[0] + "," + vic[1];

        qCheckRtqIn = audiCheckRtq[0] + "," + audiCheckRtq[1] + ";" +
                vwCheckRtq[0] + "," + vwCheckRtq[1] + ";" +
                skodaCheckRtq[0] + "," + skodaCheckRtq[1] + ";" +
                seatCheckRtq[0] + "," + seatCheckRtq[1] + ";" +
                vicCheckRtq[0] + "," + vicCheckRtq[1];

        qCheckIn = audiCheck[0] + "," + audiCheck[1] + ";" +
                vwCheck[0] + "," + vwCheck[1] + ";" +
                skodaCheck[0] + "," + skodaCheck[1] + ";" +
                seatCheck[0] + "," + seatCheck[1] + ";" +
                vicCheck[0] + "," + vicCheck[1];

        cssIn = audiCss[0] + "," + audiCss[1] + ";" +
                vwCss[0] + "," + vwCss[1] + ";" +
                skodaCss[0] + "," + skodaCss[1] + ";" +
                seatCss[0] + "," + seatCss[1] + ";" +
                vicCss[0] + "," + vicCss[1];

        
        marcaClienteIn = Utility.checkTextField(marcaClienteIn, badValue, "1,0;2,0;3,0;4,0;5,0", 19);
        qCheckRtqIn = Utility.checkTextField(qCheckRtqIn, badValue, "1,0;2,0;3,0;4,0;5,0", 19);
        qCheckIn = Utility.checkTextField(qCheckIn, badValue, "1,0;2,0;3,0;4,0;5,0", 19);
        cssIn = Utility.checkTextField(cssIn, badValue, "1,0;2,0;3,0;4,0;5,0", -1);

        if (procedi == true) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
	        values.put(LocalDB.visita.COLUMN_NAME_CLIENTE_FK, cliente_fk);
	        values.put(LocalDB.visita.COLUMN_NAME_DATA, data);
	        values.put(LocalDB.visita.COLUMN_NAME_Q_CHECK_RTQ, qCheckRtqIn);
	        values.put(LocalDB.visita.COLUMN_NAME_Q_CHECK, qCheckIn);

	        values.put(LocalDB.visita.COLUMN_NAME_DISS, diss);
	        values.put(LocalDB.visita.COLUMN_NAME_DISS_2, diss2);
	        values.put(LocalDB.visita.COLUMN_NAME_DISS_8, diss8);
	        values.put(LocalDB.visita.COLUMN_NAME_DISS_INFO, dissInfo);
	        values.put(LocalDB.visita.COLUMN_NAME_DISS_RICH_TECNICHE, dissRichTecniche);
	        values.put(LocalDB.visita.COLUMN_NAME_ATTREZZATURA, attrezzatura);
	        values.put(LocalDB.visita.COLUMN_NAME_RUOLI_DBO, ruoliDBO);
	        values.put(LocalDB.visita.COLUMN_NAME_CC_AZIONI_RICHIAMO, ccAzioniRichiamo);
	        values.put(LocalDB.visita.COLUMN_NAME_RR_STAMPA_CORRETTIVI, rrStampaCorrispettivi);
	        values.put(LocalDB.visita.COLUMN_NAME_CSS, cssIn);
	        values.put(LocalDB.visita.COLUMN_NAME_ODL, odl);
	        values.put(LocalDB.visita.COLUMN_NAME_FORMAZIONE, formazione);
	        values.put(LocalDB.visita.COLUMN_NAME_RELAZIONE, relazione);
	        values.put(LocalDB.visita.COLUMN_NAME_NOTE, note);

			if (this.cliente_fk != -1) {
	            values.put(LocalDB.visita.COLUMN_NAME_CLIENTE_FK, this.cliente_fk);
	        }
	        
	        Utility.log("cliente_fk " + cliente_fk);
	
	        if (idTupla == -1) {
				/*      
				 * 		Queste sono per inserire una riga su una tabella  
				 */ 
				QueryManager q = new QueryManager(getApplicationContext());
				q.openDB(true);
		        long numRow = q.insertRow(LocalDB.visita.TABLE_NAME, values);
		        q.closeDB();
		        
		        idTupla = (int) numRow;
	        } else {
	
	        	// Which row to update, based on the ID
	        	String selection = LocalDB.Cliente._ID + " = ?";
	        	String[] selectionArgs = { String.valueOf(idTupla) };
	
	        	QueryManager q = new QueryManager(getApplicationContext());
	        	q.openDB(true);
	        	q.updateRow(LocalDB.visita.TABLE_NAME, values, selection, selectionArgs);
	            q.closeDB();
	        }
	
	        this.finish();
	
	        Utility.log("salva END");
        }
	}
}
