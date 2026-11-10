import sort.*;

import java.util.Random;

public class SortingDemo {

    public static void main(String[] args) {

        Random rng = new Random();
        Integer[] values = new Integer[50];

        for(int i = 0; i < values.length; i++){
            values[i] = rng.nextInt(100);
        }

        for (int value : values) {
            System.out.print(value + " ");
        }
        System.out.println();

        Sorter<Integer> sorter = new NaturalMergeSort<>();
        sorter.sort(values);

        for (int value : values) {
            System.out.print(value + " ");
        }
    }
}