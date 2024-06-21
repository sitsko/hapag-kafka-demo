package me.sitsko.quarkus;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
@Slf4j
public class AvroProducer {
	private final AtomicInteger counter = new AtomicInteger(0);

	@Channel("containers")
	Emitter<Container> emitter;


	@Scheduled(every = "3s", delayed = "5s")
	public void publishContainer() {
		final var random = new Random();
		var container = Container.builder()
				.id(String.valueOf(counter.incrementAndGet()))
				.type(ContainerType.values()[random.nextInt(4)].toString())
				.info(LocalDateTime.now().toString())
				.build();
		emitter.send(container);
		log.info("send container: {}", container);
	}
}
