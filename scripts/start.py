#!/usr/bin/env python
# -*- coding: utf-8 -*-

import subprocess as sp
import profile

project_name = 'awssns-springboot'
repo = '/data1/awssns-springboot/'

print ('> start deploy')
jar_list = sp.Popen(['sudo','ls','-tr','/data1/awssns-springboot/build/libs/'],stdout=sp.PIPE)
print(jar_list)
jar_name = sp.check_output(['tail','-n','1'],stdin=jar_list.stdout)
jar_name = str.replace(jar_name,'\n','')
print('>'+jar_name)
#sp.call(['chmod', '+x', jar_name])
idle_profile = profile.find_idle_profile()
sp.call(['nohup','java', '-jar', '-Dspring.config.location=classpath:/application.properties,'+
         'classpath:/application-' + idle_profile + '.properties,'+
         'classpath:/application-keys.properties',
         '-Dspring.profiles.active=' + idle_profile,
         repo+jar_name, '&'])
