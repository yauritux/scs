package id.go.beacukai.scs.query.domain.service.port.input;

import id.go.beacukai.scs.domain.event.ScsBaseEvent;

public interface DocumentBaseEventHandler {
    void handle(ScsBaseEvent event);
}
