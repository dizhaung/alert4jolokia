package org.hifly.alert4jolokia.scheduler.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.hifly.alert4jolokia.common.domain.alert.Server;
import org.hifly.alert4jolokia.common.domain.alert.Alert;
import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.hifly.alert4jolokia.scheduler.http.HttpClientManager;
import org.json.JSONObject;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class JolokiaService {

    private static final String CONTENT_TYPE_LABEL = "content-type";
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    @Inject
    HttpClientManager httpClientManager;

    @Inject
    @Alert4JolokiaLogger
    Logger logger;

    public JolokiaResponse post(Alert alert) {

        JolokiaResponse response = new JolokiaResponse();
        String result;

        try {
            HttpPost post = new HttpPost(getJolokiaUrl(alert.getTarget()));
            JolokiaInputBody jolokiaInputBody = new JolokiaInputBody();
            jolokiaInputBody.setMbean(alert.getJmxBean().getMbean());
            jolokiaInputBody.setAttribute(alert.getJmxBean().getAttribute());
            if(StringUtils.isNotBlank(alert.getThreshold().getCondition().getName()))
                jolokiaInputBody.setPath(alert.getThreshold().getCondition().getName());

            JSONObject jsonObject = new JSONObject(jolokiaInputBody);
            String body = jsonObject.toString();

            StringEntity params = new StringEntity(body);
            post.addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_FORM);
            post.setEntity(params);

            if(alert.getTarget().isBasicAuth()) {
                String toEncode = alert.getTarget().getUser() + ":" + alert.getTarget().getPassword();
                byte [] encoding = Base64.encodeBase64(toEncode.getBytes());
                post.setHeader("Authorization", "Basic " + new String(encoding));
            }

            result = httpClientManager.executeRequest(post);
        } catch (Exception ex) {
            logger.error("Error invoking Jolokia", ex);
            return response;
        }

        response.setStatus(200);
        response.setContent(result);

        return response;
    }

    private String getJolokiaUrl(Server server) {
        StringBuilder sb = new StringBuilder();
        sb.append(server.getProtocol());
        sb.append("://");
        sb.append(server.getHost());
        sb.append(":");
        sb.append(server.getPort());
        sb.append("/");
        sb.append(server.getJolokiaContext());
        sb.append("/");
        return sb.toString();
    }
}
