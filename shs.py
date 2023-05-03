import os
import sys

if __name__ == "__main__":
        if len(sys.argv) <= 1:
            print(""" |-----LinwinSoft Shs Server-----|
 1- start               Start SHS.
 2- stop                Stop SHS.
 3. proxy_start         Start Proxy Server.
 4. proxy_stop          Stop Proxy Server.
 |-------------------------------|""")
            exit(0)
        if sys.argv[1] == 'start':
            os.system("sudo /usr/shs-server/default/start.sh")
            exit(0)

        if sys.argv[1] == 'stop':
            os.system('sudo killall -9 shs-server')
            exit(0)

        if sys.argv[1] == 'proxy_start':
             os.system("sudo /usr/shs-server/default/proxy_start.sh")
             exit(0)
        
        if sys.argv[1] == 'proxy_stop':
            os.system("sudo killall -9 shs-proxy")
            exit(0)

        else:
            print(""" |-----LinwinSoft Shs Server-----|
 1- start               Start SHS.
 2- stop                Stop SHS.
 3. proxy_start         Start Proxy Server.
 4. proxy_stop          Stop Proxy Server.
 |-------------------------------|""")


