package org.hifly.alert4jolokia.scheduler.core.timer;


import org.hifly.alert4jolokia.common.domain.alert.ScheduleDefinition;

import javax.ejb.ScheduleExpression;

public class ScheduleExpressionFactory {

    public static ScheduleExpression create(ScheduleDefinition scheduleDefinition) {
        switch (scheduleDefinition) {
            case EVERY30SECONDS:
                return every30Seconds();
            case EVERY5MINUTES:
                return every5Minutes();
            default:
                return every5Minutes();
        }

    }

    private static ScheduleExpression every30Seconds() {
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.second("*/30");
        scheduleExpression.minute("*");
        scheduleExpression.hour("*");
        return scheduleExpression;
    }

    private static ScheduleExpression every5Minutes() {
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.second(0);
        scheduleExpression.minute("*/5");
        scheduleExpression.hour("*");
        return scheduleExpression;
    }
}
