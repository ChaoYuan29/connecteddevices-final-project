package cylabs.module07;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;


public class CoapServerConnector
{
//static
    private static final Logger _Logger = Logger.getLogger(CoapServerConnector.class.getName());
    // private var's
    private CoapServer _coapServer;
    private String _protocol;
	private String _host;
	private int _port;
	private String _serverAddr;
	private CoapClient _clientConn;
	private boolean _isInitialized;
    // constructors
    /**
     * Default.
     *
     */
	public CoapServerConnector() {
		this("localhost");
	}
	
    public CoapServerConnector(String host)
    {
    	super(); 
    	if (host != null && host.trim().length() > 0) {
			_host = host;
		} else {
			_host = "localhost";
		}
    }
    // public methods
    public void addResource(CoapResource resource)
    {
       if (resource != null) {
              _coapServer.add(resource);
       	} 
    }
    public void start()
    {
    	if (_coapServer == null) {
    		_Logger.info("Creating CoAP server instance and 'temp' handler...");
   
    	_coapServer = new CoapServer();
//NOTE: you must implement TestResourceHandler yourself
    	TempResourceHandler tempHandler = new TempResourceHandler("temp");
    	_coapServer.add(tempHandler);
    	}
	_Logger.info("Starting CoAP server...");
	_coapServer.start();
    }
    public void stop()
    {
    	_Logger.info("Stopping CoAP server...");
    	_coapServer.stop();
    }
}
