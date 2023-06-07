package ch.web.web_shop.service;

import ch.web.web_shop.SpringBootDataJpaApplication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpringBootDataJpaApplicationTest {

    @Test
    public void main_StartsApplication_Successfully() {
        assertDoesNotThrow(() -> {
            SpringBootDataJpaApplication.main(new String[]{});
        });
    }
}
