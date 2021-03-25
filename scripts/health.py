#!/usr/bin/env python
# -*- coding: utf-8 -*-

import subprocess as sp
import switch, profile


idle_port = profile.get_idle_port()
profile_url = 'http://localhost:'+idle_port+'/profile'
print ('> health check start')
print ('> IDLE_PORT:',idle_port)

for i in range(10):
    try:
        response = sp.check_output(['curl','-s',profile_url])
    except sp.CalledProcessError, e:
        print ('> idle port\'s process is not running... \n health check end')
        exit()
    if response == '1' or '2':
        print ('> switch start')
        switch.switch()
        exit()


print ('> health check failed')
exit()

