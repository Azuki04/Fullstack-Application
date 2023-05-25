package ch.web.web_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.web.web_shop.model.Newslatter;
import ch.web.web_shop.repository.NewsletterRepository;

/**
 * Newsletter service class.
 * Handles the business logic for newsletter operations.
 * Uses NewsletterRepository for data persistence.
 * This class can be used to perform additional business logic if needed.
 * It provides a layer of abstraction between the controller and the repository.
 * The @Service annotation is used to indicate that this class is a service component.
 * It enables component scanning and automatic dependency injection.
 *
 * @author Sy Viet
 * @version 1.0
 * @see Newslatter
 * @see NewsletterRepository
 */
@Service
public class NewsletterService {

    @Autowired
    private NewsletterRepository newsletterRepository;

    /**
     * Creates a new newsletter.
     *
     * @param newsletter The newsletter object to be created.
     * @return The created newsletter object.
     */
    public Newslatter createNewsletter(Newslatter newsletter) {
        return newsletterRepository.save(newsletter);
    }
}
