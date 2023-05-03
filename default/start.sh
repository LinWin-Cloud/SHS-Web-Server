basepath=$(cd `dirname $0`; pwd)
cd $basepath/../bin/
nohup ../shs/bin/shs-server -jar http_service.jar &