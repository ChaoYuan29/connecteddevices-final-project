'''
Created on 2018年12月1日

@author: andy
'''

# import sys
# sys.path.append('/home/pi/SemesterProject/apps')

from coapthon.client.helperclient import HelperClient
# from labs.common import ConfigUtil
# from labs.common import ConfigConst
#import socket


client = None

class CoapClientConnector():


    config = None
    serverAddr = None
    host = "192.168.0.148"
    port = 5683
    
    
    def __init__(self):
    
        
        print('\tHost: ' + self.host)
        print('\tPort: ' + str(self.port))
        
        if not self.host or self.host.isspace():
            print("Using default host: " + self.host)
        if self.port < 1024 or self.port > 65535:
            print("Using default port: " + self.port)
            
        self.serverAddr = (self.host, self.port)
        print('Server address:' + str(self.serverAddr))
        
        self.url = "coap://" + self.host + ":" + str(self.port) + "/temp"
    
    
    def initClient(self):

        try:
            self.client = HelperClient(server=(self.host, self.port))
            print("Created CoAP client ref: " + str(self.client))
            print(" coap://" + self.host + ":" + str(self.port))
        except Exception:
            print("Failed to create CoAP helper client reference using host: " + self.host)
            pass
    
    '''
        handling 4 request of CoAP as following
    '''
    
    def handlePostTest(self, resource, payload):
        print("handlePost is working")
    
        print("Testing POST for resource: " + resource + ", payload: " + payload)
        self.initClient()
        response = self.client.post(resource, payload)
        if response:
            print(response.pretty_print())
            print("received POST response.")
        else:
            print("No response received for POST using resource: " + resource)
        self.client.stop()
    
    
    def handleGetTest(self,resource):
        print("handleGet is working")
    
        print("Testing GET for resource: " + str(resource))
        self.initClient()
        response = self.client.get(resource)
        if response:
            print(response.pretty_print())
            print("received GET response.")
        else:
            print("No response received for GET using resource: " + resource)
        self.client.stop()
      
        
    def handlePutTest(self, resource, payload):
        print("handlePut is working")
    
        print("Testing PUT for resource: " + resource + ", payload: " + payload)
        self.initClient()
        response = self.client.put(resource, payload)
        if response:
            print(response.pretty_print())
            print("received PUT response.")
        else:
            print("No response received for put using resource: " + resource)
        self.client.stop()

        
    def handleDeleteTest(self, resource, payload):
        print("handleDelete is working")
    
        print("Testing delete for resource: " + resource + ", payload: " + payload)
        self.initClient()
        response = self.client.delete(resource, payload)
        if response:
            print(response.pretty_print())
            print("received DELETE response.")
        else:
            print("No response received for delete using resource: " + resource)
        self.client.stop()


    
