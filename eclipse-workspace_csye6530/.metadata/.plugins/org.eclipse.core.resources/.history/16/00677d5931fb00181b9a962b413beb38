package semester_project;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;


public class CoapServerConnector {
	

	
	// static
	private static final Logger _Logger = Logger.getLogger(CoapServerConnector.class.getName());
	// private var's
	private CoapServer _coapServer;

	// constructors
	public CoapServerConnector() {
		super();
		_coapServer = new CoapServer();
		TempResourceHandler tempHandler = new TempResourceHandler();
		addResource(tempHandler);
	}

	// public methods
	public void addResource(CoapResource resource) {
		if (resource != null) {
			_coapServer.add(resource);
		}
	}
	
	
	/**
	 *  start() method is used to start the CoAP server;
	 */
	public void start() {
		if (_coapServer == null) {
			_Logger.info("Creating CoAP server instance and 'temp' handler...");
//			_coapServer = new CoapServer();
//			//Create a new TempResourceHandler and add it to coapServer 
//			TempResourceHandler tempRH = new TempResourceHandler();
//			_coapServer.add(tempRH);

		}
		_Logger.info("Starting CoAP server...");
		_coapServer.start();
		

	}

	/**
	 *  stop() is used to stop the CoAP server;
	 */
	public void stop() {
		_Logger.info("Stopping CoAP server...");
		_coapServer.stop();
	}
}