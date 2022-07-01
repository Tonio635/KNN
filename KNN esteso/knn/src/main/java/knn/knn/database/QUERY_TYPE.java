package knn.knn.database;

/**
 * Enumerazione utilizzata per estrarre dal database
 * i valori di minimo e massimo delle variabili indipendenti continue.
 */
public enum QUERY_TYPE {
    /**
     * Costante usata per estrerre il valore di minimo da ciascuna colonna delle
     * variabili indipendenti nel database
     */
    MIN,
    /**
     * Costante usata per estrerre il valore di massimo da ciascuna colonna delle
     * variabili indipendenti nel database
     */
    MAX
}
