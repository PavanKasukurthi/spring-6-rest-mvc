package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong(){

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My Beer sskjandnxcksjdsskjandnxcksjdsskjandnxcksjdsskjandnxcksjdsskjandnxcksjdsskjandnxcksjdsskjandnxcksjdsskjandnxcksjd")
                    .beerStyle(BeerStyle.GOSE)
                    .upc("123456")
                    .price(new BigDecimal("19.99"))
                    .build());

            beerRepository.flush();
//        assertThat(savedBeer).isNotNull();
//        assertThat(savedBeer.getId()).isNotNull();
        });

    }

    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My Beer")
                        .beerStyle(BeerStyle.GOSE)
                        .upc("123456")
                        .price(new BigDecimal("19.99"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}