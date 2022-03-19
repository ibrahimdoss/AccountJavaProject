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
 //Thread = programların bir bütün olarak değil bölümlü olarak çalıştırılması.
    /*thread-safe = Aynı anda çalışan birden fazla Thread var ise sorun çıkartmaması, çakışmaması için kullanırız.
        Birden fazla aynı anda aynı Thread’e gelen istekleri sıraya alarak kontrollü bir yapı sağlamaktayız. */

/*Clock geçerli ana ihtiyacımız olduğunda kullanılmalıdır.
        Döndürülen uygulama değişmez(immutable), iş parçacığı için güvenli(thread-safe) ve Serileştirilebilir.(Serializable)*/


//compareTo da büyükse 1 eşitse 0 küçükse -1 değerlerini döner.
// new account dediğimizde id alanı istiyor bunun için account entitysinde idye boş bir "" koyarak id alanını boş bırakıyoruz.
// bizim için idyi hibernate üretecek zaten.

/*
@Autowired kullanmıyoruz çünkü AccountRepository artık immutable olmuyor.
Test etmek için mocku da buna göre ayarlamak gerekiyor.
final ile inject etmek daha kullanılabilir olabilir.

DI ve IOC springbootta servislerde interface gerek yok.Çünkü spring boot
arka planda zaten bu işlemleri gerçekleştiriyor.
*/
