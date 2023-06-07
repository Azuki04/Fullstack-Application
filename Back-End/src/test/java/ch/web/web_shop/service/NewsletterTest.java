package ch.web.web_shop.service;

import ch.web.web_shop.model.Newsletter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NewsletterTest {

    @Test
    public void testCreateNewsletter() {
        String email = "test@example.com";
        Newsletter newsletter = new Newsletter(email);

        Assertions.assertEquals(email, newsletter.getEmail());
    }
}