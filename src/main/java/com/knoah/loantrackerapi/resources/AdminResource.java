package com.knoah.loantrackerapi.resources;

import com.knoah.loantrackerapi.domain.ApiMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class AdminResource {

    ApiMetrics metrics = new ApiMetrics();

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        System.out.println(metrics.getTotalRequests());
        model.addAttribute("metrics", metrics);
        return "admin";
    }
}
