#!/usr/bin/env python
# -*- coding: utf-8 -*-

import subprocess as sp
import profile

#
# function switch_proxy() {
#     IDLE_PORT=$(find_idle_port)
#
#     echo "> 전환할 Port: $IDLE_PORT"
#     echo "> Port 전환"
#     echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
#
#     echo "> 엔진엑스 Reload"
#     sudo service nginx reload
# }
def switch():
    idle_port = profile.get_idle_port()
    localhost_url = 'http://127.0.0.1:'+idle_port+';'
    print ('> 전환할 포트 :', idle_port)
    print ('> 포트 전환')
    sp.call(['set','\$service_url',localhost_url,'|','sudo','tee','/etc/nginx/conf.d/service-url.inc'])

    print ('> nginx reload')
    sp.call(['sudo','service','nginx','reload'])

