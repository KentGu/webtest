#!/bin/bash
. `dirname $0`/env.sh
. `dirname $0`/jboss_version.sh

CLASSPATH="${COLLINE_BASE_DIR}/jboss/server/${PROFILE_NAME}/lib/jdom.jar:${COLLINE_BASE_DIR}/bin/${OJDBC_JAR}:${COLLINE_BASE_DIR}/bin/LRSClient.jar:${COLLINE_BASE_DIR}/bin/LRS3rdPartyClient.jar:${COLLINE_BASE_DIR}/bin/jboss-messaging/jboss-messaging-client.jar:${COLLINE_BASE_DIR}/bin/jboss-messaging/javassist.jar:${COLLINE_BASE_DIR}/bin/jboss-messaging/jboss-aop-jdk50.jar:${COLLINE_BASE_DIR}/bin/jboss-messaging/trove.jar"

java -classpath ${CLASSPATH} -Djboss.version.string=${JBOSS_VERSION} -Djboss.jndi.port=1099 -Djboss.bind.address=127.0.0.1 -Djboss.hajndi.port=1100 -Djava.naming.factory.initial=org.jnp.interfaces.NamingContextFactory -Djava.naming.provider.url=jnp://127.0.0.1:1099/ -Djava.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces -Djnp.disableDiscovery=true -Djava.security.auth.login.config=clientAuth.conf -DUSERNAME=sa -DPASSWORD=password -DENCRYPTED=N -DPROP_FILE=colline.properties com.lombardrisk.f3.F3TaskManagerWithApacheCLIParser "$@"