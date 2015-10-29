package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

public class Cliente {

    private Socket cliente;
    private String nombre = null;
    private int puerto;

    public int getPuerto() {
        return puerto;
    }

    public Cliente(String direccion, int port) {
        try {
            puerto = port;
            cliente = new Socket(direccion, port);
        }
        catch(UnknownHostException e1) {
        	System.out.println("No se pudo conectar con el servidor, cerrando el  programa...");
        }
        catch (IOException e2) {
            System.out.println("No se pudo crear el socket, cerrando el  programa...");
            System.exit(-1);
        }
    }

    public Socket getSocket() {
        return cliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void enviarMensaje() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          	//Se lee desde el host del usuario y dirige el flujo o información al server
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            String data;
            while ((data = br.readLine()) != null) {
                if (data.contains(".exit")) {
                    System.out.println("Cerrando Aplicacion...");
                    cerrarCliente();
                    System.exit(1);
                }
                else if (!data.equals("")) {
                	dos.writeUTF("["+horaDelMensaje()+"] " + nombre + ": " + data);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarCliente() {
        try {
            cliente.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String horaDelMensaje() {
        Calendar cal = Calendar.getInstance();
        return +cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND);
    }
}
