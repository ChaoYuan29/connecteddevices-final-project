package semester_project;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

import semester_project.MqttClientConnector;

public class TempResourceHandler extends CoapResource{
	
	private static final Logger _Logger = 
			Logger.getLogger(TempResourceHandler.class.getName());

	public TempResourceHandler() {
		super("temp");
	}
	
	public TempResourceHandler(String name) {
		super(name);
	}
	
	public TempResourceHandler(String name, boolean visible) {
		super(name, visible);
	}
	
	@Override
	public void handleGET(CoapExchange exch) {
		exch.respond(ResponseCode.VALID, super.getName()+"response");
		_Logger.info("Get request：" + super.getName());
	}
	@Override
	public void handlePOST(CoapExchange exch) {

		System.out.println(exch.getRequestText());
		
		
		//Start Mqtt clientCloud
		MqttClientConnector _mqttClient = new MqttClientConnector("things.ubidots.com","A1E-cohoPVgrJYePKdP7FkjNMRWKSzQ4xC",null,"/Users/andyyuan/Desktop/ubidots.pem");
		_mqttClient.connect();
		String topicName = "/v1.6/devices/homeiotgateway/Tempsensor";
		String payload = exch.getRequestText();
		_mqttClient.publishMessage(topicName, 1, payload.getBytes());
		_mqttClient.disconnect();
		
		exch.respond(ResponseCode.VALID, super.getName()+"response");
		_Logger.info("Post request：" + exch.getRequestText());
	}

	@Override
	public void handlePUT(CoapExchange exch) {
		exch.respond(ResponseCode.VALID, super.getName()+"response");
		_Logger.info("Put request：" + exch.getRequestText());
	}

	@Override
	public void handleDELETE(CoapExchange exch) {
		exch.respond(ResponseCode.VALID, super.getName()+"response");
		_Logger.info("Delete request：" + super.getName());
	}
}
