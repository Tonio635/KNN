package knn.knn.server;

/**
 * Classe che gestisce il server
 */
public class Server {
	/** Attributo che rende singleton la classe */
	private static Server singleton = null;

	/**
	 * Costruttore di classe
	 */
	private Server() {
	}

	/**
	 * Metodo che serve per rendere Singleton la classe Server.
	 * Istanzio un nuovo server solo 1 volta, quelle successive non potrò più farlo.
	 * 
	 * @return L'oggetto instanceServer istanziato.
	 */
	public static Server instanceServer() {
		if (singleton == null)
			singleton = new Server();
		return singleton;
	}
}
