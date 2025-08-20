package ru.netology.loanproducer.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.netology.loanproducer.dto.LoanDto;
import ru.netology.loanproducer.entity.LoanEntity;
import ru.netology.loanproducer.event.LoanSendEvent;
import ru.netology.loanproducer.repository.LoanRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanProducerService {

    @Value("${topic.send-loan}")
    private String sendClientTopic;

    private final ModelMapper modelMapper;

    private final LoanRepository loanRepository;

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public Long createLoan(LoanDto loanDto){
        LoanEntity entity = saveToDb(loanDto);
        loanDto.setId(entity.getId());
        sendLoan(loanDto);
        return loanDto.getId();
    }

    public String getStatus(Long loanId){
        Optional<LoanEntity> request = loanRepository.findById(loanId);
        String result = request.get().getStatus();
        return result;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.queue-name}", durable = "true"),
            exchange = @Exchange(value = "myExchange", type = "topic", durable = "true")
    ))
    public void processOrder(String response) {
        String[] data = response.split(":");
        Long loanId = Long.parseLong(data[0]);
        String status = data[1];
        System.out.println("Received message: " + response);
        loanRepository.updateStatus(status, loanId);
    }

    private void sendLoan(LoanDto loanDto){
        LoanSendEvent loanSendEvent = modelMapper.map(loanDto, LoanSendEvent.class);
        kafkaTemplate.send(sendClientTopic, loanSendEvent);
    }

    private LoanEntity saveToDb(LoanDto loanDto){
        LoanEntity entity = loanToEntity(loanDto, "processing");
        LoanEntity saved = loanRepository.save(entity);
        return saved;
    }

    private LoanEntity loanToEntity(LoanDto dto, String status){
        LoanEntity entity = new LoanEntity(dto.getId()
                                            , dto.getAmount()
                                            , dto.getTerm()
                                            , dto.getIncome()
                                            , dto.getDsti()
                                            , dto.getRating()
                                            , status);
        return entity;
    }
}
