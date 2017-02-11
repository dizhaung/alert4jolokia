package org.hifly.alert4jolokia.scheduler.alert;

import org.hifly.alert4jolokia.common.domain.alert.Alert;
import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.hifly.alert4jolokia.scheduler.core.timer.AbstractTimer;
import org.hifly.alert4jolokia.scheduler.persistence.AlertJsonStore;
import org.hifly.alert4jolokia.scheduler.service.AlertService;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.inject.Inject;
import javax.inject.Named;


@Stateless
@Named("alert-timer")
public class AlertTimer extends AbstractTimer {

    @Inject
    AlertService alertService;

    @Inject
    AlertJsonStore alertJsonStore;

    @Inject
    @Alert4JolokiaLogger
    Logger logger;


    @Override
    @Timeout
    public void execute(Timer timer) {
        Alert payload = (Alert) timer.getInfo();

        try {
            alertService.evaluate(payload);
            alertJsonStore.save(payload);
        } catch (Exception e) {
            logger.info("Job {} failed {}", payload.getUuid(), e);
        }

    }
}
