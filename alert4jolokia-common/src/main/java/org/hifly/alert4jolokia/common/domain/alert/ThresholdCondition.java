package org.hifly.alert4jolokia.common.domain.alert;


import java.io.Serializable;

public class ThresholdCondition implements Serializable {

    private String name;

    private Operator operator;

    private Value value;

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
