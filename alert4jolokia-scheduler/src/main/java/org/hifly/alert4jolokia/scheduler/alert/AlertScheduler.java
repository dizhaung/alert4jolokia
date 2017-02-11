package org.hifly.alert4jolokia.scheduler.alert;

import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.hifly.alert4jolokia.scheduler.core.timer.AbstractTimer;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Named;


@Startup
@Singleton
public class AlertScheduler {

    @Inject
    @Alert4JolokiaLogger
    Logger logger;

    @Inject
    AlertTimerService alertTimerService;

    @Inject
    @Named("alert-timer")
    AbstractTimer timer;

    @PostConstruct
    public void init() {
        alertTimerService.initJobs();
    }

}
