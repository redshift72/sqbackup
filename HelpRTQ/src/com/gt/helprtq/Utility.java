package com.gt.helprtq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public final class Utility {
	private static boolean booWrite = true;

	public Utility () {}
	
	public static boolean getBooWrite() {
		return booWrite;
	}

	public static void setBooWrite(boolean bool) {
		booWrite = bool;
	}

	public static String checkTextField(String variable, String[] badValue, String defaulValue, int length) {
		String str2Return = variable;
		
		for (int k = 0; badValue!= null && k < badValue.length; k++) {
			if (badValue[k] == null) {
				if (variable == null) {
					str2Return = defaulValue;
				}
			} else {
				if (variable.equals(badValue[k]) == true) {
					str2Return = defaulValue;
				}
			}
		}
		
        if (length != -1 && str2Return.length() != length) str2Return = defaulValue; 
		
		return str2Return;
	}
	
	public static boolean checkDateField(String variable, String[] badValue, Context context) {
		boolean boo2Return = true;
		
		variable = checkTextField(variable, badValue, "", -1);

        Utility.log("--- data dop la check: " + variable);
		if (variable.equals("") == false) {
	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY); 
	        Date checkDate;
	
	        try {
		    	checkDate = df.parse(variable);
		        String newDateString = df.format(checkDate);
		        Utility.log("--- data PRIMA: " + variable);
		        Utility.log("--- data DOPO: " + newDateString);
	
		        if (variable.equals(newDateString) == false) {
			    	boo2Return=false;
			    	Utility.log("--- data non valida!!!!! " + variable);
	
			    	CharSequence text = "La data non è valida verificare";
		        	int duration = Toast.LENGTH_SHORT;
		        	Toast toast = Toast.makeText(context, text, duration);
		        	toast.setGravity(Gravity.TOP, 0, 180);
		        	toast.show();        	
		        }
	
		    	return boo2Return;
	
		    } catch (Exception e) {
		    	boo2Return=false;
		    	Utility.log("--- data a CAZZO!!!!! " + variable);
	
		    	CharSequence text = "La data deve avere il formato gg/mm/aaaa";
	        	int duration = Toast.LENGTH_SHORT;
	        	Toast toast = Toast.makeText(context, text, duration);
	        	toast.setGravity(Gravity.TOP, 0, 180);
	        	toast.setText(text);
	        	toast.show();        	
		    	
		    	return boo2Return;
		    }
		}

		return boo2Return;
	}
	
	public static String convertBooleanField(String variableName, String variable) {
		String str2Return = variable;

		if (variable.equals("1") == true) {
			str2Return = "<b>" + variableName + "</b>";
		} else {
			str2Return = "";//"<u>" + variableName + "</u>";
		}
		
		return str2Return;
	}
	
	public static String convertDateField(String variable) {
		String str2Return = variable;

		if (variable.equals("") == true) {
			str2Return = "gg/mm/aaaa";
		} else {
			str2Return = variable;
		}
		
		return str2Return;
	}
	
	public static void log (String out) {
		if (getBooWrite() == true) System.out.println(out);
	}

	public static void print (String out) {
		System.out.println(out);
	}
}
