package cylabs.module06;

import java.util.logging.Logger;

public class MqttPubClientTestApp {
	
	// static
    private static final Logger _Logger =
       Logger.getLogger(MqttPubClientTestApp.class.getName());
    private static MqttPubClientTestApp _App;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
       _App = new MqttPubClientTestApp();
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

    public MqttPubClientTestApp()
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
	_mqttClient = new MqttClientConnector("test.mosquitto.org","tcp",1883);
	_mqttClient.connect();
	String topicName = "aaaaa";
	_Logger.info("Publishing...");
// // only for subscribing...
//	_mqttClient.subscribeToTopic(topicName,2); // you must implement this method yourself
//	_mqttClient.subscribeToAll(); // this is implemented for you
// // only for publishing...
	String payload = "This is the payload of topic 1";
// // only for publishing...
	_mqttClient.publishMessage(topicName, 2, payload.getBytes());
	_mqttClient.disconnect();
	}

}
