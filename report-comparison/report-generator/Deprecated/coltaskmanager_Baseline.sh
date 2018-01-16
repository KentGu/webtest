#!/bin/bash
. `dirname $0`/env.sh
. `dirname $0`/databasetype.sh
CLASSPATH="${ROOT}/jboss/server/${PROFILE_NAME}/lib/jdom.jar:./ojdbc6.jar:./LRSClient.jar:./LRS3rdPartyClient.jar:./jboss-messaging/jboss-messaging-client.jar:./jboss-messaging/javassist.jar:./jboss-messaging/jboss-aop-jdk50.jar:./jboss-messaging/trove.jar"

java -classpath ${CLASSPATH} -Djboss.bind.address=172.20.31.241 -Djboss.jndi.port=1099 -Djboss.hajndi.port=1100 -Djboss.scheduler.manualtask.timeout=20 -Djboss.scheduler.manualtask.debug=true -Djava.naming.factory.initial=org.jnp.interfaces.NamingContextFactory -Djava.naming.provider.url=jnp://172.20.31.241:1099/ -Djava.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces -Djnp.disableDiscovery=true -Djava.security.auth.login.config=clientAuth.conf -DUSERNAME=$2 -DPASSWORD=password -DENCRYPTED=N com.lombardrisk.f3.F3TaskManager ${DATABASE_TYPE}.properties $1

