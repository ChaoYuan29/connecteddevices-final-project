package semester_project;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClientConnector implements MqttCallback
{
    // static
    private static final Logger _Logger = Logger.getLogger(MqttClientConnector.class.getName());
    
	// private var's
    private String _protocol;
    private String _host;
    private int    _port;
    private String _clientID;
    private String _brokerAddr;
    private MqttClient _mqttClient;
	private String _pemFileName = "/Users/andyyuan/Desktop/ubidots.pem ";
	private String _userName;
	private String _authToken;
	private boolean _isSecureConn;
   
	// constructor
	public MqttClientConnector(String host, String userName,String _authToken, String pemFileName)
	{
		super();
		if (host != null && host.trim().length() > 0) {
			_host = host;
		}
		if (userName != null && userName.trim().length() > 0) {
		 _userName = userName;
		}

		if (pemFileName != null) {
			File file = new File(pemFileName);
			
			if (file.exists()) {
				_protocol = "ssl";
				_port = 8883;
				_pemFileName = pemFileName;
				_isSecureConn = true;
				_Logger.info("PEM file valid. Using secure connection: " + _pemFileName);
			} else {
				_Logger.warning("PEM file invalid. Using insecure connection: " + pemFileName);
			}
		}
		_clientID = MqttClient.generateClientId();
		_brokerAddr = _protocol + "://" + _host + ":" + _port;
		_Logger.info("Using URL for broker conn: " + _brokerAddr);
	}
	
	//public methods
	public void connect(){
		if (_mqttClient == null) {
			MemoryPersistence persistence = new MemoryPersistence();
			try {
				_mqttClient = new MqttClient(_brokerAddr, _clientID, persistence);
				MqttConnectOptions connOpts = new MqttConnectOptions();
				// TODO: do we always want a clean session?
				connOpts.setCleanSession(true);
				if (_userName != null) {
					connOpts.setUserName(_userName);
				}
				// if we are using a secure connection, there's a bunch of stuff we need to do...
				if (_isSecureConn) {
					initSecureConnection(connOpts);
				}
				_mqttClient.setCallback(this);
				_mqttClient.connect(connOpts);
				
				_Logger.info("Connected to broker: " + _brokerAddr);
				} 
			catch (MqttException e) {
					_Logger.log(Level.SEVERE, "Failed to connect to broker: " + _brokerAddr, e);
				}
		}
	 }
	
	private void initSecureConnection(MqttConnectOptions connOpts)
	{
		try {
			 _Logger.info("Configuring TLS...");
			 SSLContext sslContext = SSLContext.getInstance("SSL");
			 KeyStore keyStore = readCertificate();
			 TrustManagerFactory trustManagerFactory =
			 TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			 trustManagerFactory.init(keyStore);
			 sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			 connOpts.setSocketFactory(sslContext.getSocketFactory());
	 } 
		catch (Exception e) {
	 _Logger.log(Level.SEVERE, "Failed to initialize secure MQTT connection.", e);
	 }
	}
	private KeyStore readCertificate()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		 KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		 FileInputStream fis = new FileInputStream(_pemFileName);
		 BufferedInputStream bis = new BufferedInputStream(fis);
		 CertificateFactory cf = CertificateFactory.getInstance("X.509");
		 
		 ks.load(null);
		 
		 while (bis.available() > 0) {
			 Certificate cert = cf.generateCertificate(bis);
			 ks.setCertificateEntry("adk_store" + bis.available(), cert);
		 }
	 return ks;
	}
public void disconnect()
	{
	try {
		_mqttClient.disconnect();
		_Logger.info("Disconnected from broker: " + _brokerAddr);
		} catch (Exception e) {
		_Logger.log(Level.SEVERE, "Failed to disconnect from broker: " + _brokerAddr, e);
		} 
	}
/**
* Publishes the given payload to broker directly to topic 'topic'.
*
* @param topic
* @param qosLevel
* @param payload
*/
public boolean publishMessage(String topic, int qosLevel, byte[] payload){
	
	boolean success = false;
	try {
	   _Logger.info("Publishing message to topic: " + topic);
	
	   // create a new MqttMessage, pass 'payload' to the constructor
	   MqttMessage mqttmessage = new MqttMessage(payload);
	   
	   // set QoS level
	   mqttmessage.setQos(qosLevel);
	   
	   //publish message
	   _mqttClient.publish(topic, mqttmessage);
	   _Logger.log(Level.SEVERE, "Log the ID: ", mqttmessage.getId());
	   success = true;
	} catch (Exception e) {
		_Logger.log(Level.SEVERE, "Failed to publish MQTT message: " + e.getMessage());

		}
		return success;
	}

public boolean subscribeToAll(){
	try {
		_mqttClient.subscribe("$SYS/#");
		return true;
	} catch (MqttException e) {
		_Logger.log(Level.WARNING, "Failed to subscribe to all topics.", e);
	}
	return false;
	}

public boolean subscribeToTopic(String topic, int qosLevel){
	try {
		_mqttClient.subscribe(topic,qosLevel);
		System.out.println("Topic" + topic);
		return true;
	}catch(MqttException e) {
		_Logger.log(Level.WARNING, "Failed to subscribe to current topics.", e);
	}
	return false;
	
	}
/*
* (non-Javadoc)
*
* @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.
* Throwable)
*/
public void connectionLost(Throwable t)
	{
	_Logger.log(Level.WARNING, "Connection to broker lost. Will retry soon.", t);
	}
/*
* (non-Javadoc)
*
* @see
* org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho
* .client.mqttv3.IMqttDeliveryToken)
*/
public void deliveryComplete(IMqttDeliveryToken token)
	{
	try {

         _Logger.info("Delivery complete: " + token.getMessageId() + " - " + token.getResponse() + " - "
                     + token.getMessage());
	} catch (Exception e) {
		_Logger.log(Level.SEVERE, "Failed to retrieve message from token.", e);
		} 
	}
/*
* (non-Javadoc)
*
* @see
* org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String,
* org.eclipse.paho.client.mqttv3.MqttMessage)
*/
public void messageArrived(String data, MqttMessage msg) throws Exception
	{
	
	MqttClientConnectorII _mqttClient = new MqttClientConnectorII("test.mosquitto.org","tcp",1883);
	String backvalue = new String(msg.getPayload());
	_mqttClient.connect();
	_mqttClient.publishMessage("topicaa", 1, backvalue.getBytes());
	
	_Logger.info("Message arrived: " + data + ", " + msg.getId());
	_Logger.info(msg.toString());
	
		}
	}