package org.hifly.alert4jolokia.common.smtp;

import java.util.List;

public class SmtpConfig {

    private String smtp;
    private Integer port;
    private boolean sslEnabled;
    private boolean smtpAutheticate;
    private String smtpUser;
    private String smtpPwd;
    private String smtpDefaultSubject;
    private List<String> smtpDefaultTo;
    private List<String> smtpDefaultCC;
    private List<String> smtpDefaultBCC;
    private String smtpFrom;

    public String getSmtp() {
        return smtp;
    }

    protected void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public Integer getPort() {
        return port;
    }

    protected void setPort(Integer port) {
        this.port = port;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    protected void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    protected void setSmtpUser(String smtpUser) {
        this.smtpUser = smtpUser;
    }

    public String getSmtpPwd() {
        return smtpPwd;
    }

    protected void setSmtpPwd(String smtpPwd) {
        this.smtpPwd = smtpPwd;
    }

    public List<String> getSmtpDefaultTo() {
        return smtpDefaultTo;
    }

    public void setSmtpDefaultTo(List<String> smtpDefaultTo) {
        this.smtpDefaultTo = smtpDefaultTo;
    }

    public boolean isSmtpAutheticate() {
        return smtpAutheticate;
    }

    public void setSmtpAutheticate(boolean smtpAutheticate) {
        this.smtpAutheticate = smtpAutheticate;
    }

    public String getSmtpDefaultSubject() {
        return smtpDefaultSubject;
    }

    public void setSmtpDefaultSubject(String smtpDefaultSubject) {
        this.smtpDefaultSubject = smtpDefaultSubject;
    }

    public String getSmtpFrom() {
        return smtpFrom;
    }

    public void setSmtpFrom(String smtpFrom) {
        this.smtpFrom = smtpFrom;
    }

    public List<String> getSmtpDefaultCC() {
        return smtpDefaultCC;
    }

    public void setSmtpDefaultCC(List<String> smtpDefaultCC) {
        this.smtpDefaultCC = smtpDefaultCC;
    }

    public List<String> getSmtpDefaultBCC() {
        return smtpDefaultBCC;
    }

    public void setSmtpDefaultBCC(List<String> smtpDefaultBCC) {
        this.smtpDefaultBCC = smtpDefaultBCC;
    }

}
