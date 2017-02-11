package org.hifly.alert4jolokia.common.domain.action;


import org.hifly.alert4jolokia.common.smtp.SmtpConfig;

import java.io.Serializable;
import java.util.List;

public class EmailAction extends Action implements Serializable {

    private String subject;
    private List<String> to;
    private SmtpConfig smtpConfig;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public SmtpConfig getSmtpConfig() {
        return smtpConfig;
    }

    public void setSmtpConfig(SmtpConfig smtpConfig) {
        this.smtpConfig = smtpConfig;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }
}
