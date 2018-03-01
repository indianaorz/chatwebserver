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
@RequestMapping("/{userId}/colors")
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
	Collection<Color> getColors(@PathVariable String userId) {
		this.validateUser(userId);
		return this.colorRepository.findByUserUsername(userId);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> postColor(@PathVariable String userId, @RequestBody Color input) {
		this.validateUser(userId);

		return this.userRepository
				.findByUsername(userId)
				.map(user -> {
					Color result = colorRepository.save(new Color(user,
							input.getColor(), input.getDate()));

					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getId()).toUri();

					return ResponseEntity.created(location).build();
				})
				.orElse(ResponseEntity.noContent().build());

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{colorId}")
	Color getColor(@PathVariable String userId, @PathVariable Long colorId) {
		this.validateUser(userId);
		return this.colorRepository.findOne(colorId);
	}

	private void validateUser(String userId) {
		this.userRepository.findByUsername(userId).orElseThrow(
				() -> new UserNotFoundException(userId));
	}
}