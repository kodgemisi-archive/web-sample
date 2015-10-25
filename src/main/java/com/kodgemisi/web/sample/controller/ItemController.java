/*
 * Copyright (C) 10, 2015 Kod Gemisi Ltd. <foss@kodgemisi.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kodgemisi.web.sample.controller;

import com.kodgemisi.web.sample.model.Item;
import com.kodgemisi.web.sample.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping()
	public String list(Model model) {
		model.addAttribute("items", this.itemService.getAll());
		return "itemList";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String form(@ModelAttribute Item item) {
		return "itemCreateForm";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String updateForm(Model model, @PathVariable("id") Long id,
			@RequestParam(value = "message", required = false) String message) {

		model.addAttribute("item", this.itemService.getById(id));
		model.addAttribute("message", message);
		return "itemUpdateForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute Item item) {
		this.itemService.createAndAttachToUser(item);
		return "redirect:/items";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String update(@ModelAttribute Item item,
			@PathVariable("id") Long id, Model model) {

		this.itemService.update(item);
		model.addAttribute("message", "Successful");
		return "redirect:/items/update/" + id;
	}

}
