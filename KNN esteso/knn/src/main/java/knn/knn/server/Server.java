package knn.knn.server;

import java.util.HashMap;

import knn.knn.mining.KNN;

/**
 * Classe che gestisce il server
 */
public class Server extends Thread{
	/** Attributo che rende singleton la classe */
	private static Server singleton = null;
	/** Attributo che salva i modelli KNN dei client */
	private static HashMap<Integer, KNN> models = new HashMap<Integer, KNN>();

	/**
	 * Costruttore di classe
	 */
	private Server() {
		start();
	}

	/**
	 * Aggiunge il modello KNN al client
	 * @param id
	 */
	public static void addKNN(Integer id, KNN k) {
		models.put(id, k);
	}

	/**
	 * Restituisce il nuovo id al client
	 * 
	 * @return id
	 */
	public static Integer getNewID() {
		Integer newID;
		
		do{
			newID = new Long(System.currentTimeMillis()).intValue();
		}while(!models.containsKey(newID));
		
		addKNN(newID, null);

		return newID;
	}

	/**
	 * Thread che ripulisce i vecchi knn dei client ogni ora
	 */
	public void run() {
		try {
			models.keySet().
			stream().filter(p-> p < new Long(System.currentTimeMillis()).intValue()).
			mapToInt(p->p).
			forEach((p) -> models.remove(p));

			sleep(60 * 3600 * 1000);
		} catch (InterruptedException e) {

		}
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
