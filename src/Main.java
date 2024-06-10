import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Port port = new Port(10, 100);

        for (int i = 0; i < 30; i++) {
            new Ship(i, port, random.nextInt(50, 100), random.nextBoolean(), 100);

        }
    }
}