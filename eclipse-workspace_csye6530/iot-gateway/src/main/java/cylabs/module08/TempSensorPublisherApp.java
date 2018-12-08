package cylabs.module08;

import java.util.logging.Logger;

public class TempSensorPublisherApp {
	
	// static
    private static final Logger _Logger =
       Logger.getLogger(TempSensorPublisherApp.class.getName());
    private static TempSensorPublisherApp _App;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
       _App = new TempSensorPublisherApp();
       try {
              _App.start();
       } catch (Exception e) {
              e.printStackTrace();
       } 
    }
    // params
    private MqttClientConnector _mqttClient;
    // constructors
    /**
     * Default.
     */

    public TempSensorPublisherApp()
	{
	super(); 
	}
// public methods
/**
* Connect to the MQTT client, then:
* 1) If this is the subscribe app, subscribe to the given topic
* 2) If this is the publish app, publish a test message to the given topic
*/
    public void start()
	{
	_mqttClient = new MqttClientConnector("things.ubidots.com","A1E-cohoPVgrJYePKdP7FkjNMRWKSzQ4xC",null,"/Users/andyyuan/Desktop/ubidots.pem");
	_mqttClient.connect();
	String topicName = "/v1.6/devices/HomeIotGateway/tempsensor";
// // only for subscribing...
//	_mqttClient.subscribeToTopic(topicName,2); // you must implement this method yourself
//	_mqttClient.subscribeToAll(); // this is implemented for you
// // only for publishing...
	String payload = "5";
// // only for publishing...
	_mqttClient.publishMessage(topicName, 0, payload.getBytes());
//	_mqttClient.disconnect();
	}

}
