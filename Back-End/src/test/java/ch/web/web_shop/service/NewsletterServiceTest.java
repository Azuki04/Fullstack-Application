package ch.web.web_shop.service;

import ch.web.web_shop.model.Newsletter;
import ch.web.web_shop.repository.NewsletterRepository;
import ch.web.web_shop.service.NewsletterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NewsletterServiceTest {

    @Mock
    private NewsletterRepository newsletterRepository;

    @InjectMocks
    private NewsletterService newsletterService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewsletter_Success() {
        Newsletter newsletter = new Newsletter();
        when(newsletterRepository.save(newsletter)).thenReturn(newsletter);

        Newsletter result = newsletterService.createNewsletter(newsletter);
        assertEquals(newsletter, result);
    }

}
