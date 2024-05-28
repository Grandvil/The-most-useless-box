import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final int USER_ITERATIONS = 5;
    private static final int USER_SLEEP_MS = 1000;

    private static AtomicBoolean isOn = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        Thread userThread = new Thread(() -> {
            for (int i = 0; i < USER_ITERATIONS; i++) {
                isOn.set(true);
                System.out.println("Пользователь включил тумблер");
                try {
                    Thread.sleep(USER_SLEEP_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread toyThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (isOn.get()) {
                    isOn.set(false);
                    System.out.println("Игрушка выключила тумблер");
                }
            }
        });

        userThread.start();
        toyThread.start();

        userThread.join();
        toyThread.interrupt();
    }
}
