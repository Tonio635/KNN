package knn;

import knn.server.MultiServer;

/**
 * Classe main del Server.
 */
public class Main {

	/**
	 * Punto di partenza dell'applicazione lato server.
	 * 
	 * @param args argomenti passati da terminale (non vengono gestiti)
	 */
	public static void main(String[] args) {
		MultiServer.instanceMultiServer();
	}
}
