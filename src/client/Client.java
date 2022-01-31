package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Util.Message;
import server.Util.Status;

/**
 *
 * @author Michacreu
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
        
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            Message message = new Message("HELLO");
            message.setStatus( Status.REQUEST );
            message.setParam("name", "João");
            message.setParam("surname", "Michael");
            
            output.writeObject(message);
            output.flush();
            
            message = (Message) input.readObject();
            System.out.println("Message -> " + message);
            if(message.getStatus() == Status.OK) {
                String response = (String) message.getParam("message");
                System.out.println("Message response -> " + response);
            } else {
                System.out.println("Erro in message -> " + message.getStatus());
            }
            
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro no cast: " + ex.getMessage());
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
