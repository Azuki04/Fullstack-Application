package ch.web.web_shop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.web.web_shop.model.Newslatter;
import ch.web.web_shop.repository.NewslatterRepository;

/**
 * Newslatter controller class.
 * This is just a test, only emails are saved here. this class is not necessary
 * The @RequestMapping("/api/newslatter") annotation informs that this
 * controller will process requests whose URI begins with "/api/newslatter".
 *
 * @author Sy Viet
 * @version 1.0
 * @see newslatter
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/newslatter")

public class NewslatterController {

	@Autowired
	private NewslatterRepository newslatterRepository;

	// create newslatter rest api
	@PostMapping("")
	public Newslatter createNewslatter(@Valid @RequestBody Newslatter newslatter) {
		return newslatterRepository.save(newslatter);
	}

}
