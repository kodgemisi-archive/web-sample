package com.kodgemisi.web.sample.controller;

import com.kodgemisi.web.sample.model.dto.SampleTodoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class DashboardController {

	@RequestMapping("")
	public String dashboard(Model model) {

        List<SampleTodoDto> todoList = new ArrayList<>();
        todoList.add(new SampleTodoDto("Inspect the app",
                "Briefly take a look into the source code to understand how the app is structured."));

        todoList.add(new SampleTodoDto("Do the todo items in README", "They are the main objective."));
        todoList.add(new SampleTodoDto("Do the bonus", "What other adjustments may be done to make this app better?"));

		model.addAttribute("message", "Sample message");
        model.addAttribute("sampleList", todoList);
		return "dashboard";
	}

}
