package com.SpringBoot.account.dto.converter;

import com.SpringBoot.account.dto.CustomerAccountDto;
import com.SpringBoot.account.model.Account;

import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerAccountDtoConverter {

    private final TransactionDtoConverter converter;

    public CustomerAccountDtoConverter(TransactionDtoConverter converter) {
        this.converter = converter;
    }

    public CustomerAccountDto convert(Account from){
        return new CustomerAccountDto(
                Objects.requireNonNull(from.getId()), //if(a==b) yerine kullanılır burasının kesinlikle boş olmadığını varsayabiliriz.
                from.getBalance(),
                from.getTransaction()
                        .stream()
                        .map(converter::convert)
                        .collect(Collectors.toSet()),
                from.getCreationDate());
    }
}
