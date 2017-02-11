package org.hifly.alert4jolokia.scheduler.service;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.hifly.alert4jolokia.common.domain.action.SlackAction;
import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.hifly.alert4jolokia.scheduler.http.HTTPResponse;
import org.hifly.alert4jolokia.scheduler.http.HttpClientManager;
import org.json.JSONObject;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SlackService {

    @Inject
    @Alert4JolokiaLogger
    Logger logger;

    @Inject
    HttpClientManager httpClientManager;

    private static final String CONTENT_TYPE_LABEL = "content-type";
    private static final String CONTENT_TYPE_FORM = "application/json";

    public HTTPResponse sendToChannel(SlackAction action) {

        HTTPResponse response = new HTTPResponse();

        try {
            HttpPost post = new HttpPost(action.getUrl());

            SlackInputBody slackInputBody = new SlackInputBody(action.getText());

            JSONObject jsonObject = new JSONObject(slackInputBody);
            String body = jsonObject.toString();

            StringEntity params = new StringEntity(body);
            post.addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_FORM);
            post.setEntity(params);

            httpClientManager.executeRequest(post);
        } catch (Exception ex) {
            return response;
        }

        response.setStatus(200);

        return response;
    }
}
