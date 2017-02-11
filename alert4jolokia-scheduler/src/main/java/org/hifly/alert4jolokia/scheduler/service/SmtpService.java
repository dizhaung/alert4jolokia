package org.hifly.alert4jolokia.scheduler.service;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.MultiPartEmail;
import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.hifly.alert4jolokia.common.domain.action.EmailAction;
import org.hifly.alert4jolokia.common.smtp.SmtpConfig;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SmtpService {

    @Inject
    @Alert4JolokiaLogger
    Logger logger;

    public MultiPartEmail createMultiPartMsg(EmailAction template) throws Exception {
        MultiPartEmail email = new MultiPartEmail();

        SmtpConfig config = template.getSmtpConfig();

        email.setHostName(config.getSmtp());
        email.setSmtpPort(config.getPort());
        if(config.isSmtpAutheticate())
            email.setAuthenticator(new DefaultAuthenticator(config.getSmtpUser(), config.getSmtpPwd()));
        email.setSSLOnConnect(config.isSmtpAutheticate());
        email.setFrom(config.getSmtpFrom());
        email.setSubject(template.getSubject());
        if(StringUtils.isEmpty(template.getText()))
            //apache email doesn't accept EMPTY as message
            email.setMsg(" ");
        else
            email.setMsg(template.getText());

        if(CollectionUtils.isEmpty(template.getTo()) && CollectionUtils.isNotEmpty(config.getSmtpDefaultTo())) {
            for(String str: config.getSmtpDefaultTo())
                if(StringUtils.isNotEmpty(str))
                    email.addTo(str);
        }


        return email;

    }

}
