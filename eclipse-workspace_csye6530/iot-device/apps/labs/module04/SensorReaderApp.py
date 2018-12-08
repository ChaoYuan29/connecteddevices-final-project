'''
Created on Oct 13, 2018

@author: andyyuan
'''

from time import sleep
from labs.module04 import I2CSenseHatAdaptor
#from labs.module02 import SmtpClientConnector


i2CSenseHatAdaptor = I2CSenseHatAdaptor.I2CSenseHatAdaptor()

i2CSenseHatAdaptor.daemon = True
print('- - - - - - - - - - - - - - - - - - - - - - - - ')
print("Starting system performance app daemon thread...")
i2CSenseHatAdaptor.enableEmulator = True
i2CSenseHatAdaptor.start()

while (True):
    sleep(10)
    pass