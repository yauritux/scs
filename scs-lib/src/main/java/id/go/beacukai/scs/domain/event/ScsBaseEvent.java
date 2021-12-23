package id.go.beacukai.scs.domain.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class ScsBaseEvent {
    private String eventId;
    private String eventType;
    private String eventHandler;
    private String eventReferenceId;
    private Date timestamp;
    private String createdBy;

    public ScsBaseEvent(final String id) {
        eventId = id;
        this.eventType = toCamelCase(this.getClass().getName());
        this.eventHandler = this.getClass().getSimpleName() + "Handler";
        this.timestamp = new Date();
    }

    public ScsBaseEvent(final String id, final String refId) {
        eventId = id;
        eventReferenceId = refId;
        this.eventType = toCamelCase(this.getClass().getName());
        this.eventHandler = this.getClass().getSimpleName() + "Handler";
        this.timestamp = new Date();
        this.createdBy = "system";
    }

    public ScsBaseEvent(final String id, final boolean isSimpleEventName) {
        eventId = id;
        this.eventType = toCamelCase(isSimpleEventName ? this.getClass().getSimpleName() : this.getClass().getName());
        this.eventHandler = isSimpleEventName ? this.getClass().getSimpleName() + "Handler" : this.getClass().getName() + "Handler";
        this.timestamp = new Date();
    }

    public ScsBaseEvent(final String id, final boolean isSimpleEventName, final String refId) {
        eventId = id;
        eventReferenceId = refId;
        this.eventType = toCamelCase(isSimpleEventName ? this.getClass().getSimpleName() : this.getClass().getName());
        this.eventHandler = isSimpleEventName ? this.getClass().getSimpleName() + "Handler" : this.getClass().getName() + "Handler";
        this.timestamp = new Date();
        this.createdBy = "system";
    }

    public ScsBaseEvent(final String id, final String createdBy, boolean isSimpleEventName) {
        eventId = id;
        this.createdBy = createdBy;
        this.eventType = toCamelCase(isSimpleEventName ? this.getClass().getSimpleName() : this.getClass().getName());
        this.eventHandler = isSimpleEventName ? this.getClass().getSimpleName() + "Handler" : this.getClass().getName() + "Handler";
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return this.getEventType();
    }

    private String toCamelCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
