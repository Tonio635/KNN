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
   * @param port
   */
  public MultiServer(int port) {
    this.PORT = port;
    run();
  }

  /**
   * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di
   * richiesta di connessioni da parte del client. Ad ogni nuova richiesta
   * connessione si istanzia ServerOneClient.
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
            socket.close();
          }
        }
      } finally {
        s.close(); // ServerSocket
      }
    } catch (IOException e) {
      System.out.println("Errore...");
    }
  }
}
