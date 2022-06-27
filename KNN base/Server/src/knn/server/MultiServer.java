package knn.server;

import java.io.*;
import java.net.*;

/**
 * Classe che gestisce il server
 */
public class MultiServer {
  private int PORT;
  private static MultiServer singleton = null;      //Attributo che ci serve per rendere la classe singleton
  
  /**
   * Costruttore di classe, inizializza la porta ed invoca run()
   * 
   * @param port che indica la porta alla quale connettersi
   */
  private MultiServer(int port) {
    this.PORT = port;
    run();
  }

   /**
   * Metodo che serve per istanziare un nuovo Server, specificando
   * la porta in modo tale da rendere Singleton la classe Server.
   * Istanzio un nuovo server solo 1 volta, quelle successive non potrò più farlo.
   * 
   * @return L'oggetto MultiServer istanziato.
   */
  public static MultiServer instanceMultiServer(){
    if(singleton == null)
      singleton = new MultiServer(2025);
    return singleton;
  }

  /**
   * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di
   * richiesta di connessioni da parte del client. Ad ogni nuova richiesta
   * connessione si istanzia ServerOneClient sfruttando cosi il MultiThreading
   */
  private void run() {
    try {
      ServerSocket s = new ServerSocket(PORT);
      System.out.println("Started: " + s);
      try {
        while (true) {
          Socket socket = s.accept();
          System.out.println("Connessione client: " + socket);

          try {
            new ServerOneClient(socket);
          } catch (IOException e) {
            System.out.println("Errore nell'istanziazione del socket: " + socket);
            socket.close();  // chiude la socket se va male l'istanziazione
          }
        }
      } finally {
        s.close(); // ServerSocket, serve se ci fanno un kill del thread dall'esterno
      }
    } catch (IOException e) {
      System.out.println("Errore...");
    }
  }
}
