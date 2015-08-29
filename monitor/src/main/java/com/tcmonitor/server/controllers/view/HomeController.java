package com.tcmonitor.server.controllers.view;

import com.tcmonitor.server.message.SerializableResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("${home.url}")
public class HomeController {

    @Value("${app-version:NO_VERSION}")
    private String version;

    @Autowired
    private SerializableResourceBundleMessageSource serializableResourceBundleMessageSource;

    @RequestMapping
    public String home() {
        return "redirect:index";
    }

    @RequestMapping("index")
    public String home2(HttpServletRequest request, Model model) {
        model.addAttribute("context", request.getContextPath());
        model.addAttribute("localization", serializableResourceBundleMessageSource.getAllProperties(request.getLocale()));
        model.addAttribute("version", version);
        return "index";
    }

}
