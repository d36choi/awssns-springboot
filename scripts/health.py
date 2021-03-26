#!/usr/bin/env python
# -*- coding: utf-8 -*-

import subprocess as sp
import switch, profile
import time,requests

idle_port = profile.get_idle_port()
profile_url = 'http://localhost:'+idle_port+'/profile'
print ('> health check start ('+profile_url+')')
print ('> IDLE_PORT:'+idle_port)
response = ''
print('> sleep 5 seconds for waiting project to run')
time.sleep(5)
for i in range(10):
    try:
        print(requests.get(profile_url))
        response = sp.check_output(['curl','-s',profile_url])
	print('> res:'+response)
    except sp.CalledProcessError, e:
        print ('> health check 응답 없음...')
    time.sleep(2)
    if response == '1' or response == '2':
        print ('> 검증 성공 \n> switch start: -> :'+idle_port)
        switch.switch()
        exit()


print ('> health check failed')
exit()

