package org.hifly.alert4jolokia.scheduler.service;



public class SlackInputBody {

    public SlackInputBody(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;
}
