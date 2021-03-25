#!/usr/bin/env python
# -*- coding: utf-8 -*-
import subprocess as sp
import sys
import profile

def stop():
    # abs_dir =sp.check_output(['dirname','$ABSPATH'])
    # abs_dir = abs_dir.replace('\n','')

    idle_port = profile.get_idle_port()
    print("> idle_port 구동중인 애플리케이션 pid 확인")
    try:
        idle_pid = sp.check_output(['lsof','-ti','tcp:'+idle_port])
    except sp.CalledProcessError, e:
        idle_pid = ''
    if idle_pid == '':
        print("구동중인 idle 애플리케이션이 없으므로 멈추지 않습니다.")
    else:
        sp.call(['kill','-15',idle_pid])


stop()