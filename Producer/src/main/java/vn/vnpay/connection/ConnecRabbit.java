package vn.vnpay.connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import vn.vnpay.config.RabbitMQConfig;


@Log4j2
public class ConnecRabbit {
   private static final class SingletonHolder {
      private static final ConnecRabbit INSTANCE = new ConnecRabbit();
   }

   public static ConnecRabbit getInstance(){
      return SingletonHolder.INSTANCE;
   }
   public ConnecRabbit(){}


   public static ConnectionFactory getConnect(RabbitMQConfig config){
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(config.getHost());
//      factory.setPort(config.getPort());
//      factory.setUsername(config.getUser());
//      factory.setPassword(config.getPass());
      return factory;
   }
}
