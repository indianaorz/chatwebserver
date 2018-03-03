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
import java.util.Optional;

@RestController
@RequestMapping("/user")
class UserRestController {

	private final UserRepository userRepository;


	@Autowired
	UserRestController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

  	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	Collection<User> getUsers() {
		return this.userRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	Collection<User> getUser(@PathVariable String username) {
		return this.userRepository.findByUsername(username);
	}

	@RequestMapping(method = RequestMethod.POST)
	User postUser(@RequestBody String token) {
		//Decode Token
		
        User existingUser = this.userRepository.findByGoogleUserId(message.username)[0];
        if(null != existingUser){
            //Throw error user already exists
            return;
        }
        
		return this.userRepository.save(message);

	}

}