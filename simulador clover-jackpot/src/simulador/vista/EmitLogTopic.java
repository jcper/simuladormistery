package simulador.vista;


	/*
	 * Recibir mensajes basados en una routing key y una cola.
	 * Se utilizan los parametros del constructor para configurar la routing key
	 * Routing Key kern.*" "*.critical. se configra en el ReceiveLogstopic.
	 * EmitLogTopic se configura "kern.critical" "A critical kernel error" como
	 * constructor de la clase.
	 */


	import com.rabbitmq.client.AMQP.BasicProperties;
	import com.rabbitmq.client.ConnectionFactory;
	import com.rabbitmq.client.Connection;
	import com.rabbitmq.client.Channel;

	public class EmitLogTopic {

	  private static final String EXCHANGE_NAME = "rfranco_topic_exchange";
	  private static final BasicProperties egm_queue = null;
	  private static final BasicProperties Queue_Name=egm_queue;
	   
	  public static void   Emisor(String message)  {
		  Connection connection = null;
		  Channel channel = null;
	    try {
	      ConnectionFactory factory = new ConnectionFactory();
	      factory.setHost("localhost");

	      connection = factory.newConnection();
	      channel = connection.createChannel();
	       
	      channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	      channel.queueDeclare();//declaramos la cola como propiedad basica EGM_TEST
	      String routingKey = "*.*.response.egm.config";
	      

	      channel.basicPublish(EXCHANGE_NAME, routingKey,Queue_Name, message.getBytes("UTF-8"));
	      System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

	    }
	    catch  (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      if (connection != null) {
	        try {
	          connection.close();
	        }
	        catch (Exception ignore) {}
	      }
	    }
	  }
	}
	

	 
	 

	


