'''
Created on 2018年9月15日

@author: andyyuan
'''
from random import uniform
from time import sleep
import threading
from labs.common import SensorData
from labs.module02 import SmtpClientConnector 


DEFAULT_RATE_IN_SEC = 10



class TempSensorEmulator (threading.Thread):
    sensorData = SensorData.SensorData()
    connector = SmtpClientConnector.SmtpClientConnector()
    enableEmulator = False
    isPrevTempSet  = False
    rateInSec      = DEFAULT_RATE_IN_SEC
    sensorData.setName('Temperature')
    
    lowVal = 0
    highVal = 30
    alertDiff = 5
   
    def __init__(self, rateInSec = DEFAULT_RATE_IN_SEC):
        super(TempSensorEmulator, self).__init__()
        
        if rateInSec > 0:
            self.rateInSec = rateInSec

    def run(self):
            while True:
                if self.enableEmulator:
                    self.curTemp = uniform(float(self.lowVal), float(self.highVal))
                    self.sensorData.addValue(self.curTemp)
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