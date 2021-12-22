package id.go.beacukai.scs.domain.service.port.output;

public interface DocumentStreamingService<T> {
    void publish(T t);
}
