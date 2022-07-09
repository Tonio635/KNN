package knn.knn.server;

import java.util.HashMap;
import java.util.List;

import knn.knn.mining.KNN;

/**
 * Classe che gestisce il server
 */
public class Server {
	/** Attributo che rende singleton la classe */
	private static Server singleton = null;
	/** Attributo che salva i modelli KNN dei client */
	private static HashMap<Long, KNN> clientModels = new HashMap<Long, KNN>();

	/**
	 * Costruttore di classe
	 */
	private Server() {
		new ClearClientModels();
	}

	/**
	 * Aggiunge il modello KNN al client
	 * 
	 * @param id
	 */
	public static void addKNN(Long id, KNN k) {
		clientModels.put(id, k);
	}

	/**
	 * Restituisce il nuovo id al client facendo in modo di non
	 * avere id duplicati
	 * 
	 * @return id
	 */
	public static Long getNewID() {
		Long newID;

		do {
			newID = new Long(System.currentTimeMillis());
		} while (clientModels.containsKey(newID));

		addKNN(newID, null);

		return newID;
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

	/**
	 * Classe in background che ripulisce i knn ogni ora per evitare sovraccarichi
	 */
	class ClearClientModels extends Thread {

		/** Range di tempo(in millisecondi) ogni quanto vengono ripuliti i knn vecchi. */
		private static final long time = 3600;

		/**
		 * Costruttore di ClearClientModels, fa partire un nuovo thread in background.
		 */
		ClearClientModels() {
			start();
		}

		/**
		 * Thread che ripulisce i vecchi knn dei client ogni ora.
		 */
		public void run() {
			try {
				while(true) {
					sleep(time);

					List<Long> l = clientModels.keySet().
					stream().
					filter(p -> (p + time) < new Long(System.currentTimeMillis()))
					.mapToLong(p -> p)
					.boxed()
					.toList();

					for(Long idToRemove: l) {
						clientModels.remove(idToRemove);
					}
				}

			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}
