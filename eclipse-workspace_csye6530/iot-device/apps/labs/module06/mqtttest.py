'''
Created on Dec 8, 2018

@author: andyyuan
'''

import paho.mqtt.client as mqttClient


def on_connect(clientConn, data, flags, resultCode):


    print("Client connected to server. Result: " + str(resultCode))


# NOTE: This subscribes to ALL topics (depending on broker implementation) 
    clientConn.subscribe("aaaaa")
def on_message(clientConn, data, msg):


    print("Received PUBLISH on topic {0}. Payload: {1}".format(str(msg.topic), str(msg.payload)))
mc = mqttClient.Client() 
mc.on_connect = on_connect 
mc.on_message = on_message
mc.connect("test.mosquitto.org", 1883, 60) 
mc.loop_forever()