package ch.web.web_shop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.web.web_shop.model.Newslatter;
import ch.web.web_shop.repository.NewsletterRepository;

/**
 * Newslatter controller class.
 * This is just a test, only emails are saved here. this class is not necessary
 * The @RestController annotation combines the @Controller and @ResponseBody annotations,
 * simplifying the code and eliminating the need for individual @ResponseBody annotations.
 * The @RequestMapping("/api/newslatter") annotation informs that this controller
 * will process requests whose URI begins with "/api/newslatter".
 * Handles HTTP methods such as GET, POST, etc. using appropriate mapping annotations.
 * Manages and provides access to Newslatter objects.
 * Uses NewsletterRepository to save data.
 *
 * @author Sy Viet
 * @version 1.0
 * @see Newslatter
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/newslatter")
public class NewsletterController {

	@Autowired
	private NewsletterRepository newsletterRepository;

	/**
	 * Creates a newslatter.
	 *
	 * @param newsletter The newslatter object to be created.
	 * @return The created newslatter object.
	 */
	@PostMapping("")
	public Newslatter createNewslatter(@Valid @RequestBody Newslatter newsletter) {
		return newsletterRepository.save(newsletter);
	}
}