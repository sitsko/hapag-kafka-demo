package me.sitsko.quarkus;

import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
@Slf4j
public class AvroProducer {
	private final AtomicInteger containerId = new AtomicInteger(0);
	private final AtomicInteger containerEventId = new AtomicInteger(0);

	@Channel("containers")
	Emitter<Container> emitter;  //CDI should be package-private

	@Scheduled(every = "1s", delayed = "5s")
	public void publishContainer() {
		var random = new Random();
		var container = Container.builder()
				.id(containerId.incrementAndGet())
				.type(ContainerType.values()[random.nextInt(4)].toString())
				.info(LocalDateTime.now().toString())
				.build();
		emitter.send(container); // send async
		log.info("send container: {}", container);
	}


	//async sending
	@Outgoing("container-event")
	public Multi<ContainerRepositionEvent> generateEvent() {
		// Build an infinite stream of ContainerRepositionEvent
		return Multi.createFrom()
				.ticks()
				.every(Duration.ofSeconds(1))
				.map(x -> ContainerRepositionEvent.builder()
						.eventId(containerEventId.incrementAndGet())
						.build());
	}


}

