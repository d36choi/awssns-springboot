#!/usr/bin/env python
# -*- coding: utf-8 -*-
import subprocess as sp
import time


class launcher:
    idle_profile = None
    idle_port = None
    localhost_profile = 'http://localhost/profile'

    def __init__(self):
        self.idle_profile = self.find_idle_profile()
        self.idle_port = self.get_idle_port()
    def find_idle_profile(self):
        res_code = ''
        try:
            res_code = sp.check_output(
            ['curl', '-s', '-o', '/dev/null', '-w', '"%{http_code}"', ('%s' % self.localhost_profile)])
        except sp.CalledProcessError, e:
            res_code = "404"
        res_code = int(res_code.replace('"', ''))

        if res_code >= 400:
            current_profile = '2'
        else:
            current_profile = sp.check_output(['curl', '-s', ('%s' % self.localhost_profile)])

        if current_profile == '1':
            idle_profile = '2'
        else:
            idle_profile = '1'

        return idle_profile

    def get_idle_port(self):

        if self.idle_profile == '1':
            return '8081'
        elif self.idle_profile == '2':
            return '8082'
        else:
            return 'err'

    def stop(self):

        print("> idle_port 구동중인 애플리케이션 pid 확인")
        try:
            idle_pid = sp.check_output(['lsof', '-ti', 'tcp:' + self.idle_port])
        except sp.CalledProcessError, e:
            idle_pid = ''
        if idle_pid == '':
            print("구동중인 idle 애플리케이션이 없으므로 멈추지 않습니다.")
        else:
            idle_pid = idle_pid.replace('"', '').replace('\n', '')
            print('> ' + idle_pid + ' 프로세스 제거')
            sp.call(['kill', '-15', idle_pid])

    def health_check(self):
        profile_url = 'http://localhost:' + self.idle_port + '/profile'
        print ('> health check start (' + profile_url + ')')
        response = ''
        print('> sleep 5 seconds for waiting project to run')
        time.sleep(5)
        for i in range(10):
            try:
                response = sp.check_output(['curl', '-s', profile_url])
                print('> res:' + response)
            except sp.CalledProcessError, e:
                print ('> health check 응답 없음...')
            time.sleep(2)
            if response == '1' or response == '2':
                print ('> 검증 성공 \n> switch start: -> :' + self.idle_port)
                self.switch()
                return

        print ('> health check failed')

    def switch(self):
        localhost_url = 'http://127.0.0.1:' + self.idle_port + ';'
        print ('> 포트 전환')

        url_pipe = sp.Popen(['echo', 'set $service_url ' + localhost_url], stdout=sp.PIPE)
        sp.call(['sudo', 'tee', '/etc/nginx/conf.d/service-url.inc'], stdin=url_pipe.stdout)

        print ('> nginx reload')
        sp.call(['sudo', 'service', 'nginx', 'reload'])

    def launch(self):
        print ('> ### STOP IDLE PROCESS ###')
        self.stop()
        print ('> ### START NEW PROCESS ###')
        sp.call(['./start.sh'])
        print ('> ### HEALTH CHECK AND SWITCH PROXY ###')
        self.health_check()

if __name__ == '__main__':

    launcher = launcher()
    launcher.launch()
