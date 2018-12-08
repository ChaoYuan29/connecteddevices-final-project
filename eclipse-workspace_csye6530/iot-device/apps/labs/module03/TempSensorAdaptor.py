'''
Created on 2018年9月22日

@author: andyyuan
'''
from random import uniform
from time import sleep
import threading
from labs.common import SensorData
from labs.common import ActuatorData
from labs.module03 import SmtpClientConnector 
from labs.module03 import TempActuatorEmulator 


DEFAULT_RATE_IN_SEC = 10



class TempSensorAdaptor (threading.Thread):
    sensorData = SensorData.SensorData()
    actuatorData = ActuatorData.ActuatorData()
    connector = SmtpClientConnector.SmtpClientConnector()
    actu = TempActuatorEmulator.TempActuatorEmulator()
    enableEmulator = False
    isPrevTempSet  = False
    rateInSec      = DEFAULT_RATE_IN_SEC
    sensorData.setName('Temperature')
    
    lowVal = 0
    highVal = 30
    alertDiff = 5
   
    def __init__(self, rateInSec = DEFAULT_RATE_IN_SEC):
        super(TempSensorAdaptor, self).__init__()
        
        if rateInSec > 0:
            self.rateInSec = rateInSec

    def run(self):
            while True:
                if self.enableEmulator:
                    self.curTemp = uniform(float(self.lowVal), float(self.highVal))
                    self.sensorData.addValue(self.curTemp)
                    self.actuatorData.setValue(self.curTemp)
                    self.actu.processMessage(self.actuatorData)
                    print('\n--------------------')
                    print('New sensor readings:')
                    print('  ' + str(self.sensorData))
                    if self.isPrevTempSet == False:
                        self.prevTemp      = self.curTemp
                        self.isPrevTempSet = True
                    else:
                        if (abs(self.curTemp - self.sensorData.getAvgValue()) >= self.alertDiff):                            
                            print('\n  Current temp exceeds average by > ' + str(self.alertDiff) + '. Triggeringalert...')
                            self.connector.publishMessage('Exceptional sensor data [test]', self.sensorData)
                sleep(self.rateInSec)