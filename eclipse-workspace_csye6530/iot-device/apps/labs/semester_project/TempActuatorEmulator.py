'''
Created on 2018年9月22日

@author: andyyuan
'''

# import sys
# sys.path.append('/home/pi/workspace/iot-device/apps')

import threading

from labs.common import ConfigUtil
from labs.common import ConfigConst
from labs.common import ActuatorData
from labs.semester_project import SenseHatLedActivator

Data = ActuatorData.ActuatorData()
shla = SenseHatLedActivator.SenseHatLedActivator()


class TempActuatorEmulator(threading.Thread):
    
    def __init__(self):
        self.config = ConfigUtil.ConfigUtil('')
        self.config.loadConfig()
        print('Configuration data...\n' + str(self.config)) 
        print('============= Setting Done! =============')
        
        shla.daemon = True
        shla.start()
    
    def processMessage(self,data):
        self.nominalTemp = self.config.getProperty(ConfigConst.DEVICE_SECTION,ConfigConst.NOMINALTEMP)
        '''
        nominalTemp = 20
        
        '''
        nomal = float(self.nominalTemp)
        Data = data
        
        
        Data.setCommand(0)
        Data.setName('signal')
        Data.setErrorCode(0)
        Data.setStateData('stateData')
        Data.setStatusCode(0)
        
        Data.updateData(Data)
        difference = str(abs(int(Data.getValue() - nomal)))
        
        if (Data.getValue() > nomal):
            Data.setCommand(1)
            Data.setErrorCode(0)
            Data.setStateData('Please decrease ' + difference + 'degrees')
            Data.setStatusCode(1)
        
            Data.updateData(Data)
            print('---' + Data.__str__())
            
            shla.enableLed = True
            shla.setDisplayMessage(Data.getStateData())
            
        if (Data.getValue() < nomal):
            Data.setCommand(1)
            Data.setErrorCode(0)
            Data.setStateData('Please increase ' + difference + 'degrees')
            Data.setStatusCode(1)
            
            Data.updateData(Data)
            print('---' + Data.__str__())
            
            shla.enableLed = True
            shla.setDisplayMessage(Data.getStateData())
            
            
        if (Data.getValue() == nomal):
            Data.setCommand(0)
            Data.setErrorCode(0)
            Data.setStateData('temperature is normal')
            Data.setStatusCode(0)
            
            Data.updateData(Data)
            print('---' + Data.__str__())
            
            shla.enableLed = False

            
            
        
        
        
        
        
    