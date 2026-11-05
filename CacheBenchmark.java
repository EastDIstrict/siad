import java.util.Random;

public class CacheBenchmark {

    static long sink = 0;

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

            double smallTime = 0;
            for (int r = 0; r < 10; r++) {

                for (int w = 0; w < 10; w++) {
                    long sum = 0;
                    for (int i = 0; i < smallArray.length; i = i + step) {
                        sum = sum + smallArray[i];
                    }
                    sink = sum;
                }

                long start = System.nanoTime();
                long sum = 0;
                for (int i = 0; i < smallArray.length; i = i + step) {
                    sum = sum + smallArray[i];
                }
                sink = sum;
                long end = System.nanoTime();

                smallTime = smallTime + (end - start);
            }

            int countSmall = (smallArray.length + step - 1) / step;
            smallTime = smallTime / 5 / countSmall;



            double largeTime = 0;
            for (int r = 0; r < 10; r++) {
                for (int w = 0; w < 10; w++) {
                    long sum = 0;
                    for (int i = 0; i < largeArray.length; i = i + step) {
                        sum = sum + largeArray[i];
                    }
                    sink = sum;
                }
                long start = System.nanoTime();
                long sum = 0;
                for (int i = 0; i < largeArray.length; i = i + step) {
                    sum = sum + largeArray[i];
                }
                sink = sum;
                long end = System.nanoTime();
                largeTime = largeTime + (end - start);
            }
            int countLarge = (largeArray.length + step - 1) / step;
            largeTime = largeTime / 5 / countLarge;

            System.out.println(step + ", " + smallTime + ", " + largeTime);
        }
    }
}