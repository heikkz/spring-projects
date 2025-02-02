package com.apress.reactor;

import com.apress.reactor.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Configuration
public class FluxExample {

    private static final Logger log = LoggerFactory.getLogger(FluxExample.class);

    @Bean
    public CommandLineRunner runFluxExample() {
        return args -> {
            EmitterProcessor<ToDo> stream = EmitterProcessor.create();

            Mono<List<ToDo>> promise = stream
                    .filter(ToDo::isCompleted)
                    .doOnNext(s -> log.info("FLUX >>> ToDo: {}", s.getDescription()))
                    .collectList()
                    .subscribeOn(Schedulers.single());

            stream.onNext(new ToDo("Read a Book", true));
            stream.onNext(new ToDo("Listen Classical Music",true));
            stream.onNext(new ToDo("Workout in the Mornings"));
            stream.onNext(new ToDo("Organize my room", true));
            stream.onNext(new ToDo("Go to the Car Wash", true));
            stream.onNext(new ToDo("SP1 2018 is coming" , true));

            stream.onComplete();
            promise.block();
        };
    }
}
