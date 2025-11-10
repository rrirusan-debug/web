import java.util.*;

class FramingMethodsSimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter data: ");
        String data = sc.nextLine();

        // Character Count
        String charCountFrame = (data.length() + 1) + data;
        System.out.println("\nCharacter Count Method: " + charCountFrame);

        // Character Stuffing
        String flag = "F";
        String esc = "E";
        String stuffed = flag;
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (ch == 'F' || ch == 'E')
                stuffed += "E";
            stuffed += ch;
        }
        stuffed += flag;
        System.out.println("Character Stuffing Method: " + stuffed);

        // Bit Stuffing
        String bitFlag = "01111110";
        String bitStuffed = bitFlag;
        int count = 0;
        for (int i = 0; i < data.length(); i++) {
            char bit = data.charAt(i);
            bitStuffed += bit;
            if (bit == '1') count++;
            else count = 0;
            if (count == 5) {
                bitStuffed += '0';
                count = 0;
            }
        }
        bitStuffed += bitFlag;
        System.out.println("Bit Stuffing Method: " + bitStuffed);

        sc.close();
    }
}
