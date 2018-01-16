package com.lombardrisk.util;

import com.lombardrisk.colline.agreement.service.ColAgreementServiceRemote;
import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.util.PropHelper;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class RemoteEJBUtil {
    private final static Logger logger = LoggerFactory.getLogger(RemoteEJBUtil.class);
    private static final InitialContext CONTEXT;

    static {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        InitialContext initialContext = null;
        try {
            initialContext = new InitialContext(jndiProperties);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        CONTEXT = initialContext;
        Properties properties = new Properties();
        properties.put("endpoint.name", PropHelper.getProperty("endpoint.name"));
        properties.put("java.naming.factory.url.pkgs", PropHelper.getProperty("java.naming.factory.url.pkgs"));
        properties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", PropHelper.getProperty("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED"));
        properties.put("remote.connections", PropHelper.getProperty("remote.connections"));
        URL url = null;
        try {
            url = new URL(TestCaseManager.getTestCase().getTestEnvironment().getApplicationServers().get(0).getUrl());
            properties.put("remote.connection.default.host", url.getHost());
        } catch (MalformedURLException e) {
            logger.error("Get URL failed. Use default value in the properties file", e);
            properties.put("remote.connection.default.host", PropHelper.getProperty("remote.connection.default.host"));
        }
        properties.put("remote.connection.default.port" , PropHelper.getProperty("remote.connection.default.port"));
        properties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", PropHelper.getProperty("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS"));
        properties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", PropHelper.getProperty("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT"));
        properties.put("remote.connection.default.username", PropHelper.getProperty("remote.connection.default.username"));
        properties.put("remote.connection.default.password", PropHelper.getProperty("remote.connection.default.password"));
        PropertiesBasedEJBClientConfiguration configuration = new PropertiesBasedEJBClientConfiguration(properties);
        final ContextSelector<EJBClientContext> ejbClientContextSelector = new ConfigBasedEJBClientContextSelector(configuration);
        final ContextSelector<EJBClientContext> previousSelector = EJBClientContext.setSelector(ejbClientContextSelector);
    }

    public static ColAgreementServiceRemote getColAgreementServiceRemote() throws Exception {
        if (CONTEXT == null) {
            logger.error("InitialContext is null");
            throw new Exception();
        }
        final String jndiName = "ejb:colline/collateral/ColAgreementService" + "!com.lombardrisk.colline.agreement.service.ColAgreementServiceRemote";
        return (ColAgreementServiceRemote) CONTEXT.lookup(jndiName);
    }
}
