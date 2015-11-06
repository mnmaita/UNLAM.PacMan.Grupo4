package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadServerLogin extends Thread {

    private Socket clientSocket;
    private Server servidor;
    
    public ThreadServerLogin(Socket socket,Server serv) {
        clientSocket = socket;
        servidor=serv;
    }

    public void run() {
        try {
        	DataInputStream data= new DataInputStream(clientSocket.getInputStream());
        	ObjectInputStream is = new ObjectInputStream(data);
        	PaqueteSesion paquete=(PaqueteSesion)is.readObject();
            if (!clientSocket.isClosed()) {
	            DataOutputStream d = new DataOutputStream(clientSocket.getOutputStream());
	            ObjectOutputStream o = new ObjectOutputStream(d);
	            //VERIFICAR USUARIO EN LA DATABASE
	            if(paquete.getSesion()){
	            }
	            paquete.setAck(false);
	            //
	            o.writeObject(paquete);
            }
        }
        catch(EOFException e){
            try {
            	System.out.println("Cerrando cliente");
                clientSocket.close();
                servidor.eliminarCliente();
                System.out.println("Un cliente se ha desconectado.");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch(IOException e) {
        	e.printStackTrace();
            try {
            	System.out.println("Cerrando cliente");
                clientSocket.close();
                servidor.eliminarCliente();
                System.out.println("Un cliente se ha desconectado.");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    }
}
