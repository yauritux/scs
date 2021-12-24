package id.go.beacukai.scs.query.infrastructure.adapter.input.eventhandler;

import com.google.gson.Gson;
import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
import id.go.beacukai.scs.query.domain.service.port.input.DocumentBaseEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public class DocumentCreatedEventHandler implements DocumentBaseEventHandler {

    private final DocumentCreatedEvent event;
    private final ApplicationContext applicationContext;

    public DocumentCreatedEventHandler(String payload, ApplicationContext applicationContext) {
        this.event = new Gson().fromJson(payload, DocumentCreatedEvent.class);
        this.applicationContext = applicationContext;
    }

    @Override
    public void handle() {

    }
}
