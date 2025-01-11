package com.mhyusuf.yboilerplate.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @GetMapping(value = {"/"})
    public String greeting(@RequestParam(value = "name", defaultValue = "Yusuf") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}