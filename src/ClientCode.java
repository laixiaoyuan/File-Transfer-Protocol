import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientCode {

    public static int portNo = 3333;
    public static void main(String[] args) throws IOException {

        // if it is text file, set the client arguments as /Users/Lexie/Desktop/test.txt test.txt 0.0.0.0 3333
        // if it is binary file (say a .png image, set the client arguments as /Users/Lexie/Desktop/test.png test.png 0.0.0.0 3333

        InetAddress addr = InetAddress.getByName("localhost");
        portNo = Integer.parseInt(args[3]);
        String targetAddress = args[2];
        String outputFileName = args[1];
        String inputFileName = args[0];
        System.out.println("Request Connection.");

        Socket socket = new Socket(targetAddress, portNo);
        try {
            System.out.println("socket = " + socket);

            InputStream inputStream = new FileInputStream(inputFileName);
            OutputStream outputStream = socket.getOutputStream();

            // send the file name
            DataOutputStream dos = new DataOutputStream(outputStream);
            dos.writeUTF(outputFileName);
            dos.flush();

            // send the file content in chunks of 10 bytes.
            byte[] buffer = new byte[10];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }

            dos.close();
            outputStream.close();
            inputStream.close();
        }

        finally {
            System.out.println("close the Client socket and the io.");
            socket.close();
        }
    }
}
