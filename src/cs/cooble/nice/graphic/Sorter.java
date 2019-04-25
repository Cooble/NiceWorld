package cs.cooble.nice.graphic;

import java.util.function.Supplier;

/**
 * Created by Matej on 24.2.2018.
 * Quick sort
 */
public class Sorter {

    private static Supplier<Integer> array[];
    private static int length;

    public static void sort(Supplier<Integer>[] inputArr) {
        Sorter.array = inputArr;
        length = inputArr.length;
        quickSort(0, length - 1);
    }
    public static void invert(Supplier<Integer>[] array){
        Supplier[] inner = new Supplier[array.length];
        int in=0;
        for (int i = array.length - 1; i >= 0; i--) {
            inner[in]=array[i];
            in++;
        }
        System.arraycopy(inner, 0, array, 0, inner.length);
    }

    private static void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Supplier<Integer> pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a number
             * from right side which is less then the pivot value. Once the search
             * is done, then we exchange both numbers.
             */
            while (array[i].get().intValue() < pivot.get().intValue()) {
                i++;
            }
            while (array[j].get().intValue() > pivot.get().intValue()) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

    private static void exchangeNumbers(int i, int j) {
        Supplier temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

