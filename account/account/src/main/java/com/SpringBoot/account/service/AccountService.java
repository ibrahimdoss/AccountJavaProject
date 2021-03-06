package com.SpringBoot.account.service;

import com.SpringBoot.account.dto.AccountDto;
import com.SpringBoot.account.dto.converter.AccountDtoConverter;
import com.SpringBoot.account.dto.CreateAccountRequest;
import com.SpringBoot.account.model.Account;
import com.SpringBoot.account.model.Customer;
import com.SpringBoot.account.model.Transaction;
import com.SpringBoot.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter converter;
    private final Clock clock;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                         AccountDtoConverter converter,  Clock clock) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.converter = converter;
        this.clock = clock;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(
                customer,
                createAccountRequest.getInitialCredit(),
                getLocalDateTimeNow());

        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(createAccountRequest.getInitialCredit(), account);
            account.getTransaction().add(transaction);
        }

        return converter.convert(accountRepository.save(account));
    }

    private LocalDateTime getLocalDateTimeNow(){
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(
                instant,
                Clock.systemDefaultZone().getZone());
    }

}
 //Thread = programlar??n bir b??t??n olarak de??il b??l??ml?? olarak ??al????t??r??lmas??.
    /*thread-safe = Ayn?? anda ??al????an birden fazla Thread var ise sorun ????kartmamas??, ??ak????mamas?? i??in kullan??r??z.
        Birden fazla ayn?? anda ayn?? Thread???e gelen istekleri s??raya alarak kontroll?? bir yap?? sa??lamaktay??z. */

/*Clock ge??erli ana ihtiyac??m??z oldu??unda kullan??lmal??d??r.
        D??nd??r??len uygulama de??i??mez(immutable), i?? par??ac?????? i??in g??venli(thread-safe) ve Serile??tirilebilir.(Serializable)*/


//compareTo da b??y??kse 1 e??itse 0 k??????kse -1 de??erlerini d??ner.
// new account dedi??imizde id alan?? istiyor bunun i??in account entitysinde idye bo?? bir "" koyarak id alan??n?? bo?? b??rak??yoruz.
// bizim i??in idyi hibernate ??retecek zaten.

/*
@Autowired kullanm??yoruz ????nk?? AccountRepository art??k immutable olmuyor.
Test etmek i??in mocku da buna g??re ayarlamak gerekiyor.
final ile inject etmek daha kullan??labilir olabilir.

DI ve IOC springbootta servislerde interface gerek yok.????nk?? spring boot
arka planda zaten bu i??lemleri ger??ekle??tiriyor.
*/
