package Server_Client;
import Encodings_and_Decodings.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class ClassServer {
    public static void main(String[] args) throws IOException {

        Socket socket = null;
        InputStreamReader inread = null;
        OutputStreamWriter outWrite = null;
        BufferedReader buffr = null;
        BufferedWriter buffw = null;
        ServerSocket serversocket = null;

        System.out.println("Waiting for clients...");
        serversocket = new ServerSocket(5006);

        while(true){
            try{
                socket = serversocket.accept();
                System.out.println("Connected...\n\n");

                outWrite = new OutputStreamWriter(socket.getOutputStream());
                inread = new InputStreamReader(socket.getInputStream());

                buffr = new BufferedReader(inread);
                buffw = new BufferedWriter(outWrite);

                Scanner s = new Scanner(System.in);
                CeaserCipher cs = new CeaserCipher();
                while(true){

                    System.out.print("Server: ");
                    String msg_to_send = cs.ceaserEncoding(s.nextLine(),3);
                    buffw.write(msg_to_send);
                    buffw.newLine();
                    buffw.flush();

                    String msg_recived = buffr.readLine();
                    System.out.println("Form Client (Encrypted): "+msg_recived);
                    System.out.println("From Client (Decrypted): "+cs.ceaserDecoding(msg_recived,3));

                    buffw.flush();

                    if(msg_recived.equalsIgnoreCase("BYE"))
                        break;
                    System.out.println();
                }
                socket.close();
                inread.close();
                outWrite.close();
                buffr.close();
                buffw.close();
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
