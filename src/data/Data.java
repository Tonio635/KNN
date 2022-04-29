package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Classe che rappresenta l'intero TrainingSet modellando le variabili dipendenti 
 * affichè si possa fare la predizione.
 */
public class Data {
	// array di example che indicano le variabili indipedenti del TrainingSet quindi gli esempi
	private Example[] data;
	// array di double che indica le variabili dipendenti
	private Double[] target;
	// intero che indica il numero di example
	private int numberOfExamples;
	// array di attribute che indica le variabili indipendenti del TrainingSet
	private Attribute[] explanatorySet;
	// attributo target
	private ContinuousAttribute classAttribute;
	
	/**
	 * Costruttore della classe Data
	 * Legge il file di testo e popola l'intero TrainingSet avvalorando
	 * sia le variabili dipendenti che le variabili indipendenti
	 * 
	 * @param fileName indica il nome del file di testo da cui prendere tutti i dati
	 * @throws FileNotFoundException lancia un eccezione se il file non dovesse essere presente
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
	  	
		explanatorySet = new Attribute[new Integer(s[1])];
		short iAttribute = 0;
	    line = sc.nextLine();

	    while(!line.contains("@data")){
	    	s = line.split(" ");
	    	if(s[0].equals("@desc")) { // aggiungo l'attributo allo spazio descrittivo
		   		//@desc motor discrete
		   		explanatorySet[iAttribute] = new DiscreteAttribute(s[1], iAttribute);
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
	    data = new Example[numberOfExamples];
	    target = new Double[numberOfExamples];

		for(short iRow = 0; iRow < numberOfExamples; iRow++){
			Example e = new Example(explanatorySet.length);
			try {
				line = sc.nextLine();
			} catch (NoSuchElementException err) {
				sc.close();
				throw new TrainingDataException("Numero di esempi minore di " + numberOfExamples + ".");
			}

			// assumo che attributi siano tutti discreti
			// ! DA RIVEDERE
			s = line.split(","); // E,E,5,4, 0.28125095
			for (short jColumn = 0; jColumn < s.length - 1; jColumn++)
				e.set(s[jColumn], jColumn);

			data[iRow] = e;
			target[iRow] = new Double(s[s.length - 1]);
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
		return explanatorySet.length;
	}
	
	/*
	 * Partiziona data rispetto all'elemento x di key e restituisce il punto di separazione
	 */
	private int partition(double key[], int inf, int sup){
		int i, j;
	
		i = inf;
		j = sup;
		int	med = (inf + sup) / 2;
		
		Double x = key[med];
		
		data[inf].swap(data[med]);
		
		double temp = target[inf];
		target[inf] = target[med];
		target[med] = temp;
		
		temp = key[inf];
		key[inf] = key[med];
		key[med] = temp;
		
		while(true){

			while(i <= sup && key[i] <= x){
				i++;
			}

			while(key[j] > x) {
				j--;
			
			}
			
			if(i < j) {
				data[i].swap(data[j]);
				temp = target[i];
				target[i] = target[j];
				target[j] = temp;
				
				temp = key[i];
				key[i] = key[j];
				key[j] = temp;
				
			}
			else break;
		}
		data[inf].swap(data[j]);
		
		temp = target[inf];
		target[inf] = target[j];
		target[j] = temp;
		
		temp = key[inf];
		key[inf] = key[j];
		key[j] = temp;
		
		return j;
	}
	
	/*
	 * Algoritmo quicksort per l'ordinamento di data 
	 * usando come relazione d'ordine totale "<=" definita su key
	 * @param key[], inf, sup
	 */
	private void quicksort(double key[], int inf, int sup){
		
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
		double[] key = new double[numberOfExamples];
		double somma;
		int i, j;

		//try{
		for(i = 0; i < numberOfExamples; i++){
			key[i] = data[i].distance(e);
		}
		//}catch(ExampleSizeException err){
			//System.out.print(err.getMessage());
			//return 0;
		//}

		quicksort(key, 0, numberOfExamples - 1);

		i = 0;
		j = 0;
		somma = 0;

    	while (i < numberOfExamples && j < k) {
			somma += target[i];

			if (i != numberOfExamples - 1 && key[i] != key[i + 1])
				j++;
			i++;
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
		for (int i = 0; i < numberOfExamples; i++){
			risultato += data[i].toString();
		}
		risultato += "\n";

		risultato += "Variabili dipendenti:\n";
		for (int i = 0; i < numberOfExamples; i++){
			risultato += target[i] + "\n";
		}
		risultato += "\n";

		risultato += "Variabili indipendenti:\n";
		for (int i = 0; i < explanatorySet.length; i++){
			risultato += explanatorySet[i].toString();
		}
		risultato += "\n";

		risultato += "Attributo target:\n" + classAttribute.toString();

		return risultato;
	}

	public static void main(String args[])throws TrainingDataException{
		Data trainingSet = new Data("src/servo.dat");
		System.out.println(trainingSet);

		double[] key = new double[5]; 
	}

}
