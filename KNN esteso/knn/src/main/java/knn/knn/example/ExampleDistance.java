package knn.knn.example;

import java.util.Comparator;

public class ExampleDistance implements Comparator<Example>{

    private static Example e;

    ExampleDistance(Example ex){
        e = ex;
    }

    @Override
    public int compare(Example x1, Example x2) {
        Double dist = e.distance(x1);
        Double dist2 = e.distance(x2);
        return dist.compareTo(dist2);
    }
    
}
