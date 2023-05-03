import os
import sys

if __name__ == "__main__":
    try:
        if sys.argv[1] == 'start':
            os.system("/usr/shs-server/default/start.sh")

        else:
            print("""
 |-----LinwinSoft Shs Server-----|
 1- start               Start SHS.
 2- stop                Stop SHS.
 3- version             Show Version Information.
 |-------------------------------|
            """)
    except:
        print("""
 |-----LinwinSoft Shs Server-----|
 1- start               Start SHS.
 2- stop                Stop SHS.
 3- version             Show Version Information.
 |-------------------------------|
            """)


