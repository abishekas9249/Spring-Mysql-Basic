package com.mysql.connect.SpringMysqlBasic.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@PostMapping
	public String  saveUser(@RequestBody User user) {
		User users=repository.save(user);
		if(users==null) {
			throw new RuntimeException("Invalid User Details");
		}
		return "Successfully Created New User";
	}
	
	@GetMapping("/{id}")
	public Optional<User> retrieveUser(@PathVariable int id) {
		return repository.findById(id);
	}
	
	@PutMapping("/{id}")
	public Optional<User> updateUser(@PathVariable int id,@RequestBody User user) {
		return Optional.of(repository.findById(id)
				.map(updateUser->{
					updateUser.setFirstname(user.getFirstname());
					updateUser.setLastname(user.getLastname());
					return repository.save(updateUser);
				}).orElseThrow(() -> new RuntimeException("Person Not Found")));
	}
	
	@GetMapping
	public List<User> retrieveAll(){
		return repository.findAll();
	}
}
