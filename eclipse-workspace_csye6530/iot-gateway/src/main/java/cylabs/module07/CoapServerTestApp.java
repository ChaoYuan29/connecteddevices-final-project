package cylabs.module07;

import java.util.logging.Logger;
import java.util.logging.Level;

public class CoapServerTestApp
{
//static
    private static final Logger _Logger =
       Logger.getLogger(CoapServerTestApp.class.getName());
    private static CoapServerTestApp _App;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
       _App = new CoapServerTestApp();
       try {
              _App.start();
       } catch (Exception e) {
    	   _Logger.log(Level.SEVERE, "Bad staff ", e);
			System.exit(1);
       		} 
       }
    // private var's
    private CoapServerConnector _coapServer;
    // constructors
/** *
     */
    public CoapServerTestApp()
    {
    	super(); 
    	}
// public methods
/** *
*/
    public void start()
    {
    	_coapServer = new CoapServerConnector();
    	_coapServer.start();
    }
}
