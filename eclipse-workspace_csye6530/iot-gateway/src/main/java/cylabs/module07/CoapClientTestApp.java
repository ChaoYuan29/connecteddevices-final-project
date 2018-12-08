package cylabs.module07;

import java.util.logging.Logger;

public class CoapClientTestApp
{
//static
    private static final Logger _Logger = Logger.getLogger(CoapClientTestApp.class.getName());
    private static CoapClientTestApp _App;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
       _App = new CoapClientTestApp();
       try {
              _App.start();
       } catch (Exception e) {
              e.printStackTrace();
       		} 
    }

    public CoapClientTestApp()
    {
    	super();
    }
// public methods
/**
* Connect to the CoAP server
*
*/
    public void start(){
    	CoapClientConnector _coapClient = new CoapClientConnector();
    	_coapClient.runTests("temp");
    	_coapClient.sendGetRequest();

    	
    }
}
