package com.SpringBoot.account.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Account(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val balance: BigDecimal? = BigDecimal.ZERO,
    val creationDate: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "customer_id", nullable= false)
    val customer: Customer?,

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val transaction: Set<Transaction> = HashSet()


){

    constructor(customer: Customer, balance: BigDecimal, creationDate: LocalDateTime): this(
        "",
        customer= customer,
        balance = balance,
        creationDate = creationDate
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (balance != other.balance) return false
        if (creationDate != other.creationDate) return false
        if (customer != other.customer) return false
        if (transaction != other.transaction) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + (customer?.hashCode() ?: 0)
        return result
    }
}




/*account modelinde ManyToOne kullandığımız için Hashcode fonksiyonunu override etmemiz lazım
çünkü veritabanından veriyi çekerken buradaki hashcode değerinin karşılaştırmasını yapar.
eğer hashcode customer ya da transaction eklendiği zaman accountu da çekiyor bunu da hashcode üzerinden yapıyor.*/



/*CascadeType.ALL bu entitye ait yapılacak herhangi bir işlemde(ekleme,silme, güncelleme)
eğer accounta ait bir customer güncelleme yapıldıysa
customer tablosunda da güncelle, ekle veya sil.*/


/*val customer: Customer?,
yukarıdaki soru işareti bu alan boş olabilir demek.
javadaki optionala denk geliyor.*/



//kotlin de genellikle val kullanmak gerekiyor. Çünkü immutable'dir.'

/*
immutable = nesneler bir kez oluşturulduktan sonra içeriği değiştirilemeyen sınıflardır.
Kısacası Immutable nesneler değişmeyen nesnelerdir.*/
