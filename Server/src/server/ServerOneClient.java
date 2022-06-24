package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.Data;
import data.TrainingDataException;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.InsufficientColumnNumberException;
import mining.KNN;

/**
 * Classe che estende la classe Thread e che serve ad avviarne uno nuovo.
 * Gestisce, inoltre, la comunicazione con il client attraverso oggetti di
 * input e output stream.
 */
public class ServerOneClient extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * Costruttore di classe.
     * Inizializza gli attributi socket, in e out.
     * Avvia il thread.
     * 
     * @param s Socket con cui inizializzare l'attributo.
     * @throws IOException Eccezione per il controllo dei flussi di Input/Output.
     */
    public ServerOneClient(Socket s) throws IOException {
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.start();
    }

    /**
     * Metodo che stabilisce il punto di entrata per un nuovo thread di
     * esecuzione all’interno del programma.
     */
    public void run() {
        try {
            while (true) {
                Integer decision = (Integer) in.readObject();
                String tableName = null;
                KNN knn = null;
                Data trainingSet = null;

                switch (decision) {
                    case 1: {
                        boolean flag = false;
                        do {
                            try {
                                tableName = (String) in.readObject();
                                tableName += ".dat";
                                System.out.println("Nome file contenente un training set valido: " + tableName);
                                trainingSet = new Data(tableName);
                                out.writeObject("@SUCCESS");
                                flag = true;
                            } catch (TrainingDataException exc) {
                                System.out.println(exc.getMessage());
                                out.writeObject("@ERROR");
                            }
                        } while (!flag);

                        knn = new KNN(trainingSet);
                        knn.salva(tableName + ".dmp");
                    }
                    break;

                    case 2: {
                        boolean flag = false;
                        do {
                            try {
                                tableName = (String) in.readObject();
                                String file = tableName + ".dmp";
                                System.out.println("Nome file contenente una serializzazione dell'oggetto KNN:" + tableName);
                                knn = KNN.carica(file);
                                out.writeObject("@SUCCESS");
                                flag = true;
                            } catch (IOException | ClassNotFoundException exc) {
                                System.out.println(exc.getMessage());
                                out.writeObject("@ERROR");
                            }
                        } while (!flag);
                    }
                    break;

                    case 3: {
                        boolean flag = false;
                        do{
                            try {
                                System.out.print("Connessione al database...");
                                DbAccess db = new DbAccess();
                                System.out.println("Fatto!");
                                tableName = (String) in.readObject();
                                System.out.println("Nome tabella: " + tableName);
                                trainingSet = new Data(db, tableName);
                                System.out.println(trainingSet);
                                db.closeConnection();
                                out.writeObject("@SUCCESS");
                                flag = true;
                            } catch (InsufficientColumnNumberException | TrainingDataException exc1) {
                                System.out.println(exc1.getMessage());
                                out.writeObject("@ERROR");
                            } catch (DatabaseConnectionException exc2) {
                                System.out.println(exc2.getMessage());
                                out.writeObject("@ERROR");
                            }
                        } while(!flag);

                        knn = new KNN(trainingSet);

                        knn.salva(tableName + "DB.dmp");
                    }
                    break;
                }

                String str = "";
                do {
                    out.writeObject(knn.predict(out, in));
                    str = (String) in.readObject();
                } while (str.equalsIgnoreCase("y"));

                str = (String) in.readObject();
                if (!str.equalsIgnoreCase("y"))
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Disconnessione client: " + socket);
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println("Errore nella chiusura del socket o degli ObjectStream");
            }
        }
    }
}
