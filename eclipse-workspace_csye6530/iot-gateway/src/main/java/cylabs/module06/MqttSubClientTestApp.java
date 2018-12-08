package cylabs.module06;

import java.util.logging.Logger;
// only include this if you have a ‘ConfigConst’ class...
//import {your package name}.labs.common.ConfigConst;

public class MqttSubClientTestApp {
	// static
    private static final Logger _Logger =
       Logger.getLogger(MqttSubClientTestApp.class.getName());
    private static MqttSubClientTestApp _App;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
       _App = new MqttSubClientTestApp();
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

    public MqttSubClientTestApp()
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
	String topicName = "the topic 1";
	_Logger.info("Waiting for publishing... ");
 // only for subscribing...
	_mqttClient.subscribeToTopic(topicName,2); // you must implement this method yourself
//	_mqttClient.subscribeToAll(); // this is implemented for you
// // only for publishing...
//	String payload = "This is a test...";
// // only for publishing...
//	_mqttClient.publishMessage(topicName, 0, payload.getBytes());
//	_mqttClient.disconnect();
	}

}
