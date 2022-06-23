package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

import database.Column;
import database.DbAccess;
import database.InsufficientColumnNumberException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;
import example.Example;
import utility.Keyboard;

/**
 * Classe che rappresenta l'intero TrainingSet modellando le variabili
 * dipendenti affichè si possa fare la predizione.
 */
public class Data implements Serializable {
	// list di example che indicano le variabili indipedenti del TrainingSet quindi
	// gli esempi
	private List<Example> data;
	// list di double che indica le variabili dipendenti
	private List<Double> target;
	// intero che indica il numero delle variabili dipendenti
	private int numberOfExamples;
	// list di attribute che indica le colonne delle variabili indipendenti del
	// TrainingSet
	private List<Attribute> explanatorySet;
	// attributo target
	private ContinuousAttribute classAttribute;

	/**
	 * Costruttore della classe Data
	 * Legge il file di testo e popola l'intero TrainingSet avvalorando
	 * sia le variabili dipendenti che le variabili indipendenti
	 * 
	 * @param fileName indica il nome del file di testo da cui prendere tutti i dati
	 * @throws TrainingDataException lancia un'eccezione nel caso in cui il training
	 *                               set dovesse
	 *                               essere acquisito in maniera errata
	 */
	public Data(String fileName) throws TrainingDataException {

		File inFile = new File(fileName);
		Scanner sc = null;

		try {
			sc = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			throw new TrainingDataException("File non trovato.");
		}

		String line = sc.nextLine();
		if (!line.contains("@schema")) {
			sc.close();
			throw new TrainingDataException("Errore nello schema.");
		}

		String s[] = line.split(" ");

		// popolare explanatory Set
		explanatorySet = new LinkedList<Attribute>();
		short iAttribute = 0;
		line = sc.nextLine();

		while (!line.contains("@data")) {
			s = line.split(" ");
			if (s[0].equals("@desc")) { // aggiungo l'attributo allo spazio descrittivo
				// @desc motor discrete
				if (s[2].equals("discrete"))
					explanatorySet.add(new DiscreteAttribute(s[1], iAttribute));
				else if (s[2].equals("continuous"))
					explanatorySet.add(new ContinuousAttribute(s[1], iAttribute));
				else {
					sc.close();
					throw new TrainingDataException("Formattazione errata del file.");
				}
			} else if (s[0].equals("@target"))
				classAttribute = new ContinuousAttribute(s[1], iAttribute);
			else {
				sc.close();
				throw new TrainingDataException("Formattazione errata del file.");
			}

			iAttribute++;
			try {
				line = sc.nextLine();
			} catch (NoSuchElementException e) {
				sc.close();
				throw new TrainingDataException("Formattazione errata del file.");
			}
		}

		if (classAttribute == null) {
			sc.close();
			throw new TrainingDataException("Target non presente nel Training Set.");
		}

		// avvalorare numero di esempi
		try {
			numberOfExamples = Integer.parseInt(line.split(" ")[1]);
		} catch (NumberFormatException e) {
			sc.close();
			throw new TrainingDataException("Numero di esempi non valido.");
		}

		// popolare data e target
		data = new LinkedList<Example>();
		target = new ArrayList<Double>();
		// ciclo che itera per tutte le righe dell'explanatorySet.
		for (short iRow = 0; iRow < numberOfExamples; iRow++) {
			Example e = new Example(explanatorySet.size());

			try {
				line = sc.nextLine();
			} catch (NoSuchElementException err) {
				sc.close();
				throw new TrainingDataException("Numero di esempi minore di " + numberOfExamples + ".");
			}

			s = line.split(","); // E,E,5,4, 0.28125095

			Double d;
			// Ciclo che serve per popolare l'example che poi verrà inserito in data dato
			// che è una list di example
			for (short jColumn = 0; jColumn < s.length - 1; jColumn++) {
				try {
					d = Double.valueOf(s[jColumn]);
					e.set(d, jColumn);

					((ContinuousAttribute) explanatorySet.get(jColumn)).setMin(d);
					((ContinuousAttribute) explanatorySet.get(jColumn)).setMax(d);

				} catch (NumberFormatException err) {
					e.set(s[jColumn], jColumn);
				}
			}

			data.add(e);
			target.add(Double.valueOf(s[s.length - 1]));
		}

		if (sc.hasNextLine()) {
			sc.close();
			throw new TrainingDataException("Numero di esempi maggiore di " + numberOfExamples + ".");
		}

		sc.close();

	}

