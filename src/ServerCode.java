import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCode {
    public static int portNo = 3333;
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(portNo);
        System.out.println("The Server is start:" + s);
        Socket socket = s.accept();
        System.out.println("Accept the Client: " + socket);

        try {
            InputStream inputStream = socket.getInputStream();

            // receive filename and create the file with the filename
            DataInputStream clientData = new DataInputStream(inputStream);

            String fileName = clientData.readUTF();
            OutputStream outputStream = new FileOutputStream("/Users/Lexie/Documents/" + fileName);

            // receive and write file content in chunks of 5 bytes
            byte[] buffer = new byte[5];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }

            clientData.close();
            outputStream.close();
            inputStream.close();
        }

        finally {
            System.out.println("close the Server socket and the io.");
            socket.close();
            s.close();
        }
    }
}
