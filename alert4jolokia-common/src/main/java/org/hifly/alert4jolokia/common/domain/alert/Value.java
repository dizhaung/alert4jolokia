package org.hifly.alert4jolokia.common.domain.alert;


public class Value {

    private Type type = Type.COMPARE;
    private String value;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public enum Type {
        COMPARE, COMPARE_PREVIOUS_VALUE
    }


}
