import os
import sys

if __name__ == "__main__":
        if len(sys.argv) <= 1:
            print(""" |-----LinwinSoft Shs Server-----|
 1- start               Start SHS.
 2- stop                Stop SHS.
 |-------------------------------|""")
            exit(0)
        if sys.argv[1] == 'start':
            os.system("/usr/shs-server/default/start.sh")
            exit(0)

        if sys.argv[1] == 'stop':
            os.system('killall -9 shs-server')
            exit(0)

        else:
            print(""" |-----LinwinSoft Shs Server-----|
 1- start               Start SHS.
 2- stop                Stop SHS.
 |-------------------------------|""")


