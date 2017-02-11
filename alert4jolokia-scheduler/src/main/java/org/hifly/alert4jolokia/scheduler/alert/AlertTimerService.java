package org.hifly.alert4jolokia.scheduler.alert;

import org.apache.commons.collections.CollectionUtils;
import org.hifly.alert4jolokia.common.domain.alert.Alert;
import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.hifly.alert4jolokia.scheduler.core.timer.AbstractTimer;
import org.hifly.alert4jolokia.scheduler.core.timer.ScheduleExpressionFactory;
import org.hifly.alert4jolokia.scheduler.core.timer.TimerException;
import org.hifly.alert4jolokia.scheduler.persistence.AlertJsonStore;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class AlertTimerService {

    @Inject
    @Alert4JolokiaLogger
    Logger logger;

    @Inject
    @Named("alert-timer")
    AbstractTimer timer;

    @Inject
    AlertJsonStore alertJsonStore;

    private final static String JOBS_PREFIX =  "ALERT_JOBS";

    public void initJobs() {
        try {
            timer.cancelTimers(JOBS_PREFIX);

            List<Alert> jobs = alertJsonStore.getAlerts();

            if(CollectionUtils.isNotEmpty(jobs))
                jobs.forEach(j -> timer.createTimer(j, ScheduleExpressionFactory.create(j.getScheduleExprName()), logger));

        } catch (TimerException e) {
            throw new IllegalStateException("Can't delete previous timers");
        }
    }



}
