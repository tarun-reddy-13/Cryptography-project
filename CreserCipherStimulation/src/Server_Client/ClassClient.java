package Server_Client;
import Encodings_and_Decodings.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class ClassClient {
    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader inreader = null;
        OutputStreamWriter outwriter = null;
        BufferedReader buffr = null;
        BufferedWriter buffw = null;

        try{
            socket = new Socket("localhost", 5006);

            inreader = new InputStreamReader(socket.getInputStream());
            outwriter = new OutputStreamWriter(socket.getOutputStream());

            buffr = new BufferedReader(inreader);
            buffw = new BufferedWriter(outwriter);
            System.out.println("Connected...");
            
            Scanner s = new Scanner(System.in);
            CeaserCipher cs = new CeaserCipher();
            
            while (true){
            	
            	String ServerMessage = buffr.readLine();
            	System.out.println("From Server (Encrypted): "+ServerMessage);
            	System.out.println("From Server (Decrypted): "+cs.ceaserDecoding(ServerMessage,3)+"\n");
                
            	System.out.print("Client: ");
                
                String msg_to_send = s.nextLine();
                buffw.write(cs.ceaserEncoding(msg_to_send,3));
                buffw.newLine();
                buffw.flush();
                
                if(msg_to_send.equalsIgnoreCase("BYE"))
                    break;

                
            }

        }
        catch(IOException e){
            System.out.println("Some error occured...");
        }
        finally{
            try{
                if(socket != null)
                    socket.close();
                if(inreader!=null)
                    inreader.close();
                if(outwriter!=null)
                    outwriter.close();
                if(buffr!=null)
                    buffr.close();
                if(buffw!=null)
                    buffw.close();
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
