package br.com.leonardozv.examples;

import br.com.leonardozv.examples.services.FakeDataProducerService;
import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;

@Component
public class SpringKafkaBatchProducerRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringKafkaBatchProducerRunner.class);

    private FakeDataProducerService fakeDataProducerService;

    public SpringKafkaBatchProducerRunner(FakeDataProducerService fakeDataProducerService) {
        this.fakeDataProducerService = fakeDataProducerService;
    }

    @Override
    public void run(String... args) throws IOException {

        String topic = "accounting-journal-entry-created";

        Schema schema = new Schema.Parser().parse("{\"namespace\": \"example.avro\", \"type\": \"record\", \"name\": \"User\", \"fields\": [{\"name\": \"name\", \"type\": \"string\"}]}");

        String key = null;

        String header = null;

        String payload = "{\"name\": \"testUser\" }";

        Integer batches = 1;

        Long eventsPerBatch = 1L;

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        this.fakeDataProducerService.generateAndProduceEvents(topic, schema, key, header, payload, batches, eventsPerBatch);

        stopWatch.stop();

        long amountEventsProduced = getAmountEventsProduced(batches, eventsPerBatch);

        double amountEventsProducedPerSecond = getAmountEventsProducedPerSecond(amountEventsProduced, stopWatch.getTotalTimeSeconds());

        log.info("{} message(s) | {} second(s) | {} messages/s.", amountEventsProduced, stopWatch.getTotalTimeSeconds(), amountEventsProducedPerSecond);

    }

    private long getAmountEventsProduced(Integer batches, Long events) {
        return batches * events;
    }

    private double getAmountEventsProducedPerSecond(long amountEventsProduced, double totalTimeSeconds) {
        return amountEventsProduced / totalTimeSeconds;
    }

}
