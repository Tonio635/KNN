package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

import utility.Keyboard;


/**
 * Classe che rappresenta l'intero TrainingSet modellando le variabili dipendenti 
 * affichè si possa fare la predizione.
 */
public class Data implements Serializable{
	// array di example che indicano le variabili indipedenti del TrainingSet quindi gli esempi
	private List<Example> data;
	// array di double che indica le variabili dipendenti
	private List<Double> target;
	// intero che indica il numero delle variabili dipendenti
	private int numberOfExamples;
	// array di attribute che indica le variabili indipendenti del TrainingSet
	private List<Attribute> explanatorySet;
	// attributo target
	private ContinuousAttribute classAttribute;
	
	/**
	 * Costruttore della classe Data
	 * Legge il file di testo e popola l'intero TrainingSet avvalorando
	 * sia le variabili dipendenti che le variabili indipendenti
	 * 
	 * @param fileName indica il nome del file di testo da cui prendere tutti i dati
	 * @throws TrainingDataException lancia un'eccezione nel caso in cui il training set dovesse
	 * essere acquisito in maniera errata
	 */
	public Data(String fileName)throws TrainingDataException{
		
		File inFile = new File (fileName);
		Scanner sc = null;

		try{
			sc = new Scanner (inFile);
		}
		catch(FileNotFoundException e) {
			throw new TrainingDataException("File non trovato.");
		}

		String line = sc.nextLine();
	    if(!line.contains("@schema")){
			sc.close();
			throw new TrainingDataException("Errore nello schema.");
		}
	    
	    String s[] = line.split(" ");

		//popolare explanatory Set
		explanatorySet = new LinkedList<Attribute>();
		short iAttribute = 0;
	    line = sc.nextLine();

	    while(!line.contains("@data")){
	    	s = line.split(" ");
	    	if(s[0].equals("@desc")) { // aggiungo l'attributo allo spazio descrittivo
		   		//@desc motor discrete
				if(s[2].equals("discrete"))
		   			explanatorySet.add(new DiscreteAttribute(s[1], iAttribute));
				else if(s[2].equals("continuous"))
					explanatorySet.add(new ContinuousAttribute(s[1], iAttribute));
		   	}
	      	else if(s[0].equals("@target"))
	    		classAttribute = new ContinuousAttribute(s[1], iAttribute);
			else{
				sc.close();
				throw new TrainingDataException("Formattazione errata del file.");
			}
	    	
	      	iAttribute++;
			try{
				line = sc.nextLine();
			}
			catch(NoSuchElementException e){
				sc.close();
				throw new TrainingDataException("Formattazione errata del file.");
			}	
	    }

		if(classAttribute == null){
			sc.close();
			throw new TrainingDataException("Target non presente nel Training Set.");
		}
		      
		//avvalorare numero di esempi
	    try{
			numberOfExamples = new Integer(line.split(" ")[1]);
		}
		catch(NumberFormatException e){
			sc.close();
			throw new TrainingDataException("Numero di esempi non valido.");
		}
	    
	    //popolare data e target
	    data = new LinkedList<Example>();
		target = new ArrayList<Double>();

		for(short iRow = 0; iRow < numberOfExamples; iRow++){
			Example e = new Example(explanatorySet.size());

			try {
				line = sc.nextLine();
			} catch (NoSuchElementException err) {
				sc.close();
				throw new TrainingDataException("Numero di esempi minore di " + numberOfExamples + ".");
			}

			s = line.split(","); // E,E,5,4, 0.28125095

			Double d;

			for (short jColumn = 0; jColumn < s.length - 1; jColumn++){
				try{
					d = Double.valueOf(s[jColumn]);
					e.set(d, jColumn);

					((ContinuousAttribute) explanatorySet.get(jColumn)).setMin(d);
					((ContinuousAttribute) explanatorySet.get(jColumn)).setMax(d);
					
				} catch(NumberFormatException err){
					e.set(s[jColumn], jColumn);
				}
			}
			
			data.add(e);
			target.add(new Double(s[s.length - 1]));
		}
		
		if(sc.hasNextLine()){
			sc.close();
			throw new TrainingDataException("Numero di esempi maggiore di " + numberOfExamples + ".");
		}

	  	sc.close();

	}

	/**
	 * Restituisce la lunghezza dell explanatorySet cioè le variabili indipendenti
	 * 
	 * @return lunghezza dell'explanatorySet
	 */
	public int getNumberOfExplanatoryAttributes(){
		return explanatorySet.size();
	}
	
	/*
	 * Partiziona data rispetto all'elemento x di key e restituisce il punto di separazione
	 */
	private int partition(List<Double> key, int inf, int sup) throws ExampleSizeException{
		int i, j;
	
		i = inf;
		j = sup;
		int	med = (inf + sup) / 2;
		
		Double x = key.get(med);
		
		data.get(inf).swap(data.get(med));

		double temp = target.get(inf);
		target.set(inf, target.get(med));
		target.set(med, temp);
		
		temp = key.get(inf);
		key.set(inf, key.get(med));
		key.set(med, temp);
		
		while(true){

			while(i <= sup && key.get(i) <= x){
				i++;
			}

			while(key.get(j) > x) {
				j--;
			
			}
			
			if(i < j) {
				data.get(i).swap(data.get(j));
				
				temp = target.get(i);
				target.set(i, target.get(j));
				target.set(j, temp);
				
				temp = key.get(i);
				key.set(i, key.get(j));
				key.set(j, temp);
				
			}
			else break;
		}
		data.get(inf).swap(data.get(j));
		
		temp = target.get(inf);
		target.set(inf, target.get(j));
		target.set(j, temp);
		
		temp = key.get(inf);
		key.set(inf, key.get(j));
		key.set(j, temp);
		
		return j;
	}
	
