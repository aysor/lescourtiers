package ru.netology.loanprocessor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.netology.loanprocessor.config.RabbitMQConfig;

@Service
@RequiredArgsConstructor
public class RabbitSenderService {
    private final Environment environment;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        try {
            String routingKey = environment.getProperty(RabbitMQConfig.QUEUE_CONFIG);
            System.out.println("!!!!! RoutingKey: " + routingKey);
            rabbitTemplate.convertAndSend(routingKey, message);
            System.out.println("!!!!! Sent message: " + message);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}
