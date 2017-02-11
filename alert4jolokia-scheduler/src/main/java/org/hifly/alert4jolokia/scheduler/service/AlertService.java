package org.hifly.alert4jolokia.scheduler.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.Configuration;
import org.hifly.alert4jolokia.common.domain.action.Action;
import org.hifly.alert4jolokia.common.domain.action.EmailAction;
import org.hifly.alert4jolokia.common.domain.action.SlackAction;
import org.hifly.alert4jolokia.common.domain.alert.Alert;
import org.hifly.alert4jolokia.common.domain.alert.Operator;
import org.hifly.alert4jolokia.common.domain.alert.ThresholdCondition;
import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.hifly.alert4jolokia.common.properties.ActionTemplateConfig;
import org.hifly.alert4jolokia.scheduler.persistence.AlertJsonStore;
import org.json.JSONObject;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;

@ApplicationScoped
public class AlertService {

    @Inject
    private JolokiaService jolokiaService;

    @Inject
    private SmtpService smtpService;

    @Inject
    private SlackService slackService;

    @Inject
    private AlertJsonStore alertJsonStore;

    @Inject
    @ActionTemplateConfig
    Configuration configuration;

    @Inject
    @Alert4JolokiaLogger
    Logger logger;

    public void evaluate(Alert alert) {
        JolokiaResponse response = jolokiaService.post(alert);
        if (response.getStatus() == 200) {

            try {

                JSONObject json = new JSONObject(response.getContent());

                ThresholdCondition condition = alert.getThreshold().getCondition();
                Long value = json.getLong("value");
                switch (condition.getValue().getType()) {
                    case COMPARE_PREVIOUS_VALUE:
                        List<Alert> alerts = alertJsonStore.getAlerts();
                        if (CollectionUtils.isNotEmpty(alerts)) {
                            Alert temp = new Alert();
                            temp.setUuid(alert.getUuid());
                            if (alerts.indexOf(temp) != -1) {
                                temp = alerts.get(alerts.indexOf(temp));

                                Long previousValue = Long.valueOf(temp.getThreshold().getCondition().getValue().getValue());

                                logger.info(String.format("Job %s for %s, actual value: %d, previous value: %d",
                                        alert.getUuid(),
                                        alert.getName(),
                                        value,
                                        previousValue));

                                if (evaluateOperator(condition.getOperator(), value, previousValue))
                                    dispatchActions(alert, value, "check_previous_value");
                                else {
                                    alert.getThreshold().getCondition().getValue().setValue(String.valueOf(value));
                                    alertJsonStore.save(alert);
                                }

                            } else
                                alert.getThreshold().getCondition().getValue().setValue(String.valueOf(value));
                        } else
                            alert.getThreshold().getCondition().getValue().setValue(String.valueOf(value));
                        break;
                    case COMPARE:
                        Long thresholdValue = Long.valueOf(condition.getValue().getValue());
                        logger.info(String.format("Job %s for %s, actual value: %d, threshold value: %d",
                                alert.getUuid(),
                                alert.getName(),
                                value,
                                thresholdValue));

                        if (evaluateOperator(condition.getOperator(), value, thresholdValue))
                            dispatchActions(alert, value, "compare_greater");
                        break;

                }


            } catch(Exception ex) {
                logger.error("Error in evaluate Jolokia response, content is {}", response.getContent());
            }
        }

    }

    private boolean evaluateOperator(Operator operator, Long actualValue, Long comparisonValue) {
        boolean result = false;
        switch (operator) {
            case GREATER:
                if (actualValue > comparisonValue)
                    result = true;
                break;
            case GREATER_EQUAL:
                if (actualValue >= comparisonValue)
                    result = true;
                break;
            case LESS:
                if (actualValue < comparisonValue)
                    result = true;
                break;
            case LESS_EQUAL:
                if (actualValue <= comparisonValue)
                    result = true;
                break;
            case EQUAL:
                if (actualValue == comparisonValue)
                    result = true;
                break;
        }
        return result;
    }

    private void dispatchActions(Alert alert, Long value, String templateKey) {
        alert.getActions().forEach(action -> {
            action.setTemplateText(configuration.getString(templateKey));
            action.setText(MessageFormat.format(configuration.getString(templateKey),
                    alert.getTarget().getName(),
                    alert.getJmxBean().getName(),
                    value,
                    alert.getThreshold().getCondition().getValue().getValue()));
            logger.info(String.format("--->Job %s for %s, condition raised, dispatch action %s",
                    alert.getUuid(),
                    alert.getName(),
                    action.getClass().getName()));
            executeAlertAction(action);
        });
    }

    private void executeAlertAction(Action action) {
        if (action instanceof EmailAction) {
            EmailAction emailAction = (EmailAction) action;
            //TODO define mail templates
            emailAction.setSubject("Test");

            try {
                smtpService.createMultiPartMsg(emailAction);
            } catch (Exception e) {
                logger.error("Can't send email", e);
            }
        } else if (action instanceof SlackAction) {
            SlackAction slackAction = (SlackAction) action;
            try {
                slackService.sendToChannel(slackAction);
            } catch (Exception e) {
                logger.error("Can't send to slack", e);
            }
        }
    }


}
