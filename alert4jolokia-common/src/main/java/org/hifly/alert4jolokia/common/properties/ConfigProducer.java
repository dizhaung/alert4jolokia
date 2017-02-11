package org.hifly.alert4jolokia.common.properties;


import org.apache.commons.configuration.Configuration;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;


public class ConfigProducer {

    @Inject
    ConfigurationProvider configurationProvider;


    @Singleton
    @Produces
    @HttpClientConfig
    public Configuration produceHttpClientConfig(){
        return configurationProvider.provideConfiguration("httpclient.properties");
    }

    @Singleton
    @Produces
    @ActionTemplateConfig
    public Configuration produceActionTemplateConfig(){
        return configurationProvider.provideConfiguration("action_templates.properties");
    }

}
