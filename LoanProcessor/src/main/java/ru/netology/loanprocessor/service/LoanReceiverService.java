package ru.netology.loanprocessor.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.loanprocessor.event.LoanEvent;

@Service
@RequiredArgsConstructor
public class LoanReceiverService {
    private static final String topicCreateLoan = "${topic.send-loan}";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";
    private final ModelMapper modelMapper;

    private final RabbitSenderService rabbitSenderService;

    private final LoanCalculator loanCalculator;

    @Transactional
    @KafkaListener(topics = topicCreateLoan
            , groupId = kafkaConsumerGroupId
            , properties = {"spring.json.value.default.type=ru.netology.loanprocessor.event.LoanEvent"})
    public LoanEvent createLoan(LoanEvent loanEvent) {
        System.out.println("!!!!! Caught loan with id: " + loanEvent.getId());
        System.out.println("!!!!! Calculating...");

        boolean approved = false;
        try {
            approved = loanCalculator.approved(loanEvent);
        }catch (NumberFormatException ex){
            System.out.println("Incorrect data?");
        }

        String message = approved ? "approved" : "declined";
        try {
            rabbitSenderService.sendMessage(String.format("%d:%s", loanEvent.getId(), message));
        }catch (Exception ex){
            System.out.println(ex);
        }
        return loanEvent;
    }

}
