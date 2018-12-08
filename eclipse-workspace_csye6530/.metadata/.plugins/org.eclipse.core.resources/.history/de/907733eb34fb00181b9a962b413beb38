'''
Created on 2018年9月22日

@author: andyyuan
'''
# import sys
# sys.path.append('/home/pi/workspace/iot-device/apps')

from time import sleep
from labs.semester_project import TempSensorAdaptor
#from labs.module02 import SmtpClientConnector


tempSensAdaptor = TempSensorAdaptor.TempSensorAdaptor()

tempSensAdaptor.daemon = True
print('- - - - - - - - - - - - - - - - - - - - - - - - ')
print("Starting system performance app daemon thread...")
tempSensAdaptor.enableEmulator = True
tempSensAdaptor.start()

while (True):
    sleep(10)
    pass