	/**
	 * Algoritmo quicksort per l'ordinamento di data 
	 * usando come relazione d'ordine totale "<=" definita su key
	 * 
	 * @param key[], inf, sup
	 */
	private void quicksort(List<Double> key, int inf, int sup) throws ExampleSizeException{
		
		if(sup >= inf){
			
			int pos;
			pos = partition(key, inf, sup);
					
			if ((pos - inf) < (sup - pos + 1)) {
				quicksort(key, inf, pos - 1);
				quicksort(key, pos + 1,sup);
			} else{
				quicksort(key, pos + 1, sup);
				quicksort(key, inf, pos - 1);
			}
			
		}
		
	}

	Example scaledExample(Example e){
		
		for(int i = 0; i < explanatorySet.size(); i++){
			if(explanatorySet.get(i) instanceof ContinuousAttribute){
				e.set(((ContinuousAttribute) explanatorySet.get(i)).scale((Double) e.get(i)), i);
			}
		}

		return e;
	}

	/**
	 * Metodo che:
	 * 1) Avvalora key con le distanze calcolate tra ciascuna istanza di Example memorizzata in data
	 *	ed e usando il metodo distance di Example
	 * 2) ordina data, target e key in accordo ai valori contenuti in key usando il metodo quicksort
	 * 3) identifica gli esempi di data che sono associati alle k distanze più piccole in key
	 * 4) calcola la media dei valori memorizzati in target in corrispondenza degli esempi associati
	 * 	 alle k distanze più piccole in key
	 * 
	 * @param e indica l'array di example che vogliamo passare in input
	 * @param k intero che indica la lunghezza della distanza da tenere in considerazione per la predizione
	 * @return la media calcolata in base alla somma dei valori che siano più piccoli della distanza su k
	 */
	public double avgClosest(Example e, int k){

		LinkedList<Double> key = new LinkedList<Double>();
		double somma;
		int i, j;
		Iterator<Double> iterKey = key.iterator();

		LinkedList<Example> elements = new LinkedList<Example>();
		
		for(Example example : data){
			elements.add(example.clone());
		}

		for(i = 0; i < elements.size(); i++){
			elements.set(i, scaledExample(elements.get(i)));
		}

		e = scaledExample(e);

		for(Example example : elements){
			key.add(example.distance(e));
		}
		
		quicksort(key, 0, numberOfExamples - 1);

		System.out.println("distances:" + key);

		i = 0;
		j = 0;
		somma = 0;
		ListIterator<Double> iter3 = target.listIterator();
		iterKey = key.iterator();
		Double elemprec = iterKey.next();	
		Double elemsucc = 0.0;

		while(iter3.hasNext() && j < k){
			
			Double elem = iter3.next();
			somma += elem;

			if (i != numberOfExamples - 1){
				elemsucc = iterKey.next();
				if(!elemprec.equals(elemsucc))
					j++;
			}
			i++;
			elemprec = elemsucc;
    	}
		
		return somma / i;
	}

	/**
	 * Restituisce i valori di data
	 * 
	 * @return stringa contentente i valori di data
	 */
	public String toString(){
		
		String risultato = "";

		risultato += "Numero di esempi: " + numberOfExamples + "\n";

		risultato += "Esempi:\n";
		for (Example e : data){
			risultato += e.toString();
		}
		risultato += "\n";

		risultato += "Variabili dipendenti:\n";
		for(Double d : target){
			risultato += d + "\n";
		}
		risultato += "\n";

		risultato += "Variabili indipendenti:\n";
		for(Attribute a : explanatorySet){
			risultato += a.toString() + "\n";;
		}
		risultato += "\n";

		risultato += "Attributo target:\n" + classAttribute.toString();

		return risultato;
	}

	public Example readExample() {
		Example e =new Example(getNumberOfExplanatoryAttributes());
		int i=0;
		for(Attribute a:explanatorySet){
			if(a instanceof DiscreteAttribute) {
				System.out.print("Inserisci valore discreto X["+i+"]:");
				e.set(Keyboard.readString(),i);
			}
			else {
				double x=0.0;
				do{
					System.out.print("Inserisci valore continuo X["+i+"]:");
					x=Keyboard.readDouble();
				} while(new Double(x).equals(Double.NaN));

				e.set(x,i);
			}
			i++;
		}
		return e;
	}
		

	public static void main(String args[])throws TrainingDataException{
		Data trainingSet = new Data("src/servo.dat");
		System.out.println(trainingSet);

		double[] key = new double[5]; 
	}

}
