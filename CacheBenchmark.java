import java.util.Random;
import java.util.Arrays;

public class CacheBenchmark {


    static long total = 0;

    public static void main(String[] args) {

        int smallSize = 16 * 1024 / 4;
        int largeSize = 64 * 1024 * 1024 / 4;

        int[] smallArray = new int[smallSize];
        int[] largeArray = new int[largeSize];

        Random random = new Random();
        for (int i = 0; i < smallSize; i++) {
            smallArray[i] = random.nextInt();
        }
        for (int i = 0; i < largeSize; i++) {
            largeArray[i] = random.nextInt();
        }

        System.out.println("Step, Small_Time, Large_Time");

        for (int step = 1; step <= 128; step = step * 2) {

            for (int w = 0; w < 100; w++) {
                long sum = 0;
                for (int i = 0; i < smallArray.length; i = i + step) {
                    sum = sum + smallArray[i];
                }
                total = sum;
            }

            long[] smallTimes = new long[11];

            for (int r = 0; r < 11; r++) {
                long start = System.nanoTime();
                long sum = 0;
                for (int i = 0; i < smallArray.length; i = i + step) {
                    sum = sum + smallArray[i];
                }
                total = sum;
                long end = System.nanoTime();

                smallTimes[r] = end - start;
            }

            Arrays.sort(smallTimes);
            long medianSmall = smallTimes[5];

            int countSmall = (smallArray.length + step - 1) / step;
            double smallTime = (double) medianSmall / countSmall;

            for (int w = 0; w < 100; w++) {
                long sum = 0;
                for (int i = 0; i < largeArray.length; i = i + step) {
                    sum = sum + largeArray[i];
                }
                total = sum;
            }

            long[] largeTimes = new long[11];

            for (int r = 0; r < 11; r++) {
                long start = System.nanoTime();
                long sum = 0;
                for (int i = 0; i < largeArray.length; i = i + step) {
                    sum = sum + largeArray[i];
                }
                total = sum;
                long end = System.nanoTime();

                largeTimes[r] = end - start;
            }

            Arrays.sort(largeTimes);
            long medianLarge = largeTimes[5];

            int countLarge = (largeArray.length + step - 1) / step;
            double largeTime = (double) medianLarge / countLarge;

            System.out.printf("%d, %.3f, %.3f\n", step, smallTime, largeTime);
        }
    }
}
