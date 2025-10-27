import java.util.Random;
import java.util.Scanner;

class StopAndWait {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Enter number of frames to send: ");
        int n = sc.nextInt();

        int i = 1;
        while (i <= n) {
            System.out.println("\nSender: Sending frame " + i);

            boolean frameLost = rand.nextInt(10) < 2;
            boolean ackLost = rand.nextInt(10) < 2;

            if (frameLost) {
                System.out.println("Receiver: Frame " + i + " lost!");
                System.out.println("Sender: Timeout! Resending frame " + i);
                continue;
            } else {
                System.out.println("Receiver: Frame " + i + " received successfully.");
            }

            if (ackLost) {
                System.out.println("Sender: ACK lost! Timeout, resending frame " + i);
                continue;
            } else {
                System.out.println("Sender: ACK for frame " + i + " received.");
                i++;
            }
        }

        System.out.println("\nAll frames sent successfully!");
        sc.close();
    }
}
