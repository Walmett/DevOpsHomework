#!/bin/bash

NAME=$(basename "$0")
CSV_DATE=$(date +"%Y-%m-%d")
CSV_TIME=$(date +"%H-%M-%S")
INTERVAL=20 #seconds

start() {
	nohup bash "$0" BACKGROUND </dev/null &>/dev/null & 
	echo $!
}

stop() {
	pkill -f "$NAME"
}

status() {
	if pgrep -f "$NAME BACKGROUND" > /dev/null; then
		echo "Running"
	else
		echo "Not running"
	fi
}

monitor() {
	while true; do
		CURRENT_DATE=$(date +"%Y-%m-%d")
		CURRENT_TIME=$(date +"%H-%M-%S")
		if [ "$CSV_DATE" != "$CURRENT_DATE" ]; then
			CSV_DATE=$CURRENT_DATE
			CSV_TIME=$CURRENT_TIME
		fi
		CSV_NAME="$CSV_DATE-$CSV_TIME.csv"
		
		FILESYSTEM=$(df -ih | awk 'NR==3 {print $1}')
		USE=$(df -ih | awk 'NR==3 {print $5}')
		INODES=$(df -ih | awk 'NR==3 {print $2}')

		echo "$CURRENT_TIME,$FILESYSTEM,$USE,$INODES" >> "$CSV_NAME"

		sleep $INTERVAL
	done
}


case "$1" in
	START)
		start
		;;
	STOP)
		stop
		;;

	STATUS)
		status
		;;
	BACKGROUND)
		monitor
		;;
	*)
		echo "Use START|STOP|STATUS"
		exit 1
		;;
esac
