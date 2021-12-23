package id.go.beacukai.scs.command.domain.port.output;

public interface DocumentStreamingService<T> {
    void publish(String topic, T t);
}