	/**
	 * Costruttore della classe Data
	 * Legge i dati da database e popola l'intero TrainingSet avvalorando
	 * sia le variabili dipendenti che le variabili indipendenti
	 * 
	 * @param db        database di accesso
	 * @param tableName tabella da cui leggere il training set
	 * @throws TrainingDataException
	 * @throws InsufficientColumnNumberException
	 */
	public Data(DbAccess db, String tableName) throws TrainingDataException, InsufficientColumnNumberException {
		TableSchema tSchema;
		try {
			tSchema = new TableSchema(tableName, db);
		} catch (SQLException e) {
			throw new TrainingDataException("Errore nello schema:" + e.getMessage());
		} catch (InsufficientColumnNumberException e) {
			throw new InsufficientColumnNumberException("Numero di colonne insufficienti.");
		}

		explanatorySet = new LinkedList<Attribute>();

		Iterator<Column> it = tSchema.iterator();
		int i = 0;
		do {
			Column c = it.next();
			if (c.isNumber())
				explanatorySet.add(new ContinuousAttribute(c.getColumnName(), i));
			else
				explanatorySet.add(new DiscreteAttribute(c.getColumnName(), i));
			i++;
		} while (it.hasNext());

		classAttribute = new ContinuousAttribute(tSchema.getTarget().getColumnName(), tSchema.getNumberOfAttributes() - 1);

		TableData tData;
		try {
			tData = new TableData(db, tSchema);
		} catch (SQLException e) {
			throw new TrainingDataException("Errore di myslq:" + e.getMessage());
		}

		// popolare data e target
		data = tData.getExamples();

		numberOfExamples = data.size();

		target = new ArrayList<Double>();
		for (Object o : tData.getTargetValues()) {
			target.add((Double) o);
		}

		it = tSchema.iterator();
		for (int j = 0; j < explanatorySet.size(); j++) {
			Column c = it.next();
			if (explanatorySet.get(j) instanceof ContinuousAttribute) {
				try {
					((ContinuousAttribute) explanatorySet.get(j))
							.setMin((Double) tData.getAggregateColumnValue(c, QUERY_TYPE.MIN));
					((ContinuousAttribute) explanatorySet.get(j))
							.setMax((Double) tData.getAggregateColumnValue(c, QUERY_TYPE.MAX));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if (data.size() != target.size()) {
			throw new TrainingDataException("Numero di esempi diverso dal numero di target");
		}

	}

	/**
	 * Restituisce la lunghezza dell explanatorySet cioè le variabili indipendenti
	 * 
	 * @return lunghezza dell'explanatorySet
	 */
	public int getNumberOfExplanatoryAttributes() {
		return explanatorySet.size();
	}

	/**
	 * Scala l'esempio passato in input su tutto il training set
	 * 
	 * @param e esempio da scalare
	 * @return esempio passato in input scalato su tutto il training set
	 */
	private Example scaledExample(Example e) {
		Example ex = (Example) e.clone();

		for (int i = 0; i < explanatorySet.size(); i++) {
			if (explanatorySet.get(i) instanceof ContinuousAttribute) {
				ex.set(((ContinuousAttribute) explanatorySet.get(i)).scale((Double) ex.get(i)), i);
			}
		}

		return ex;
	}

	/**
	 * Metodo che serve per calcolare la media in base alla somma dei valori minori
	 * di un certo intero. Si utilizza la programmazione funzionale per popolare un
	 * TreeMap (in modo tale da avere tutte le distanze già ordinate) e per ogni 
	 * distanza che è una key, si aggiunge una lista di valori che rappresentano
	 * gli example presi da data e scalati.
	 * La programmazione funzionale viene utilizzata per prendere le prime k distanze
	 * e unire le liste di vei valori corrispondenti alle chiavi e farne la media.
	 * 
	 * @param e indica la lista di example che vogliamo passare in input
	 * @param k intero che indica la lunghezza della distanza da tenere in
	 *          considerazione per la predizione
	 * @return la media calcolata in base alla somma dei valori che siano più
	 *         piccoli della distanza su k
	 */
	public double avgClosest(Example e, int k) {
		// Crea una mappa ordinata raggruppando i target in base alla distanza 
		Map<Double, ArrayList<Double>> map = new TreeMap<Double, ArrayList<Double>>();
		Iterator<Double> iter = target.iterator();

		e = scaledExample(e);
		for (Example example : data) {
			example = scaledExample(example);
			Double dist = e.distance(example);
			
			if(!map.containsKey(dist))
				map.put(dist, new ArrayList<Double>());	

			map.get(dist).add(iter.next());
		}

		return map.values()
		.stream()
		.limit(k)																		// Filtra le prime K liste della treemap
		.flatMapToDouble(l -> l.stream().mapToDouble(d->d)) 							// Appiattisce le liste in un'unica lista
		.average().getAsDouble();														// Esegue la media dei valori
	}

	/**
	 * Restituisce i valori di data in una stringa
	 * 
	 * @return stringa contentente i valori di data
	 */
	public String toString() {

		String risultato = "";

		risultato += "Numero di esempi: " + numberOfExamples + "\n";

		risultato += "Esempi:\n";
		for (Example e : data) {
			risultato += e.toString();
		}
		risultato += "\n";

		risultato += "Variabili dipendenti:\n";
		for (Double d : target) {
			risultato += d + "\n";
		}
		risultato += "\n";

		risultato += "Variabili indipendenti:\n";
		for (Attribute a : explanatorySet) {
			risultato += a.toString() + "\n";
			;
		}
		risultato += "\n";

		risultato += "Attributo target:\n" + classAttribute.toString();

		return risultato;
	}

	/**
	 * Metodo che serve per fare inserire all'utente l'example su cui eseguire il
	 * knn e controllare se si tratta
	 * di un attributo continuo o discreto.
	 * 
	 * @return e l'example che è stato avvalorato con i dati inseriti dall'utente.
	 */
	public Example readExample() {
		Example e = new Example(getNumberOfExplanatoryAttributes());
		int i = 0;
		for (Attribute a : explanatorySet) {
			if (a instanceof DiscreteAttribute) {
				System.out.print("Inserisci valore discreto X[" + i + "]:");
				e.set(Keyboard.readString(), i);
			} else {
				double x = 0.0;
				do {
					System.out.print("Inserisci valore continuo X[" + i + "]:");
					x = Keyboard.readDouble();
				} while (Double.valueOf(x).equals(Double.NaN));

				e.set(x, i);
			}
			i++;
		}
		return e;
	}

	/**
	 * Metodo che serve per fare inserire all'utente(tramite socket) l'example su
	 * cui eseguire il knn e
	 * controllare se si tratta di un attributo continuo o discreto.
	 * 
	 * @param out canale di output del socket
	 * @param in  canale di input del socket
	 * @return e l'example che è stato avvalorato con i dati inseriti dall'utente
	 *         tramite socket.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws ClassCastException
	 */
	public Example readExample(ObjectOutputStream out, ObjectInputStream in)
			throws IOException, ClassNotFoundException, ClassCastException {
		Example e = new Example(getNumberOfExplanatoryAttributes());
		int i = 0;
		for (Attribute a : explanatorySet) {
			if (a instanceof DiscreteAttribute) {
				out.writeObject("@READSTRING");
				out.writeObject("Inserisci valore discreto X[" + i + "]:");
				e.set((String) (in.readObject()), i);
			} else {
				out.writeObject("@READFLOAT");
				out.writeObject("Inserisci valore continuo X[" + i + "]:");
				e.set((Double) (in.readObject()), i);
			}
			i++;
		}
		out.writeObject("@ENDEXAMPLE");

		return e;
	}

}
