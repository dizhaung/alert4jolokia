package org.hifly.alert4jolokia.common.domain.alert;


import java.io.Serializable;
import java.util.List;

public class Threshold implements Serializable {

    private ThresholdCondition condition;

    public ThresholdCondition getCondition() {
        return condition;
    }

    public void setCondition(ThresholdCondition condition) {
        this.condition = condition;
    }
}
