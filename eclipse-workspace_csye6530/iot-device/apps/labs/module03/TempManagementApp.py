'''
Created on 2018年9月22日

@author: andyyuan
'''


from time import sleep
from labs.module03 import TempSensorAdaptor
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