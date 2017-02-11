package org.hifly.alert4jolokia.scheduler.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.hifly.alert4jolokia.common.domain.alert.Alert;
import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AlertJsonStore {

    @Inject
    @Alert4JolokiaLogger
    Logger logger;

    private List<Alert> alerts;

    @PostConstruct
    public void loadAlerts() {
        try {
            String configFilesDir = System.getProperty("jboss.server.config.dir");
            String alertFile = configFilesDir + File.separator + "alerts.json";
            Path path = Paths.get(alertFile);
            if (Files.exists(path)) {


                InputStream is = new FileInputStream(alertFile);
                String jsonTxt = IOUtils.toString(is);

                ObjectMapper objectMapper = new ObjectMapper();

                alerts = objectMapper.readValue(
                        jsonTxt,
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, Alert.class));
            }

        } catch (Throwable ex) {
            logger.error("Can't load json alerts file", ex);
        }

    }

    public void save(Alert alert) {

        try {

            if (CollectionUtils.isEmpty(alerts))
                alerts = new ArrayList<>();
            else {
              if(alerts.contains(alert))
                  return;
            }

            alert.getActions().forEach(action -> action.setText(action.getTemplateText()));
            alerts.add(alert);

            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(alerts);

            String configFilesDir = System.getProperty("jboss.server.config.dir");
            String alertFile = configFilesDir + File.separator + "alerts.json";
            try (FileWriter file = new FileWriter(alertFile)) {
                file.write(jsonInString);
            }
        } catch(Throwable ex) {
            logger.error("Can't save alert", ex);
        }
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

}
