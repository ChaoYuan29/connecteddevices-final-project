package semester_project;

//import sys
//sys.path.append('/home/pi/workspace/iot-device/apps')

public class GatewayManagementApp{



	public void run() {
		//Start CoAP server
		try {
			CoapServerConnector coapserver = new CoapServerConnector();
			coapserver.start();
		}catch(Exception e) {
			System.out.println("Start Failed");
		}
	}
	
	public static void main(String[] args) {
		GatewayManagementApp gatewayApp = new GatewayManagementApp();
		gatewayApp.run();
	}
	
}