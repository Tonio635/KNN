package client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import utility.Keyboard;

/**
 * Classe Client che si collega al server tramite socket.
 */
public class Client {

	/** Attributo socket che si connette con il server. */
	private Socket socket = null;

	/** Canale di output del socket. */
	private ObjectOutputStream out = null;

	/** Canale di input del socket. */
	private ObjectInputStream in = null;

	/**
	 * Crea un nuovo oggetto client che si connette al server, se tutto è andato a
	 * buon fine.
	 * Nel caso contrario lancia un eccezione ed impedisce di connettersi.
	 * 
	 * @param address indirizzo del server
	 * @param port    porta del server
	 * @throws IOException            Eccezione di input e output
	 * @throws ClassNotFoundException Eccezione se non è stata trovata la classe
	 * @throws UnknownHostException   Eccezione se non è stato trovato il server
	 */
	public Client(String address, int port) throws IOException, ClassNotFoundException, UnknownHostException {

		// Verifica se l'host è stato trovato, altrimenti lancia l'eccezione
		InetAddress.getByName(address);

		socket = new Socket(address, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		stampaBenvenuto();
		comunica();
	}

	/**
	 * Stampa il messaggio di apertura dell'applicazione.
	 * 
	 * @throws IOException            Eccezione di input e output
	 * @throws ClassNotFoundException Eccezione se non è stata trovata la classe
	 */
	private void stampaBenvenuto() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream("src/utility/welcome.dat");
		ObjectInputStream in = new ObjectInputStream(file);
		String welcomeString = (String) in.readObject();

		System.out.println(welcomeString);

		file.close();
		in.close();
	}

	/**
	 * Comunicazione tra client e server.
	 * 
	 * @throws IOException            Eccezione di input e output
	 * @throws ClassNotFoundException Eccezione se non è stata trovata la classe
	 */
	private void comunica() throws IOException, ClassNotFoundException {
		String menu = "";

		do {
			caricaModello();
			System.out.println();

			while (eseguiPredizione()) {
				System.out.println("\u001B[34mNuova predizione sullo stesso modello KNN...\n\u001B[0m");
			}

			System.out.println();
			System.out.println("Vuoi ripetere la predizione con un \u001B[35mnuovo\u001B[0m modello KNN? (Y/N)");
			menu = Keyboard.readString();
			out.writeObject(menu);
		} while (menu.toLowerCase().equals("y"));

		System.out.print("\u001B[34mArrivederci!\u001B[0m");
	}

	/**
	 * Carica il modello KNN sul server, facendo scegliere all'utente le diverse
	 * modalità(testuale, binario e da database).
	 * 
	 * @throws IOException            Eccezione di input e output
	 * @throws ClassNotFoundException Eccezione se non è stata trovata la classe
	 */
	private void caricaModello() throws IOException, ClassNotFoundException {
		int decision = 0;
		String risposta = "";

		do {
			System.out.println("Scegli se caricare il training set da:");
			System.out.println("- file \u001B[34mtestuale\u001B[0m (1)");
			System.out.println("- file \u001B[34mbinario\u001B[0m (2)");
			System.out.println("- \u001B[34mdatabase\u001B[0m (3)");

			decision = Keyboard.readInt();
			if (decision < 0 || decision > 3)
				System.out.println("\u001B[31mScegliere tra 1, 2 e 3!\u001B[0m");

		} while (decision < 0 || decision > 3);

		// Invia al server la decisione
		out.writeObject(decision);

		String menu = "";
		switch (decision) {
			case 1:
				menu = "Inserisci il nome del file \u001B[34mtestuale\u001B[0m: ";
				break;

			case 2:
				menu = "Inserisci il nome del file \u001B[34mbinario\u001B[0m: ";
				break;

			case 3:
				menu = "Inserisci il nome della tabella nel \u001B[34mdatabase\u001B[0m: ";
				break;
		}

		do {
			String tableName = "";
			System.out.println(menu);
			tableName = Keyboard.readString();
			out.writeObject(tableName);

			risposta = (String) in.readObject();
			if (risposta.contains("@ERROR"))
				System.out.println("\u001B[31mElemento non trovato!\u001B[0m");

		} while (risposta.contains("@ERROR"));

		System.out.println("\u001B[32mModello KNN caricato con successo!\u001B[0m");
	}

	/**
	 * Esegue la predizione sul modello precedentemente caricato sul server.
	 * 
	 * @return true se si vuole rieseguire una nuova predizione, false altrimenti
	 * 
	 * @throws IOException            Eccezione di input e output
	 * @throws ClassNotFoundException Eccezione se non è stata trovata la classe
	 */
	private boolean eseguiPredizione() throws IOException, ClassNotFoundException {

		inserisciExample();
		System.out.println();
		inserisciK();

		System.out.println();
		System.out.println("Predizione: \u001B[32m" + in.readObject() + "\u001B[0m");

		System.out.println();
		System.out.println("Vuoi ripetere la predizione con lo \u001B[35mstesso\u001B[0m modello KNN? (Y/N)");
		String c = Keyboard.readString();
		out.writeObject(c);

		return c.toLowerCase().equals("y");
	}

	/**
	 * Inserisce l'esempio seguendo lo stesso schema del modello
	 * caricato sul server.
	 * 
	 * @throws IOException            Eccezione di input e output
	 * @throws ClassNotFoundException Eccezione se non è stata trovata la classe
	 */
	private void inserisciExample() throws IOException, ClassNotFoundException {
		String risposta = (String) (in.readObject());
		boolean hasAttribute = !risposta.contains("@ENDEXAMPLE");

		// Continua se ci sono altri attributi da leggere
		while (hasAttribute) {

			String msg = (String) (in.readObject());
			System.out.print(msg + " ");

			// Attributo discreto
			if (risposta.equals("@READSTRING")) {
				out.writeObject(Keyboard.readString());
			} else {
				// Attributo continuo
				double x = 0.0;
				do {
					x = Keyboard.readDouble();

					if (Double.valueOf(x).equals(Double.NaN))
						System.out.print("\u001B[31mInserire un numero valido: \u001B[0m");

				} while (Double.valueOf(x).equals(Double.NaN));

				out.writeObject(x);
			}

			risposta = (String) (in.readObject());
			hasAttribute = !risposta.contains("@ENDEXAMPLE");
		}
	}

	/**
	 * Gestisce l'inserimento del valore di K e lo invia al server.
	 * 
	 * @throws IOException            Eccezione di input e output
	 * @throws ClassNotFoundException Eccezione se non è stata trovata la classe
	 */
	private void inserisciK() throws IOException, ClassNotFoundException {

		String risposta = (String) (in.readObject());
		System.out.print(risposta);

		int k = 0;
		do {
			k = Keyboard.readInt();
			if (k < 1)
				System.out.print("\u001B[31mIl valore k dev'essere >= 1!\u001B[0m Reinserisci k:");
		} while (k < 1);

		out.writeObject(k);
	}

}
