package simulador.vista;
import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Vista extends JFrame implements ActionListener{
	
	static JButton start;
	static JLabel  rabbitmq;
	static JButton S21Config;
	static JButton S21Jugada;
	static JButton S21Alive;
	static JButton RF21Status;
	static JButton RF21Jugada;
	static JButton RF21Alive;
	static JButton clearPantalla;
	static JTextArea Servidor;
	static JButton wiznet;
	static JButton stop;
	static JButton clear;
	static JButton exit;
	static JTextField comando;
	static JTextField rabbit;
	static JTable registros;
	static JPanel datos;
	static JPanel comandos_tabla;
	static JPanel Estado;
    static JFrame ventana;
    static String mensaje_recibido="";
    static JLabel Server;
    static String IP_Server="192.168.1.213";
    static JLabel Queue;
    static String Name_Queue="egm_queue";
    static JLabel RoutingKey;
    static String name_RoutingKey=" *.*.response.egm.config ";
    static JLabel Conexion;
    static String estado_conexion="Habilitada";
    static String mensaje_rabbit;
    static Calendar calendario;
    static DefaultTableModel modelo=null;
    static String respuesta="";
    static JPanel contenedor_central;
    static JPanel RF21;
    static JPanel S21;
    static JPanel Pantalla_Socket;
    static String text_pantallaSocket;
    
	 public Vista(){
		 ventana=new JFrame("Simulador de EGM a RABBITMQ");
		 contenedor_central=new JPanel();
		 contenedor_central.setLayout(new BoxLayout(contenedor_central, BoxLayout.Y_AXIS));
		 S21Config=new JButton("S21Config");
		 S21Jugada=new JButton("S21Jugada");
	     S21Alive= new JButton("S21Alive");
		 RF21Status=new JButton("RF21Status");
		 RF21Jugada=new JButton("RF21Jugada");
		 RF21Alive=new JButton("RF21Alive");
		 Servidor=new JTextArea(text_pantallaSocket, 3, 50);
		 wiznet=new JButton("wiznet");
		 stop=new JButton("Parar");
		 start=new JButton("Envio EGM");
		 rabbitmq=new JLabel ("RabbitMQ");
		 clear=new JButton("Clear");
		 exit=new JButton("Salir");
		 clearPantalla=new JButton("clear");
		 comando= new JTextField(25);
		 rabbit=new JTextField(25);
		 registros= new JTable();
		 modelo = new DefaultTableModel();
	     JScrollPane desplazamiento = new JScrollPane(registros);
		 String[] Columnas={"Tiempo","Comando","Resultado"};
		 registros.setModel(modelo);
		 modelo.setColumnIdentifiers(Columnas);
	     Server=new JLabel("Servidor:  "+IP_Server);
	     Queue=new JLabel("Queue:  "+Name_Queue);
	     RoutingKey=new JLabel("RoutingKey:  "+name_RoutingKey);
	     Conexion=new JLabel("Conexion:  "+estado_conexion);
	        // Barras de desplazamiento
	        desplazamiento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        desplazamiento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	        
	        // Propiedades de la tabla
	        registros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	        registros.setFillsViewportHeight(true);
	        registros.getModel();
	        
	      //se agrega la fila en la tabla con un valor vacio
	     
	        
		// Parametros de la ventana
	        ventana.setTitle("Simulador Maquina EGM---------->Rabbit");
	        ventana.setLayout(new BorderLayout());
	        datos=new JPanel(new FlowLayout());
	        comandos_tabla=new JPanel(new FlowLayout());
	        Estado=new JPanel(new FlowLayout(FlowLayout.LEFT,50,10));
	        Estado.add(Server);
	        Estado.add(Queue);
	        Estado.add(RoutingKey);
	        Estado.add(Conexion);
	        datos.add(start);
	        datos.add(comando);
	        datos.add(rabbitmq);
	        datos.add(rabbit);
	        S21=new JPanel(new FlowLayout(FlowLayout.CENTER,70,10));
	        RF21=new JPanel(new FlowLayout(FlowLayout.CENTER,70,10));
	        Pantalla_Socket=new JPanel(new FlowLayout());
	        S21.add(S21Config);
	        S21.add(S21Jugada);
	        S21.add(S21Alive);
	        RF21.add(RF21Status);
	        RF21.add(RF21Jugada);
	        RF21.add(RF21Alive);
	        Pantalla_Socket.add(wiznet);
	        Pantalla_Socket.add(Servidor);
	        Pantalla_Socket.add(stop);
	        Pantalla_Socket.add(clearPantalla);
	       
	        contenedor_central.add(datos);
	        contenedor_central.add(S21);
	        contenedor_central.add(RF21);
	        contenedor_central.add(Pantalla_Socket);
	        comandos_tabla.add(clear);
	        comandos_tabla.add(exit);
	        ventana.setSize(300,200);
	       
	        ventana.getContentPane().add(Estado,BorderLayout.NORTH);
	        ventana.getContentPane().add(contenedor_central,BorderLayout.CENTER);
	        ventana.getContentPane().add(desplazamiento,BorderLayout.SOUTH);
	        ventana.getContentPane().add(comandos_tabla,BorderLayout.EAST);
	        ventana.pack(); 
	        ventana.setVisible(true); 
	        
	        start.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		    	
		    
		    	 rabbit.setText("");
		    	 String Message=comando.getText();
		    	 EmitLogTopic.Emisor(Message);
		    	 try {
					ReceiveLogsTopic.Receptor();
					 rabbit.setText(mensaje_recibido);
					
			    	 rabbit.setEnabled(true);
			    	 rabbit.setEditable(true);
			   if(Message.equals("")){
				   System.out.println("cadena vacia");
			   }else{
			    	
			    if(Message.equals(mensaje_recibido)){
			    	respuesta="Ok";
			    }else{
			    	respuesta="Fail";
			    }
			    
			    	modelo.addRow( new Object[] {Vista.Tiempo_respuesta(),Vista.mensaje_recibido,respuesta});
			    	 //comando.setText("");
			    	 
			   }	 
			    
			} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    	
		     }
	      
	    });
         clear.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		    	 comando.setText("");
		    	 rabbit.setText("");
		    	 
		     }
        });
        
        exit.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		    	 System.exit(1);
		     }
        });
        
        wiznet.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		    	try {
					IniciarServidor();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		     }
       });
        
       stop.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent e){
		    	 try {
					ServidorParlante.socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		     }
       
     });
       
      clear.addActionListener(new ActionListener(){
    	  public void actionPerformed(ActionEvent e){
    		  text_pantallaSocket="";
    	  }
      });
       
 }


	 public static String Tiempo_respuesta(){
		    String time;
		    int hora, minutos, segundos;
		    calendario = new GregorianCalendar();
			hora=calendario.get(Calendar.HOUR_OF_DAY);
			String.valueOf(hora);
			minutos = calendario.get(Calendar.MINUTE);
			String.valueOf(minutos);
			segundos = calendario.get(Calendar.SECOND);
			String.valueOf(segundos);
			return time= hora +":"+minutos+":"+segundos;
		    
	 }
	 
	 public  void IniciarServidor()  throws IOException {
		  ServerSocket s = new ServerSocket(5050);
		  System.out.println("Servidor Iniciado");
		  try {
			  while(true){
				  //Se bloquea hasta que da una conexion:
				  Socket socket= s.accept();
				try{
					new ServidorParlante(socket);
				}catch (IOException e){
					//Si falla, cerrar el socket;
				    //si no, lo cerrara el hilo
					socket.close();
				}
			  }
			  
		  }finally{
			  s.close();
		  }
	  }
  
		 
	 

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}