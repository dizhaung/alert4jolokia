package org.hifly.alert4jolokia.common.domain.action;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailAction.class, name = "mail"),
        @JsonSubTypes.Type(value = SlackAction.class, name = "slack")})
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public abstract class Action implements Serializable, Cloneable {

    protected String text;
    protected String templateText;

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return SerializationUtils.clone(this);
    }

}
