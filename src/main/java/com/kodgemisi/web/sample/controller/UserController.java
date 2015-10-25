package com.kodgemisi.web.sample.controller;

import com.kodgemisi.web.sample.model.User;
import com.kodgemisi.web.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping()
	public String list(Model model) {

		model.addAttribute("users", this.userService.getAll());

		return "userList";
	}

	@RequestMapping("/withItems")
	public String listWithItems(Model model) {

		model.addAttribute("users", this.userService.getAllWithItems());

		return "userListWithItems";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String form(@ModelAttribute User user) {
		return "userCreateForm";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String updateForm(Model model, @PathVariable("id") Long id,
			@RequestParam(value = "message", required = false) String message) {

		model.addAttribute("user", this.userService.getById(id));
		model.addAttribute("message", message);
		return "userUpdateForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute User user, @RequestParam("passwordAgain") String passwordAgain) {

		if (!passwordAgain.equals(user.getPassword())) {
			// TODO show error and don't proceed when two passwords don't match
		}

		this.userService.create(user);
		return "redirect:/users";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String update(@ModelAttribute User user, @RequestParam("passwordAgain") String passwordAgain,
			@PathVariable("id") Long id, Model model) {

		if (!passwordAgain.equals(user.getPassword())) {
			// TODO show error and don't proceed when two passwords don't match
		}

		this.userService.update(user);
		model.addAttribute("message", "Successful");
		return "redirect:/users/update/" + id;
	}

}
