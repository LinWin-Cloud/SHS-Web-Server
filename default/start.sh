basepath=$(cd `dirname $0`; pwd)
cd $basepath/../bin/
nohup `cat /usr/shs-server/config/bootJDK.txt` -jar http_service.jar &
#../shs/bin/shs-server -jar http_service.jar