package semester_project;

import java.util.logging.Logger;

public class TempActuatorSubscriberApp {
	
	// static
    private static final Logger _Logger =
       Logger.getLogger(TempActuatorSubscriberApp.class.getName());
    private static TempActuatorSubscriberApp _App;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
       _App = new TempActuatorSubscriberApp();
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

    public TempActuatorSubscriberApp()
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
	String topicName1 = "/v1.6/devices/homeiotgateway/tempactuator";
	String topicName3 = "/v1.6/devices/homeiotgateway/maxtemp";
	String topicName5 = "/v1.6/devices/homeiotgateway/mintemp";

	// subscribe topics
	_mqttClient.subscribeToTopic(topicName1,1); 
	_mqttClient.subscribeToTopic(topicName3,1);
	_mqttClient.subscribeToTopic(topicName5,1);

	}

}
