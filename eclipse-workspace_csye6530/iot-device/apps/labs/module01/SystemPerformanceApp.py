'''
Created on 2018年9月15日

@author: andyyuan
'''

from time import sleep
from labs.module01 import SystemPerformanceAdaptor
sysPerfAdaptor = SystemPerformanceAdaptor.SystemPerformanceAdaptor()
#SystemPerformanceAdaptor.SystemPerformanceAdaptor().daemon = True

print("Starting system performance app daemon thread...")
sysPerfAdaptor.enableAdaptor = True
#SystemPerformanceAdaptor.SystemPerformanceAdaptor().daemon = True
#SystemPerformanceAdaptor.SystemPerformanceAdaptor().start()
sysPerfAdaptor.start()

while (True):
    sleep(5)
    pass