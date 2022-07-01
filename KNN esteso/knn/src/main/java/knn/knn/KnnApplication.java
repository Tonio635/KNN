package knn.knn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import knn.knn.server.Server;

/**
 * Classe di partenza del Server.
 */
@SpringBootApplication
public class KnnApplication{

	/**
	 * Punto di partenza dell'applicazione server.
	 * 
	 * @param args argomenti passati da terminale (non vengono gestiti)
	 */
	public static void main(String[] args) {
		SpringApplication.run(KnnApplication.class, args);
		Server.instanceServer();
	}

}
