//********************************************************************
//  Keyboard.java       Author: Lewis and Loftus
//
//  Facilitates keyboard input by abstracting details about input
//  parsing, conversions, and exception handling.
//********************************************************************

package utility;

import java.io.*;
import java.util.*;

/*
 * Classe di utility che serve per leggere gli input da tastiera
 */
public class Keyboard {
	/**
	 * Attributi che servono per gestire gli errori dello Stream
	 */

	private static boolean printErrors = true;

	private static int errorCount = 0;

	
	/**
	 * Ritorna l'errore corrente
	 * @return errorCount errore corrente
	 */
	public static int getErrorCount() {
		return errorCount;
	}

	/**
	 * Setta l'errore a 0.
	 * @param count setta il count
	 */
	public static void resetErrorCount(int count) {
		errorCount = 0;
	}

	/**
	 * Ritorna un boolean che indica se sono presenti errori in input
	 * @return printErrors errori di input
	 */
	public static boolean getPrintErrors() {
		return printErrors;
	}

	/**
	 * Setta un boolean che indica se sono presenti errori in input
	 * @param flag errori di input
	 */
	public static void setPrintErrors(boolean flag) {
		printErrors = flag;
	}

	/**
	 * Incrementa il contatore degli errori e stampa un messaggio di errore appropriato
	 * @param str stringa come messaggio
	 */
	private static void error(String str) {
		errorCount++;
		if (printErrors)
			System.out.println(str);
	}

	/**
	 * Sezione input tokenizzato.
	 */
	private static String current_token = null;

	private static StringTokenizer reader;

	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	/**
	 * Ritorna il prossimo token di input che potrebbe essere il successivo
	 * @return getNextToken token
	 */
	private static String getNextToken() {
		return getNextToken(true);
	}

	/**
	 * Ritorna il prossimo token di input che potrebbe essere gia stato letto
	 * @param skip booleano che indica se è stato letto o meno
	 * @return token
	 */
	private static String getNextToken(boolean skip) {
		String token;

		if (current_token == null)
			token = getNextInputToken(skip);
		else {
			token = current_token;
			current_token = null;
		}

		return token;
	}

	/**
	 * Ottiene il token successivo dall'input, che può provenire da 
	 * riga di input corrente o successiva. 
	 * Il parametro determina se vengono utilizzate le righe successive.
	 * @param skip booleano che indica il parametro
	 * @return token
	 */
	private static String getNextInputToken(boolean skip) {
		final String delimiters = " \t\n\r\f";
		String token = null;

		try {
			if (reader == null)
				reader = new StringTokenizer(in.readLine(), delimiters, true);

			while (token == null || ((delimiters.indexOf(token) >= 0) && skip)) {
				while (!reader.hasMoreTokens())
					reader = new StringTokenizer(in.readLine(), delimiters,
							true);

				token = reader.nextToken();
			}
		} catch (Exception exception) {
			token = null;
		}

		return token;
	}

	/**
	 * Restituisce true se non ci sono più token da leggere sulla
	 * riga di input corrente.
	 * @return booleano
	 */
	private static boolean endOfLine() {
		return !reader.hasMoreTokens();
	}

	/*
	 * Sezione di lettura
	 */

	
	/**
	 * Ritorna la stringa letta dallo standard di input.
	 * @return str
	 */
	public static String readString() {
		String str;

		try {
			str = getNextToken(false);
			while (!endOfLine()) {
				str = str + getNextToken(false);
			}
		} catch (Exception exception) {
			error("Error reading String data, null value returned.");
			str = null;
		}
		return str;
	}

	/**
	 * Ritorna il valore intero letto dallo standard di input
	 * @return value
	 */
	public static int readInt() {
		String token = getNextToken();
		int value;
		try {
			value = Integer.parseInt(token);
		} catch (Exception exception) {
			error("Error reading int data, MIN_VALUE value returned.");
			value = Integer.MIN_VALUE;
		}
		return value;
	}

	/**
	 * Ritorna il double letto dallo standard di input
	 * @return value
	 */
	public static double readDouble() {
		String token = getNextToken();
		double value;
		try {
			value = Double.parseDouble(token);
		} catch (Exception exception) {
			error("Error reading double data, NaN value returned.");
			value = Double.NaN;
		}
		return value;
	}
}
