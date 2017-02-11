package org.hifly.alert4jolokia.common.logging;

import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


public class LoggerProducer {

    @Produces
    @ApplicationScoped
    @Alert4JolokiaLogger
    public org.slf4j.Logger produceDefaultLogger() {
        return LoggerFactory.getLogger("ALERT4JOLOKIA");
    }

}
