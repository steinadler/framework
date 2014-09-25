#!/bin/sh

# clear tomcat log
tomcat_home=/opt/AIO/Service/webservice/apache-tomcat-7.0.54
filelist=`ls $tomcat_home/logs/ | grep catalina`
filecount=`ls $tomcat_home/logs/ | grep catalina | wc -l`
filelimit=15 # keep 15 files
if [ $filecount -gt $filelimit ]; then
	delcount=$(($filecount-$filelimit))
	i=0
	for file in $filelist
	do 
		if [ $i -lt $delcount ]; then
			rm -rf $tomcat_home/logs/$file
		fi
		i=$(($i+1))
	done
fi
echo "" > $tomcat_home/logs/catalina.out

# clear nginx log
nginx_home=/opt/AIO/Service/webservice/nginx1.7.4
day=`date '+%d'`
if [ $day -eq 1 ]; then # clear logs every day of month in 1
	echo "" > $nginx_home/logs/error.log
	echo "" > $nginx_home/logs/access.log
fi
