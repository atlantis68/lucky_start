#!/bin/bash 

current_path=`pwd`
case "`uname`" in
    Linux)
		bin_abs_path=$(readlink -f $(dirname $0))
		;;
	*)
		bin_abs_path=`cd $(dirname $0); pwd`
		;;
esac
base=${bin_abs_path}/..
logback_configurationFile=$base/conf/logback.xml
export LANG=en_US.UTF-8
export BASE=$base

if [ -f $base/bin/tomcat.pid ] ; then
	echo "found tomcat.pid , Please run stop.sh first ,then startup.sh" 2>&2
    exit 1
fi

if [ ! -d $base/logs] ; then
	mkdir -p $base/logs
fi

## set java path
if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
fi

if [ -z "$JAVA" ]; then
  echo "Cannot find a Java JDK. Please set either set JAVA or put java (>=1.7) in your PATH." 2>&2
  exit 1
fi

str=`file -L $JAVA | grep 64-bit`
if [ -n "$str" ]; then
#	JAVA_OPTS="-server -Xms2048m -Xmx3072m -Xmn1024m -XX:SurvivorRatio=2 -XX:PermSize=96m -XX:MaxPermSize=256m -Xss256k -XX:-UseAdaptiveSizePolicy -XX:MaxTenuringThreshold=15 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError"
#	JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xmn640m -XX:SurvivorRatio=6 -XX:PermSize=96m -XX:MaxPermSize=128m -Xss256k -XX:-UseAdaptiveSizePolicy -XX:MaxTenuringThreshold=15 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError"
#	JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xmn640m -XX:SurvivorRatio=6 -XX:PermSize=96m -XX:MaxPermSize=128m -Xss256k -XX:-UseAdaptiveSizePolicy -XX:MaxTenuringThreshold=15 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -Xloggc:../logs/gc.log"
#	JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xmn640m -XX:SurvivorRatio=4 -XX:PermSize=96m -XX:MaxPermSize=256m -Xss256k -XX:-UseAdaptiveSizePolicy -XX:MaxTenuringThreshold=15 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -Xloggc:../logs/gc.log"
	JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xmn800m -XX:SurvivorRatio=32 -Xss256k -XX:-UseAdaptiveSizePolicy -XX:MaxTenuringThreshold=15 -XX:+DisableExplicitGC -XX:+UseFastAccessorMethods -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -Xloggc:../logs/gc.log"
else
	JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:MaxPermSize=128m "
fi

#JAVA_OPTS=" $JAVA_OPTS -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=7777 -Dcom.sun.management.jmxremote"
JAVA_OPTS=" $JAVA_OPTS -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"
BASELINE_OPTS="-DappName=datacurator-baseline -Dlogback.configurationFile=$logback_configurationFile"

if [ -e $logback_configurationFile ]
then 
	
	for i in $base/lib/*;
		do CLASSPATH=$i:"$CLASSPATH";
	done
 	CLASSPATH="$base/conf:$CLASSPATH";
 	
 	echo "cd to $bin_abs_path for workaround relative path"
  	cd $bin_abs_path
 	
	echo LOG CONFIGURATION : $logback_configurationFile
	echo CLASSPATH :$CLASSPATH
	$JAVA $JAVA_OPTS $JAVA_DEBUG_OPT $BASELINE_OPTS -classpath .:$CLASSPATH com.dmall.lightning.lightning_core.bootstrap.LightningMain 1>>$base/logs/web.log 2>&1 &
	echo $! > $base/bin/tomcat.pid
	
	echo "cd to $current_path for continue"
  	cd $current_path
else 
	echo "log configuration file($logback_configurationFile) is not exist,please create then first!"
fi
