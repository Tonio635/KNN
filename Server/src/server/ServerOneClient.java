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
import utility.Keyboard;

public class ServerOneClient extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerOneClient(Socket s) throws IOException{
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.start();
    }

    public void run(){
        while(true){
            try {
                String decision = (String) in.readObject();
                String tableName = (String) in.readObject();
                KNN knn = null;
                Data trainingSet = null;

                switch(decision) {
                    case "1": {
                        try {
                            System.out.println("Nome file contenente un training set valido:");
                            tableName += ".dat";
                            trainingSet = new Data(tableName);
                            out.writeObject(trainingSet);
                        } catch(TrainingDataException exc){
                            System.out.println(exc.getMessage());
                            out.writeObject("@ERROR");
                        }							
                        
                        knn = new KNN(trainingSet);
                        knn.salva(tableName + ".dmp");
                    }
                    break;

                    case "2": {
                        try {
                            System.out.println("Nome file contenente una serializzazione dell'oggetto KNN:");
                            String file = Keyboard.readString();
                            knn = KNN.carica(file);
                        } catch (IOException | ClassNotFoundException exc) {
                            System.out.println(exc.getMessage());
                            out.writeObject("@ERROR");
                        }
                    }
                    break;

                    case "3": {
                        try {
                            System.out.print("Connecting to DB...");
                            DbAccess db = new DbAccess();
                            System.out.println("done!");
                            System.out.println("Nome tabella: " + tableName);
                            trainingSet = new Data(db, tableName);
                            System.out.println(trainingSet);
                            db.closeConnection();
                        }
                        catch(InsufficientColumnNumberException | TrainingDataException exc1){
                            System.out.println(exc1.getMessage());
                            out.writeObject("@ERROR");
                        }
                        catch(DatabaseConnectionException exc2){
                            System.out.println(exc2.getMessage());
                            out.writeObject("@ERROR");
                        }			
                        
                        knn = new KNN(trainingSet);

                        knn.salva(tableName + "DB.dmp");
                    }
                    break;
                }

                knn.predict(out, in);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    in.close();
                    out.close();
                } catch (IOException e) {
                    System.err.println("Errore nella chiusura del socket o degli ObjectStream");
                }
            }
        }
    }
}
