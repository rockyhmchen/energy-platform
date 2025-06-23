#!/usr/bin/env sh

#java \
#-Xmx300m \
#-Xss512k \
#-XX:CICompilerCount=2 \
#-XX:+UseContainerSupport \
#-cp app:app/lib/* \
#com.zendo.backend.Application

java \
-cp app:app/lib/* \
com.zendo.energydataapi.Application
