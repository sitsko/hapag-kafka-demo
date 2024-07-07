package me.sitsko.quarkus;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


@Slf4j
@ApplicationScoped
public class AvroConsumer {

	//Case 1. single mode

	// Option 1.a Read payload
	@Incoming("containers")
	public void message(Container container) {
		log.info("received container as PAYLOAD: {}", container);
	}

	// Option 1.b Read message
	@Incoming("containers")
	public CompletionStage<Void> consume(Message<Container> msg) {
		// access record metadata
		var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
		// process the message payload.
		var container = msg.getPayload();

		var headers = Arrays.toString(metadata.getHeaders().toArray());
		var offset = metadata.getOffset();

		log.info("received container as MESSAGE: {}, metadata (headers {}, offset {})", container, headers, offset);
		// Acknowledge the incoming message (commit the offset)
		return msg.ack();

	}

	// Option 1.c Kafka consumer record
	@Incoming("containers")
	public void consume(ConsumerRecord<String, Container> record) {
		var key = record.key(); // Can be `null` if the incoming record has no key
		var container = record.value(); // Can be `null` if the incoming record has no value
		var topic = record.topic();
		int partition = record.partition();
		var offset = record.offset();

		log.info("received container as ConsumerRecord: {}, metadata (key {}, offset {})", container, key, offset);
	}

	//Case 2. Batch mode
	// Option 2.a Kafka consumer record
	@Incoming("containers")
	@RunOnVirtualThread
	public void message(List<Container> containers) {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		var containersInfo = containers.stream()
				.map(Container::toString)
				.collect(Collectors.joining(","));
		log.info("received containers: {}, {}", containers.size(), containersInfo);
	}

	@Incoming("container-event")
	@RunOnVirtualThread
	public void messageEvent(List<ContainerRepositionEvent> events) {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		var eventInfo = events.stream()
				.map(ContainerRepositionEvent::toString)
				.collect(Collectors.joining(","));
		log.info("received event(s): {}, {}", events.size(), eventInfo);
	}
}
