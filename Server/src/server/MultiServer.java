package server;

import java.io.*;
import java.net.*;

/**
 * Classe che gestisce il server
 */
public class MultiServer{
  private int PORT;

  /**
   * Costruttore di classe, inizializza la porta ed invoca run()
   * 
   * @param port
   */
  public MultiServer(int port)
  {
    this.PORT = port;
    run();
  }

  /**
   * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di 
   * richiesta di connessioni da parte del client. Ad ogni nuova richiesta connessione 
   * si istanzia ServerOneClient.
   */
  private void run()
  {
    try{
      ServerSocket s = new ServerSocket(PORT);
      System.out.println("Started: " + s);
      try {
        // si blocca fino a quando non c’è una connessione
        Socket socket = s.accept();
        // connessione accettata
        try {
          new ServerOneClient(socket);
        } finally {
          System.out.println("closing...");
          socket.close();
        }
      } 
      finally {
        s.close();//ServerSocket
      }
    }catch(IOException e)
    {
      System.out.println("Errore...");
    }
  }
}