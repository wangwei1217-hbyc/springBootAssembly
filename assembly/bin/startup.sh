#!/bin/sh
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
BASEDIR=`cd "$PRGDIR/.." >/dev/null; pwd`

SERVER_NAME="springBootAssembly"
echo "$SERVER_NAME starting..."

# Reset the REPO variable. If you need to influence this use the environment setup file.
REPO=


# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  Darwin*) darwin=true
           if [ -z "$JAVA_VERSION" ] ; then
             JAVA_VERSION="CurrentJDK"
           else
             echo "Using Java version: $JAVA_VERSION"
           fi
		   if [ -z "$JAVA_HOME" ]; then
		      if [ -x "/usr/libexec/java_home" ]; then
			      JAVA_HOME=`/usr/libexec/java_home`
			  else
			      JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home
			  fi
           fi       
           ;;
esac

if [ -z "$JAVA_HOME" ] ; then
  if [ -r /etc/gentoo-release ] ; then
    JAVA_HOME=`java-config --jre-home`
  fi
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly." 1>&2
  echo "  We cannot execute $JAVACMD" 1>&2
  exit 1
fi

if [ -z "$REPO" ]
then
  REPO="$BASEDIR"/lib
fi

LOGS_DIR=$BASEDIR/logs
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi

STDOUT_FILE=$LOGS_DIR/stdout.log

# CLASSPATH="$BASEDIR"/conf:"$REPO"/redis-util-1.3.1.RELEASE.jar:"$REPO"/framework-cache-1.0.0.RELEASE.jar:"$REPO"/spring-boot-starter-2.0.3.RELEASE.jar:"$REPO"/spring-boot-2.0.3.RELEASE.jar:"$REPO"/spring-context-5.0.7.RELEASE.jar:"$REPO"/spring-boot-autoconfigure-2.0.3.RELEASE.jar:"$REPO"/spring-boot-starter-logging-2.0.3.RELEASE.jar:"$REPO"/logback-classic-1.2.3.jar:"$REPO"/logback-core-1.2.3.jar:"$REPO"/log4j-to-slf4j-2.10.0.jar:"$REPO"/log4j-api-2.10.0.jar:"$REPO"/jul-to-slf4j-1.7.25.jar:"$REPO"/javax.annotation-api-1.3.2.jar:"$REPO"/spring-core-5.0.7.RELEASE.jar:"$REPO"/spring-jcl-5.0.7.RELEASE.jar:"$REPO"/snakeyaml-1.19.jar:"$REPO"/spring-boot-starter-web-2.0.3.RELEASE.jar:"$REPO"/spring-boot-starter-json-2.0.3.RELEASE.jar:"$REPO"/jackson-databind-2.9.6.jar:"$REPO"/jackson-annotations-2.9.0.jar:"$REPO"/jackson-core-2.9.6.jar:"$REPO"/jackson-datatype-jdk8-2.9.6.jar:"$REPO"/jackson-datatype-jsr310-2.9.6.jar:"$REPO"/jackson-module-parameter-names-2.9.6.jar:"$REPO"/spring-boot-starter-tomcat-2.0.3.RELEASE.jar:"$REPO"/tomcat-embed-core-8.5.31.jar:"$REPO"/tomcat-embed-el-8.5.31.jar:"$REPO"/tomcat-embed-websocket-8.5.31.jar:"$REPO"/hibernate-validator-6.0.10.Final.jar:"$REPO"/validation-api-2.0.1.Final.jar:"$REPO"/jboss-logging-3.3.2.Final.jar:"$REPO"/classmate-1.3.4.jar:"$REPO"/spring-web-5.0.7.RELEASE.jar:"$REPO"/spring-beans-5.0.7.RELEASE.jar:"$REPO"/spring-webmvc-5.0.7.RELEASE.jar:"$REPO"/spring-aop-5.0.7.RELEASE.jar:"$REPO"/spring-expression-5.0.7.RELEASE.jar:"$REPO"/spring-boot-starter-jdbc-2.0.3.RELEASE.jar:"$REPO"/HikariCP-2.7.9.jar:"$REPO"/spring-jdbc-5.0.7.RELEASE.jar:"$REPO"/spring-tx-5.0.7.RELEASE.jar:"$REPO"/spring-boot-devtools-2.0.3.RELEASE.jar:"$REPO"/druid-spring-boot-starter-1.1.9.jar:"$REPO"/druid-1.1.9.jar:"$REPO"/slf4j-api-1.7.25.jar:"$REPO"/jedis-2.9.0.jar:"$REPO"/commons-pool2-2.4.2.jar:"$REPO"/commons-lang3-3.7.jar:"$REPO"/commons-lang-2.6.jar:"$REPO"/mysql-connector-java-5.1.46.jar:"$REPO"/log4j-1.2.17.jar:"$REPO"/springBootAssembly-1.0-SNAPSHOT.jar
LIB_JARS=`ls $REPO|grep .jar|awk '{print "'$REPO'/"$0}'|tr "\n" ":"`
CLASSPATH="$BASEDIR"/conf:$LIB_JARS
ENDORSED_DIR=
if [ -n "$ENDORSED_DIR" ] ; then
  CLASSPATH=$BASEDIR/$ENDORSED_DIR/*:$CLASSPATH
fi

if [ -n "$CLASSPATH_PREFIX" ] ; then
  CLASSPATH=$CLASSPATH_PREFIX:$CLASSPATH
fi

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  [ -n "$HOME" ] && HOME=`cygpath --path --windows "$HOME"`
  [ -n "$BASEDIR" ] && BASEDIR=`cygpath --path --windows "$BASEDIR"`
  [ -n "$REPO" ] && REPO=`cygpath --path --windows "$REPO"`
fi


exec nohup "$JAVACMD" $JAVA_OPTS -server -Xmx1G -Xms1G \
  -classpath "$CLASSPATH" \
  -Dapp.name="startup" \
  -Dapp.pid="$$" \
  -Dapp.repo="$REPO" \
  -Dapp.home="$BASEDIR" \
  -Dbasedir="$BASEDIR" \
  com.wangwei.Application \
  "$@" > $STDOUT_FILE 2>&1 &

echo "$SERVER_NAME start success!"
