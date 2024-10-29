package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
import com.croco.dispatcherdbcontroller.kafka.handlers.IncidentHandler;
import com.croco.dispatcherdbcontroller.kafka.handlers.MapHandler;
import com.croco.dispatcherdbcontroller.kafka.handlers.MediaHandler;
import com.croco.dispatcherdbcontroller.kafka.handlers.ReporterHandler;
import com.croco.dispatcherdbcontroller.kafka.handlers.TaskHandler;
import com.croco.dispatcherdbcontroller.kafka.handlers.UserHandler;
import com.croco.dispatcherdbcontroller.kafka.handlers.WorkerHandler;
import com.croco.dispatcherdbcontroller.kafka.handlers.HealthCheckHandler;
import com.croco.dispatcherdbcontroller.kafka.model.EntityType;
import com.croco.dispatcherdbcontroller.kafka.model.KafkaMessage;
import org.springframework.stereotype.Service;
import com.croco.dispatcherdbcontroller.kafka.handlers.FilialHandler;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaMessageService {
    private final Map<EntityType, MessageHandler> handlers = new HashMap<>();
    private final DefaultProducer kafkaControllerProducer;

    public KafkaMessageService(FilialService filialService,
                               IncidentService incidentService,
                               UserService userService,
                               MapService mapService,
                               MediaService mediaService,
                               ReporterService reporterService,
                               TaskService taskService,
                               WorkerService workerService, DefaultProducer kafkaControllerProducer) {
        this.kafkaControllerProducer = kafkaControllerProducer;
        handlers.put(EntityType.FILIAL, new FilialHandler(filialService, kafkaControllerProducer));
        handlers.put(EntityType.INCIDENT, new IncidentHandler(incidentService, kafkaControllerProducer));
        handlers.put(EntityType.USER, new UserHandler(userService, kafkaControllerProducer));
        handlers.put(EntityType.MAP, new MapHandler(mapService, kafkaControllerProducer));
        handlers.put(EntityType.MEDIA, new MediaHandler(mediaService, kafkaControllerProducer));
        handlers.put(EntityType.REPORTER, new ReporterHandler(reporterService, kafkaControllerProducer));
        handlers.put(EntityType.TASK, new TaskHandler(taskService, kafkaControllerProducer));
        handlers.put(EntityType.WORKER, new WorkerHandler(workerService, kafkaControllerProducer));
        handlers.put(EntityType.HEALTHCHECK, new HealthCheckHandler(kafkaControllerProducer));
        handlers.put(EntityType.FIELDSERVICETEAM, new HealthCheckHandler(kafkaControllerProducer));
    }

    public void handleMessage(KafkaMessage data) {
        MessageHandler handler = handlers.get(data.entityType);
        if (handler != null) {
            handler.handle(data);
        } else {
            System.out.println("Unsupported entity type: " + data.entityType);
        }
    }
}