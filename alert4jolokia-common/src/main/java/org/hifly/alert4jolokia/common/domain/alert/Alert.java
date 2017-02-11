package org.hifly.alert4jolokia.common.domain.alert;


import org.apache.commons.lang.SerializationUtils;
import org.hifly.alert4jolokia.common.domain.action.Action;

import java.io.Serializable;
import java.util.List;

public class Alert implements Serializable, Cloneable {

    public enum Status {
        FINISHED, STARTED
    }

    private String uuid;
    private String name;
    private String description;
    private Threshold threshold;
    private List<Action> actions;
    private ScheduleDefinition scheduleExprName;
    private Server target;
    private JMXBean jmxBean;
    private Status status = Status.STARTED;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Threshold getThreshold() {
        return threshold;
    }

    public void setThreshold(Threshold threshold) {
        this.threshold = threshold;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Server getTarget() {
        return target;
    }

    public void setTarget(Server target) {
        this.target = target;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public JMXBean getJmxBean() {
        return jmxBean;
    }

    public void setJmxBean(JMXBean jmxBean) {
        this.jmxBean = jmxBean;
    }

    public ScheduleDefinition getScheduleExprName() {
        return scheduleExprName;
    }

    public void setScheduleExprName(ScheduleDefinition scheduleExprName) {
        this.scheduleExprName = scheduleExprName;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Alert)) return false;
        Alert other = (Alert) o;
        return (this.uuid.equalsIgnoreCase(other.uuid));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return SerializationUtils.clone(this);
    }
}
