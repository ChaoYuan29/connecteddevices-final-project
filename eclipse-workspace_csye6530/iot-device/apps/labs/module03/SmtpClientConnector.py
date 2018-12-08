'''
Created on Oct 12, 2018

@author: andyyuan
'''


import threading
import smtplib

from labs.common import ConfigUtil
from labs.common import ConfigConst

from email.mime.text import MIMEText 
from email.mime.multipart import MIMEMultipart


#sensorData = SensorData.SensorData()


class SmtpClientConnector (threading.Thread):
    def __init__(self):
        self.config = ConfigUtil.ConfigUtil('')
        self.config.loadConfig()
        print('Configuration data...\n' + str(self.config)) 
        print('============= Setting Done! =============')
        
    #HOST_KEY,PORT_KEY,FROM_ADDRESS_KEY,TO_ADDRESS_KEY,USER_AUTH_TOKEN_KEY
    def publishMessage(self, topic, data):
        host = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.HOST_KEY)
        port = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.PORT_KEY)
        fromAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY)
        toAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.TO_ADDRESS_KEY)
        authToken = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY)
        
        msg = MIMEMultipart()  #mimeMultipart
        msg['From'] = fromAddr
        msg['To'] = toAddr
        msg['Subject'] = topic
        msgBody = str(data)
        msg.attach(MIMEText(msgBody))   #mimeText  
        msgText = msg.as_string()
        # send e-mail notification
        
        smtpServer = smtplib.SMTP_SSL(host, port)
        smtpServer.ehlo()
        smtpServer.login(fromAddr, authToken)
        smtpServer.sendmail(fromAddr, toAddr, msgText)
        smtpServer.close()