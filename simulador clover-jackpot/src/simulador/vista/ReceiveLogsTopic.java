package simulador.vista;

import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;

	public class ReceiveLogsTopic {

	  private static final String EXCHANGE_NAME = "rfranco_topic_exchange";
	 
	  private static final String Queue_Name="egm_queue";
	  public static void Receptor() throws Exception {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	    
	    channel.queueDeclare(Queue_Name, false, false, false, null);

	   

	    //String bindingKey="Simulador.*";
	    String bindingKey="*.*.response.egm.config";
	      channel.queueBind(Queue_Name, EXCHANGE_NAME, bindingKey);
	    
         Consumer consumer = new DefaultConsumer(channel) {
	      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope,
	    		  AMQP.BasicProperties properties, byte[] body) throws IOException {
	        String message = new String(body, "UTF-8");
	        Vista.mensaje_recibido=message;//mensaje se recibe en la vista del simulador.
	        
	        
	        System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
	      }
	    };
	    channel.basicConsume(Queue_Name, true, consumer);
	  }
	}



