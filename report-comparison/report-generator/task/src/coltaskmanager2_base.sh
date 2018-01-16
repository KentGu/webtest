#!/bin/bash

pushd ${COLLINE_BASE_DIR}/bin
. ./env.sh
popd

CLASSPATH="${COLLINE_BASE_DIR}/bin/lib/*:${JBOSS_HOME}/bin/client/jboss-client.jar:${COLLINE_BASE_DIR}/bin/conf/"
${JAVA} -classpath ${CLASSPATH} -Djava.naming.factory.url.pkgs=org.jboss.ejb.client.naming -DUSERNAME=sa -DPASSWORD=password -DENCRYPTED=N -DPROP_FILE=colline.properties com.lombardrisk.f3.F3TaskManagerWithApacheCLIParser "$@"