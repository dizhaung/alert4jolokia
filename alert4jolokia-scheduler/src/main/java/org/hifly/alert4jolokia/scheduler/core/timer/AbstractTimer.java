package org.hifly.alert4jolokia.scheduler.core.timer;

import org.hifly.alert4jolokia.common.domain.alert.Alert;
import org.hifly.alert4jolokia.common.domain.alert.JMXBeanFactory;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import java.util.UUID;


public abstract class AbstractTimer {

    @Resource
    protected TimerService timerService;


    public void createTimer(Alert payload, ScheduleExpression scheduleExpression, Logger logger) {
        final String uuid = UUID.randomUUID().toString();
        payload.setJmxBean(JMXBeanFactory.fillJMXBean(payload.getJmxBean()));
        payload.setUuid(uuid);
        TimerConfig config = new TimerConfig();
        config.setPersistent(false);
        config.setInfo(payload);
        try {
            timerService.createCalendarTimer(scheduleExpression, config);
        } catch(Exception ex) {
           logger.error("Can't create timer!", ex);
        }
    }

    public abstract void execute(Timer timer);


    public Alert cancelTimer(String uuid) throws TimerException {

        if (timerService.getTimers() != null) {
            for(Timer timer: timerService.getTimers()) {
                Alert payload = (Alert) timer.getInfo();
                if(payload.getUuid().equals(uuid)) {
                    //clone payload
                    Alert clonedPayload;
                    try {
                        clonedPayload = (Alert)payload.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new TimerException(e);
                    }

                    timer.cancel();
                    return clonedPayload;
                }
            }
        }

        return null;
    }

    public void cancelTimers(String prefix) throws TimerException {
        if (timerService.getTimers() != null) {
            for(Timer timer: timerService.getTimers()) {
                Alert payload = (Alert) timer.getInfo();
                if(payload.getUuid().startsWith(prefix) && payload.getStatus() == Alert.Status.FINISHED)
                    timer.cancel();
            }
        }
    }

}
