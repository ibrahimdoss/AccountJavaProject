package com.SpringBoot.account.dto.converter;

import com.SpringBoot.account.dto.TransactionDto;
import com.SpringBoot.account.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoConverter {

    public TransactionDto convert(Transaction from){
        return new TransactionDto(from.getId(),
                from.getTransactionType(),
                from.getAmount(),
                from.getTransactionDate());
    }
}
