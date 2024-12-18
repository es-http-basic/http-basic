#!/bin/sh

BASE_PATH="$(dirname $0)"
CFG_FILE="${BASE_PATH}/cfg.ini"

######################
JAVA_EXE=`grep JAVA_EXE ${CFG_FILE} | cut -f2 -d=`

${JAVA_EXE} -cp ${BASE_PATH}/elasticsearch6-http-basic-plugin.jar com.cleafy.elasticsearch6.plugins.http.PassParamGenerator
