basepath=$(cd `dirname $0`; pwd)
sudo mkdir /usr/shs-server/
sudo cp -r $basepath/* /usr/shs-server

sudo cp /usr/shs-server/default/shs /usr/bin/
sudo cp /usr/shs-server/shs-server.service /usr/lib/systemd/system
`mkdir /var/www/`
`mkdir /var/www/html/`
sudo systemctl daemon-reload
