'''
Created on Oct 13, 2018

@author: andyyuan
'''

import smbus
import threading
from time import sleep
from labs.common import ConfigUtil
from labs.common import ConfigConst


i2cBus = smbus.SMBus(1) # Use I2C bus No.1 on Raspberry Pi3 +
enableControl = 0x2D 
enableMeasure = 0x08 
accelAddr = 0x1C # address for IMU (accelerometer)
magAddr = 0x6A # address for IMU (magnetometer)
pressAddr = 0x5C # address for pressure sensor
humidAddr = 0x5F # address for humidity sensor
begAddr = 0x28
totBytes = 6


DEFAULT_RATE_IN_SEC = 5
class I2CSenseHatAdaptor(threading.Thread):
    rateInSec = DEFAULT_RATE_IN_SEC
    
    accelerometerData = 0
    magnetometerData = 0
    pressureData = 0
    humidityData = 0
    
       
    def __init__(self):
        super(I2CSenseHatAdaptor, self).__init__()
             
        self.config = ConfigUtil.ConfigUtil(ConfigConst.DEFAULT_CONFIG_FILE_NAME)
        self.config.loadConfig()
             
        print('Configuration data...\n' + str(self.config))
        self.initI2CBus()

    def initI2CBus(self):
        print("Initializing I2C bus and enabling I2C addresses...")
        i2cBus.write_byte_data(accelAddr, enableControl, enableMeasure)
        i2cBus.write_byte_data(magAddr, enableControl, enableMeasure)
        i2cBus.write_byte_data(pressAddr, enableControl, enableMeasure)
        i2cBus.write_byte_data(humidAddr, enableControl, enableMeasure)
        
        # TODO: do the same for the magAddr, pressAddr, and humidAddr
        # NOTE: Reading data from the sensor will look like the following:
        self.accelerometerData = i2cBus.read_i2c_block_data(accelAddr, begAddr, totBytes)
        self.magnetometerData = i2cBus.read_i2c_block_data(magAddr, begAddr, totBytes)
        self.pressureData = i2cBus.read_i2c_block_data(pressAddr, begAddr, totBytes)
        self.humidityData = i2cBus.read_i2c_block_data(humidAddr, begAddr, totBytes)
        
    def displayAccelerometerData(self,data):
        self.accelerometerData = data
        print ('Accelerate Data: ' + self.accelerometerData)
        
    def displayMagnetometerData(self,data):
        self.magnetometerData = data
        print ('Magnetic Data: ' + self.magnetometerData)
    
    def displayPressureData(self,data):
        self.pressureData = data
        print ('Pressure Data: ' + self.pressureData) 
    
    def displayHumidityData(self,data):
        self.humidityData = data
        print('Humidity Data: ' + self.humidityData)
        

    def run(self):
            while True:
                if self.enableEmulator:
                    # NOTE: you must implement these methods
                    self.displayAccelerometerData(self,self.accelerometerData)  
                    self.displayMagnetometerData(self,self.magnetometerData)
                    self.displayPressureData(self,self.pressureData)
                    self.displayHumidityData(self,self.humidityData)
                sleep(self.rateInSec)