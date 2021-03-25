#!/usr/bin/env python
# -*- coding: utf-8 -*-
import subprocess as sp
import sys


def find_idle_profile():
    localhost_profile = 'http://localhost:8081/profile'
    res_code = sp.check_output(['curl', '-s', '-o', '/dev/null', '-w', '"%{http_code}"', ('%s' % localhost_profile)])
    res_code = int(res_code.replace('"', ''))

    if res_code >= 400:
        current_profile = '2'
    else:
        current_profile = sp.check_output(['curl', '-s', ('%s' % localhost_profile)])

    if current_profile == '1':
        idle_profile = '2'
    else:
        idle_profile = '1'

    return idle_profile


def get_idle_port():
    idle_profile = find_idle_profile()
    if idle_profile == '1':
        return '8081'
    elif idle_profile == '2':
        return '8082'
    else:
        return 'err'


find_idle_profile()
