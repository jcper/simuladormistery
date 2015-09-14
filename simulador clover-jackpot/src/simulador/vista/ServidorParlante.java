package simulador.vista;

import java.io.*;
import java.net.*;



/**
 * 
 * @author jcper
 * Servidor socket multihilo
 */


public class ServidorParlante extends Thread {
	public static Socket socket;
	public static BufferedReader entrada;
	public static PrintWriter salida;
	
	public ServidorParlante(Socket s) throws IOException
	
	{
		socket = s;
		entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//Habilitar el autovaciado:
		salida = new PrintWriter( new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		// si cualquiera de las llamadas de arriba lanza una excepcion, el llamador 
		//responsable de cerrar el socket.De lo contrario lo cerrara el hilo.
		start();//Llama a run();
	}	
		public void run(){
		try{
			while(true){
				String str= entrada.readLine();
				if(str.equals("Fin")) break;
				Vista.Servidor.setText(str);
				System.out.println("Haciendo eco: " + str);
				salida.println(str);
			}
			System.out.println("cerrando...");
		}catch (IOException e){
			System.err.println("Excepcion E/S");
		}finally{
			try{
				socket.close();
			}catch (IOException e){
				System.err.println("Socket sin cerrrar");
			
			}
		}
		
	}
    
     }
	
	
