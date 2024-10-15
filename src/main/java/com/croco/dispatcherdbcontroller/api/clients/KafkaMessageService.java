package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.kafka.DefaultProducer;
import com.croco.dispatcherdbcontroller.kafka.MessageHandler;
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

    public KafkaMessageService(FilialService kafkaFilialService,
                               IncidentService kafkaIncidentService,
                               UserService kafkaUserService,
                               MapService kafkaMapService,
                               MediaService kafkaMediaService,
                               ReporterService kafkaReporterService,
                               TaskService kafkaTaskService,
                               WorkerService kafkaWorkerService, DefaultProducer kafkaControllerProducer) {
        this.kafkaControllerProducer = kafkaControllerProducer;
        handlers.put(EntityType.FILIAL, new FilialHandler(kafkaFilialService, kafkaControllerProducer));
//        handlers.put(EntityType.INCIDENT, new IncidentHandler(kafkaIncidentService));
//        handlers.put(EntityType.USER, new UserHandler(kafkaUserService));
//        handlers.put(EntityType.MAP, new MapHandler(kafkaMapService));
//        handlers.put(EntityType.MEDIA, new MediaHandler(kafkaMediaService));
//        handlers.put(EntityType.REPORTER, new ReporterHandler(kafkaReporterService));
//        handlers.put(EntityType.TASK, new TaskHandler(kafkaTaskService));
//        handlers.put(EntityType.USERSESSION, new UserSessionHandler(kafkaUserSessionService));
//        handlers.put(EntityType.WORKER, new WorkerHandler(kafkaWorkerService));
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