#!/usr/bin/env python
# -*- coding: utf-8 -*-
import subprocess as sp
import profile

def stop():

    idle_port = profile.get_idle_port()
    print("> idle_port 구동중인 애플리케이션 pid 확인")
    try:
        idle_pid = sp.check_output(['lsof','-ti','tcp:'+idle_port])
    except sp.CalledProcessError, e:
        idle_pid = ''
    if idle_pid == '':
        print("구동중인 idle 애플리케이션이 없으므로 멈추지 않습니다.")
    else:
        idle_pid = idle_pid.replace('"','').replace('\n','')
        print('> '+idle_pid+' 프로세스 제거')
        sp.call(['kill','-15',idle_pid])

