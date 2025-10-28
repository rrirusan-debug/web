import java.util.Scanner;

public class SimpleFraming {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Character Count");
        System.out.println("2. Character Stuffing");
        System.out.println("3. Bit Stuffing");
        System.out.print("Enter your choice: ");
        int ch = sc.nextInt();
        sc.nextLine(); // clear newline

        switch (ch) {
            case 1:
                System.out.print("Enter data: ");
                String data1 = sc.nextLine();
                int count = data1.length() + 1;
                System.out.println("Framed data: " + count + data1);
                break;

            case 2:
                System.out.print("Enter data: ");
                String data2 = sc.nextLine();
                data2 = data2.replace("DLE", "DLEDLE"); // stuffing DLE
                System.out.println("Framed data: DLESTX" + data2 + "DLEETX");
                break;

            case 3:
                System.out.print("Enter binary data: ");
                String bits = sc.nextLine();
                String result = "";
                int c = 0;
                for (int i = 0; i < bits.length(); i++) {
                    char b = bits.charAt(i);
                    result += b;
                    if (b == '1') c++; else c = 0;
                    if (c == 5) {
                        result += '0'; // add stuffed bit
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