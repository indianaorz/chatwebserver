package com.colur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/user/{username}/color")
class ColorRestController {

	private final UserRepository userRepository;
	private final ColorRepository colorRepository;


	@Autowired
	ColorRestController(UserRepository userRepository,
						   ColorRepository colorRepository) {
		this.userRepository = userRepository;
		this.colorRepository = colorRepository;
	}

  	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	Collection<Color> getColors(@PathVariable String username) {
		this.validateUser(username);
		return this.colorRepository.findByUserUsername(username);
	}

	// @RequestMapping(method = RequestMethod.POST)
	// Color postColor(@PathVariable String username, @RequestBody Color input) {
	// 	this.validateUser(username);

	// 	return this.userRepository
	// 			.findByUsername(username)
	// 			.map(user -> {
	// 				Color result = colorRepository.save(new Color(user,
	// 						input.getColor(), input.getDate()));

	// 				URI location = ServletUriComponentsBuilder
	// 					.fromCurrentRequest().path("/{id}")
	// 					.buildAndExpand(result.getId()).toUri();

	// 				return ResponseEntity.created(location).build();
	// 			})
	// 			.orElse(ResponseEntity.noContent().build());

	// }

	@RequestMapping(method = RequestMethod.GET, value = "/{colorId}")
	Color getColor(@PathVariable String username, @PathVariable Long colorId) {
		this.validateUser(username);
		return this.colorRepository.findOne(colorId);
	}

	private void validateUser(String username) {
		// this.userRepository.findByUsername(username).orElseThrow(
		// 		() -> new UserNotFoundException(username));
	}
}