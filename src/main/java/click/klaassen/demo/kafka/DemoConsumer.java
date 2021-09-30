package click.klaassen.demo.kafka;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DemoConsumer {

    @Incoming("demo-topic")
    public void consume(String message) {
        System.out.println(message);
    }

}