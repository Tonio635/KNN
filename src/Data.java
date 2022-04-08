import org.w3c.dom.Attr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



class Data {
	
	private Example[] data;
	private Double[] target;
	private int numberOfExamples;
	private Attribute[] explanatorySet;
	private ContinuousAttribute classAttribute;
	
	Data(String fileName)throws FileNotFoundException{
		
		File inFile = new File (fileName);

		Scanner sc = new Scanner (inFile);
	    String line = sc.nextLine();
	    if(!line.contains("@schema"))
	    	throw new RuntimeException("Errore nello schema");
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
	    		  
	      	iAttribute++;
	      	line = sc.nextLine();
	    }
		      
		//avvalorare numero di esempi
	    numberOfExamples = new Integer(line.split(" ")[1]);
	      
	    //popolare data e target
	    data = new Example[numberOfExamples];
	    target = new Double[numberOfExamples];

	    short iRow = 0;
	    while (sc.hasNextLine()){
	    	Example e = new Example(explanatorySet.length);
	    	line = sc.nextLine();
	    	// assumo che attributi siano tutti discreti
	    	s = line.split(","); //E,E,5,4, 0.28125095
	    	for(short jColumn = 0; jColumn < s.length - 1; jColumn++)
	    		e.set(s[jColumn], jColumn);
			
	    	data[iRow] = e;
	    	target[iRow] = new Double(s[s.length-1]);
	    	iRow++;
	    }
	  	sc.close();

	}

	int getNumberOfExplanatoryAttributes(){
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

	double avgClosest(Example e, int k){
		double[] key = new double[numberOfExamples];
		double somma;
		int i, j;
		boolean stop = false;

		for(i = 0; i < numberOfExamples; i++){
			key[i] = data[i].distance(e);
		}

		quicksort(key, 0, numberOfExamples);

		i = 1;
		j = 0;
		somma = target[0];

		while(i < numberOfExamples && !stop){
			if(key[i] != key[i - 1])
				j++;
			if(j < k)
				somma += target[i];
			else
				stop = true;
			i++;
		}

		return somma / i;
	}

	public static void main(String args[])throws FileNotFoundException{
		Data trainingSet = new Data("src\\servo.dat");
		System.out.println(trainingSet);

		double[] key = new double[5]; 
	}

}
