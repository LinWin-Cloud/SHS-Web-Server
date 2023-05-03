basepath=$(cd `dirname $0`; pwd)
cd $basepath/../bin/
nohup ../shs/bin/shs-proxy -jar proxy_service.jar &
#../shs/bin/shs-server -jar http_service.jar