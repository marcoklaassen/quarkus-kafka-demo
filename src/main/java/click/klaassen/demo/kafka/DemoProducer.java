package click.klaassen.demo.kafka;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Random;

@ApplicationScoped
public class DemoProducer {

    private final Random random = new Random();

    @Outgoing("demo-topic")
    public Multi<String> generate() {
        // Build an infinite stream of random prices
        // It emits a price every second
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .map(x -> String.valueOf(random.nextInt()));
    }

}