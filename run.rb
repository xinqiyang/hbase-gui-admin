#!/bin/bash

#run scipt
. /etc/profile

min_heap_size="1300m"
max_heap_size="1300m"

SCRIPT="$0"
while [ -h "$SCRIPT" ] ; do
  ls=`ls -ld "$SCRIPT"`
  # Drop everything prior to ->
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    SCRIPT="$link"
  else
    SCRIPT=`dirname "$SCRIPT"`/"$link"
  fi
done

targetdir=`dirname "$SCRIPT"`
targetdir=`cd $targetdir; pwd`

#echo $targetdir
#exit

#cd $targetdir

#if [ "$1" == "start" ];then
#  ./compileapp.sh
#fi

cd $targetdir


classpath=.
for jarfile in `ls lib`
do
    classpath=$classpath:lib/$jarfile
done

start()
{
  echo $classpath
  nohup java -classpath $classpath -jar HBaseXploer.jar  > hbasexplorer.log  &
  echo $! > hbasexplorer1.pid
}
stop()
{
  kill -15 `cat hbasexplorer1.pid`
}

case $1 in
"restart")
   stop
   start
;;
"start")
   start
;;
"stop")
   stop
;;
*) echo "only accept params start|stop|restart" ;;
esac
