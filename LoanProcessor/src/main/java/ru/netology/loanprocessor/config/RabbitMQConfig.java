package ru.netology.loanprocessor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {
    public static String QUEUE_CONFIG = "spring.rabbitmq.queue-name";

    /*@Value("${s/*pring.rabbitmq.queue-name}")
    public static String RABBIT_QUEUE_NAME;*/

    private final Environment environment;

    @Bean
    public Queue myQueue() {
        return new Queue(environment.getProperty(QUEUE_CONFIG), true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
