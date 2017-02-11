package org.hifly.alert4jolokia.common.domain.alert;


import java.text.MessageFormat;

public class JMXBeanFactory {

    public static JMXBean fillJMXBean(JMXBean jmxBean) {
        String mbean;
        switch (jmxBean.getName()) {
            case HEAP_MEMORY:
                jmxBean.setMbean("java.lang:type=Memory");
                jmxBean.setAttribute("HeapMemoryUsage");
                break;
            case INUSE_DATASOURCE_WILDFLY:
                mbean = "jboss.as:data-source={0},statistics=pool,subsystem=datasources";
                jmxBean.setMbean(MessageFormat.format(mbean, jmxBean.getAdditionalArgs().get(0)));
                jmxBean.setAttribute("InUseCount");
                break;
            case INUSE_DATASOURCE_TOMEE:
                mbean = "openejb.management:ObjectType=datasources,DataSource={0}";
                jmxBean.setMbean(MessageFormat.format(mbean, jmxBean.getAdditionalArgs().get(0)));
                jmxBean.setAttribute("NumActive");
                break;
            case ACTIVEMQ_ENQUEUE_COUNT:
                mbean = "org.apache.activemq:type=Broker,brokerName={0},destinationType={1},destinationName={2}";
                jmxBean.setMbean(MessageFormat.format(mbean, jmxBean.getAdditionalArgs().get(0), jmxBean.getAdditionalArgs().get(1), jmxBean.getAdditionalArgs().get(2)));
                jmxBean.setAttribute("EnqueueCount");
                break;
            case ACTIVEMQ_MEMORY_PERCENTAGE:
                mbean = "org.apache.activemq:type=Broker,brokerName={0}";
                jmxBean.setMbean(MessageFormat.format(mbean, jmxBean.getAdditionalArgs().get(0)));
                jmxBean.setAttribute("MemoryPercentUsage");
                break;
            default:
                jmxBean.setMbean("java.lang:type=Memory");
                jmxBean.setAttribute("HeapMemoryUsage");
                break;
        }
        return jmxBean;
    }


}
