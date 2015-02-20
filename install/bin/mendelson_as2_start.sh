#! /bin/sh
###############################################################################
#
#  Copyright (c) 2000-2012, mendelson-e-commerce GmbH  All Rights Reserved.
#
###############################################################################

CLASSPATH=as2.jar:jetty/start.jar
export CLASSPATH

if [ -d lib ]; then
    JARDIR=lib
    export JARDIR
    for jar in `ls $JARDIR/*.jar $JARDIR/*.zip 2>/dev/null`
    do
        CLASSPATH=$CLASSPATH:$jar
    done
fi
if [ -d lib/mina ]; then
    MINADIR=lib/mina
    export MINADIR
    for jar in `ls $MINADIR/*.jar $MINADIR/*.zip 2>/dev/null`
    do
        CLASSPATH=$CLASSPATH:$jar
    done
fi
if [ -d lib/help ]; then
    HELPDIR=lib/help
    export HELPDIR
    for jar in `ls $HELPDIR/*.jar $HELPDIR/*.zip 2>/dev/null`
    do
        CLASSPATH=$CLASSPATH:$jar
    done
fi
if [ -d lib/vaadin ]; then
    VAADINDIR=lib/vaadin
    export VAADINDIR
    for jar in `ls $VAADINDIR/*.jar $VAADINDIR/*.zip 2>/dev/null`
    do
        CLASSPATH=$CLASSPATH:$jar
    done
fi
if [ -d jetty/lib ]; then
    JETTYLIBDIR=jetty/lib
    export JETTYLIBDIR
    for jar in `ls $JETTYLIBDIR/*.jar $JETTYLIBDIR/*.zip 2>/dev/null`
    do
        CLASSPATH=$CLASSPATH:$jar
    done
fi
if [ -d lib/httpclient ]; then
    HTTPLIBDIR=lib/httpclient
    export HTTPLIBDIR
    for jar in `ls $HTTPLIBDIR/*.jar $HTTPLIBDIR/*.zip 2>/dev/null`
    do
	CLASSPATH=$CLASSPATH:$jar
    done
fi
java -Xmx1024M -Xms92M -classpath $CLASSPATH de.mendelson.comm.as2.AS2 $1 $2 $3 $4 $5 $6 $7

