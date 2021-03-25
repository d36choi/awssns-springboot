#!/usr/bin/env python
# -*- coding: utf-8 -*-

import subprocess as sp
import profile

project_name = 'awssns-springboot'
repo = '/data1/awssns-springboot/'

sp.call(['cp', repo + 'build/libs/*.jar', repo])
jar_name = sp.check_output(['ls', '-tr', repo + '*.jar', '|', 'tail', '-n', '1'])
print(jar_name)
sp.call(['chmod', '+x', jar_name])
idle_profile = profile.find_idle_profile()
sp.call(['nohup', 'java', '-jar \\', '-Dspring.config.location=classpath:/application.properties,',
         'classpath:/application-' + idle_profile + '.properties,',
         'classpath:/application-key.properties',
         '-Dspring.profiles.active=' + idle_profile,
         jar_name+' > '+repo+'/nohup.out 2>&1 &'])
