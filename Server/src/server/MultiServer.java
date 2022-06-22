package server;

import java.io.*;
import java.net.*;

/**
 * Classe che gestisce il server
 */
public class MultiServer {
  private int PORT;

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
   */
  public static void istanceMultiserver(){
    new MultiServer(2025);
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
          System.out.println("Connessione accettata: " + socket);

          try {
            new ServerOneClient(socket);
          } catch (IOException e) {
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
