package ru.netology.loanproducer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.netology.loanproducer.dto.LoanDto;
import ru.netology.loanproducer.service.LoanProducerService;

//@Slf4j
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanProducerService loanService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Long createLoan(@RequestBody LoanDto loanDto){
        //log.info("Send order to kafka");
        Long result = loanService.createLoan(loanDto);
        return result;
    }

    @GetMapping("/{id}")
    public String getStatus(@PathVariable Long id){
        String status = loanService.getStatus(id);
        return status;
    }
}
