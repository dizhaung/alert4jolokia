package org.hifly.alert4jolokia.web.rest;

import org.hifly.alert4jolokia.common.domain.alert.Alert;
import org.hifly.alert4jolokia.common.logging.Alert4JolokiaLogger;
import org.hifly.alert4jolokia.scheduler.core.timer.AbstractTimer;
import org.hifly.alert4jolokia.scheduler.core.timer.ScheduleExpressionFactory;
import org.hifly.alert4jolokia.scheduler.persistence.AlertJsonStore;
import org.hifly.alert4jolokia.web.util.RestStatusCode;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/alert")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlertResource {

    @Inject
    @Named("alert-timer")
    AbstractTimer timer;

    @Inject
    AlertJsonStore alertJsonStore;

    @Inject
    @Alert4JolokiaLogger
    Logger logger;

    @POST
    @Path("/")
    public Response alert(Alert alert) {
        timer.createTimer(alert, ScheduleExpressionFactory.create(alert.getScheduleExprName()), logger);
        return Response.status(RestStatusCode.CODE_200.code()).build();
    }

    @GET
    @Path("/")
    public Response alertLists() {
        List<Alert> alerts = alertJsonStore.getAlerts();
        return Response.status(RestStatusCode.CODE_200.code()).entity(alerts).build();
    }
}
