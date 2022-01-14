package id.go.beacukai.scs.query.infrastructure.adapter.input.eventhandler;

import com.google.gson.Gson;
import id.go.beacukai.scs.domain.event.DocumentCreatedEvent;
import id.go.beacukai.scs.domain.event.DocumentHeaderUpdatedEvent;
import id.go.beacukai.scs.query.domain.service.port.input.DocumentBaseEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public class DocumentHeaderUpdatedEventHandler implements DocumentBaseEventHandler {

    private final DocumentHeaderUpdatedEvent event;
    private final ApplicationContext applicationContext;

    public DocumentHeaderUpdatedEventHandler(String payload, ApplicationContext applicationContext) {
        this.event = new Gson().fromJson(payload, DocumentHeaderUpdatedEvent.class);
        this.applicationContext = applicationContext;
    }

    @Override
    public void handle() {

    }
}
