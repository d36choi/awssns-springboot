#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Press �똽R to execute it or replace it with your code.
# Press Double �눉 to search everywhere for classes, files, tool windows, actions, and settings.
import subprocess
import sys

def git_pull_and_build():
    print('git pull...')
    subprocess.call(['git','pull'])

    print('gradle build...')
    subprocess.call(["./gradlew","build"])

    print('jar file deploy...')
    java_call = ['java','-jar','build/libs/awssns-0.0.1-SNAPSHOT.jar']

    subprocess.call(java_call)
    return

# Press the green button in the gutter to run the script.
if __name__ == '__main__':

    git_pull_and_build()
# See PyCharm help at https://www.jetbrains.com/help/pycharm/

