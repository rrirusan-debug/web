import java.io.*;
import java.net.*;

class SMTPClient {
    public static void main(String[] args) {
        String mailServer = "localhost"; // or "smtp.gmail.com" for a real server
        int port = 25; // Port 25 (default), or 587 for TLS servers

        try {
            // Connect to SMTP server
            Socket socket = new Socket(mailServer, port);
            System.out.println("Connected to SMTP server.");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            // Read initial server response
            System.out.println("S: " + in.readLine());

            // HELO command
            out.write("HELO example.com\r\n");
            out.flush();
            System.out.println("C: HELO example.com");
            System.out.println("S: " + in.readLine());

            // MAIL FROM command
            out.write("MAIL FROM:<sender@example.com>\r\n");
            out.flush();
            System.out.println("C: MAIL FROM:<sender@example.com>");
            System.out.println("S: " + in.readLine());

            // RCPT TO command
            out.write("RCPT TO:<receiver@example.com>\r\n");
            out.flush();
            System.out.println("C: RCPT TO:<receiver@example.com>");
            System.out.println("S: " + in.readLine());

            // DATA command
            out.write("DATA\r\n");
            out.flush();
            System.out.println("C: DATA");
            System.out.println("S: " + in.readLine());

            // Message content
            out.write("Subject: Test Mail\r\n");
            out.write("From: sender@example.com\r\n");
            out.write("To: receiver@example.com\r\n");
            out.write("\r\n");
            out.write("This is a test email sent using a simple SMTP client.\r\n");
            out.write(".\r\n"); // End of mail content
            out.flush();
            System.out.println("C: <mail content>");
            System.out.println("S: " + in.readLine());

            // QUIT command
            out.write("QUIT\r\n");
            out.flush();
            System.out.println("C: QUIT");
            System.out.println("S: " + in.readLine());

            socket.close();
            System.out.println("Disconnected from SMTP server.");

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
