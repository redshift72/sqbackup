package com.gt.helprtq;

import android.provider.BaseColumns;

public final class LocalDB {
    // DB name
	public static final String DATABASE_NAME = "helprtq.db";
	//DB version
	public static final int DATABASE_VERSION = 1;
    
	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public LocalDB() {}

    /* Inner class that defines the table contents */
    public static abstract class DB implements BaseColumns {
        public static final String TABLE_NAME = "db";
        
        public static final String COLUMN_NAME_VERSIONE = "versione";
        public static final String COLUMN_NAME_STATO = "stato";
    }

    /* Inner class that defines the table contents */
    public static abstract class Cliente implements BaseColumns {
        public static final String TABLE_NAME = "cliente";
        
        public static final String COLUMN_NAME_NOME = "ragione_sociale";
        public static final String COLUMN_NAME_INDIRIZZO = "indirizzo";
        public static final String COLUMN_NAME_TELEFONO = "telefono";
        public static final String COLUMN_NAME_CODICE_ORGANIZZATO = "codice";
        public static final String COLUMN_NAME_MARCHE = "marche";
    }

    /* Inner class that defines the table contents */
    public static abstract class Audit implements BaseColumns {
        public static final String TABLE_NAME = "audit";
        
        public static final String COLUMN_NAME_CLIENTE_FK = "cliente_fk";
        public static final String COLUMN_NAME_VISITA_FK = "visita_fk";

        public static final String COLUMN_NAME_DATA_MAIL = "data_mail";
        public static final String COLUMN_NAME_ESITO = "esito";
    }

    /* Inner class that defines the table contents */
    public static abstract class Assessment implements BaseColumns {
        public static final String TABLE_NAME = "assessment";
        
        public static final String COLUMN_NAME_CLIENTE_FK = "cliente_fk";
        public static final String COLUMN_NAME_VISITA_FK = "visita_fk";
        
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_RS = "rs";
        public static final String COLUMN_NAME_RC = "rc";
        public static final String COLUMN_NAME_CONSEGNATO = "consegnato";
    }
    
    /* Inner class that defines the table contents */
    public static abstract class pt implements BaseColumns {
        public static final String TABLE_NAME = "pt";
        
        public static final String COLUMN_NAME_CLIENTE_FK = "cliente_fk";
        public static final String COLUMN_NAME_VISITA_FK = "visita_fk";
        
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_ESITO = "esito";
        public static final String COLUMN_NAME_MARCA = "marca";
        public static final String COLUMN_NAME_DISCUSSO = "discusso";
        public static final String COLUMN_NAME_CORRETTIVO = "correttivo";
    }

    /* Inner class that defines the table contents */
    public static abstract class marche implements BaseColumns {
        public static final String TABLE_NAME = "marche";
        
        public static final String COLUMN_NAME_MARCA = "marca";
    }

    /* Inner class that defines the table contents */
    public static abstract class vettura implements BaseColumns {
        public static final String TABLE_NAME = "vettura";
        
        public static final String COLUMN_NAME_CLIENTE_FK = "cliente_fk";
        public static final String COLUMN_NAME_VISITA_FK = "visita_fk";
        
        public static final String COLUMN_NAME_TELAIO = "telaio";
        public static final String COLUMN_NAME_ESITO_BATTERIA = "esito_batteria";
        public static final String COLUMN_NAME_ESITO_PNEUMATICI = "esito_pneumatici";
        public static final String COLUMN_NAME_ESITO_DOCU = "esito_docu";
    }

    /* Inner class that defines the table contents */
    public static abstract class visita implements BaseColumns {
        public static final String TABLE_NAME = "visita";
        
        public static final String COLUMN_NAME_CLIENTE_FK = "cliente_fk";
        
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_Q_CHECK_RTQ = "q_check_rtq"; 
        public static final String COLUMN_NAME_Q_CHECK = "q_check"; 
        public static final String COLUMN_NAME_DISS = "diss";
        public static final String COLUMN_NAME_DISS_2 = "diss_2";
        public static final String COLUMN_NAME_DISS_8 = "diss_8";
        public static final String COLUMN_NAME_DISS_INFO = "diss_info";
        public static final String COLUMN_NAME_DISS_RICH_TECNICHE = "diss_tec";
        public static final String COLUMN_NAME_ATTREZZATURA = "attrezzatura";
        public static final String COLUMN_NAME_RUOLI_DBO = "ruoli_BDO";
        public static final String COLUMN_NAME_CC_AZIONI_RICHIAMO = "azioni_rich";
        public static final String COLUMN_NAME_RR_STAMPA_CORRETTIVI = "stampa_correttivi";
        public static final String COLUMN_NAME_CSS = "css";
        public static final String COLUMN_NAME_ODL = "odl";
        public static final String COLUMN_NAME_FORMAZIONE = "formazione";
        public static final String COLUMN_NAME_RELAZIONE = "relazione";
        public static final String COLUMN_NAME_NOTE = "note";
    }
        
}
