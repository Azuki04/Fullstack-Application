package ch.web.web_shop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.web.web_shop.model.Newsletter;
import ch.web.web_shop.repository.NewsletterRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {

	@Autowired
	private NewsletterRepository newsletterRepository;

	@PostMapping("")
	public Newsletter createNewsletter(@Valid @RequestBody Newsletter newsletter) {
		return newsletterRepository.save(newsletter);
	}
}
