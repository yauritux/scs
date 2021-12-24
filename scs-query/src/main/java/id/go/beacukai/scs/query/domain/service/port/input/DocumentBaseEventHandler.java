package id.go.beacukai.scs.query.domain.service.port.input;

public interface DocumentBaseEventHandler {
    String EVENT_HANDLER_PACKAGE = "id.go.beacukai.scs.query.infrastructure.adapter.input.eventhandler";

    void handle();
}
