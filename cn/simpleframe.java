import java.util.Scanner;

public class SimpleFraming {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Character Count");
        System.out.println("2. Character Stuffing");
        System.out.println("3. Bit Stuffing");
        System.out.print("Enter your choice: ");

        int ch = sc.nextInt();
        sc.nextLine(); // clear the newline character after nextInt()

        switch (ch) {
            case 1:
                System.out.print("Enter data: ");
                String data1 = sc.nextLine();
                int count = data1.length() + 1; // +1 for count byte
                System.out.println("Framed data: " + count + data1);
                break;

            case 2:
                System.out.print("Enter data: ");
                String data2 = sc.nextLine();
                // Replace every DLE with DLEDLE (Character Stuffing)
                data2 = data2.replace("DLE", "DLEDLE");
                System.out.println("Framed data: DLESTX" + data2 + "DLEETX");
                break;

            case 3:
                System.out.print("Enter binary data: ");
                String bits = sc.nextLine();
                StringBuilder result = new StringBuilder();
                int c = 0; // consecutive 1's counter

                for (int i = 0; i < bits.length(); i++) {
                    char b = bits.charAt(i);
                    result.append(b);
                    if (b == '1') {
                        c++;
                        if (c == 5) {
                            result.append('0'); // stuff a 0
                            c = 0;
                        }
                    } else {
                        c = 0;
                    }
                }

                System.out.println("Framed data: " + result);
                break;

            default:
                System.out.println("Invalid choice!");
        }

        sc.close();
    }
}
