package com.SpringBoot.account;

import com.SpringBoot.account.model.Customer;
import com.SpringBoot.account.repository.CustomerRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.util.HashSet;

@SpringBootApplication
public class AccountApplication implements CommandLineRunner {


	private final CustomerRepository customerRepository;

	public AccountApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String description,
								 @Value("${application-version}") String version){
		return new OpenAPI()
				.info(new Info()
						.title("Account API")
						.version(version)
						.description(description)
						.license(new License().name("Account API Licence")));
	}

	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}


	@Override
	public void run(String... args) throws Exception {
		Customer customer = customerRepository.save(new Customer("","ibrahim","dos",new HashSet<>()));
		System.out.println(customer);
	}
}


/*Customer oluşturmadan test edebilmek için customer lazım olduğu için hemen main methodunda bir customer oluşturuldu.
H2 database kullandığımız için de uygulama çalıştığında çalışan db var.*/




	/*Account a = new Account("a", BigDecimal.ONE, LocalDateTime.now(),null, SetsKt.emptySet());
	Account b = new Account("a", BigDecimal.ONE, LocalDateTime.now(),null, SetsKt.emptySet());

		if(a == b)//false == dediğimiz zaman referanslarına bakılıyor yani a ve b nin referansları kontrol ediliyor.
				a.equals(b) // burada değerleri karşılaştırıyoruz.

				//hashcode

				Set<Account> aa = Set.of(a,b);

		Map<Account, String> accountMap = new HashMap<>();

		accountMap.keySet(); // set  key alanı bir set tutuyor.
		yukarıdaki örnekte Accountun değeri key alanının kullandığı setleri setlerde hashcodeları kullanarak tuttuğu için
		hızlı çalışma imkanı sunuyor.

		accountMap.values(); // Collection value ise collection tutuyor, list veya arraylist gibi
hashcode veritabanın da bir primary key oluşturduğumuzu düşünelim peki bu pk veya unique key hızlı gelme mantığıdna
hashcode çalışıyor. hashcode bizim nesnelerimizin değerleriyle bir tane unique bir integer oluşturuluyor.
daha sonra bu int kaydediliyor daha sonra karşılaştırma yapıldığı zaman hashcodelar karşılaştırılıyor.
mesela yukarıdaki a ve b nesnesinin hashcode eşleştiği zaman daha hızlı erişim sağlayabiliyoruz.

*/