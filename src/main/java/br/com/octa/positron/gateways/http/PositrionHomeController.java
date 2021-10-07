package br.com.octa.positron.gateways.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
public class PositrionHomeController {

    @ApiIgnore
    @RequestMapping("/apidoc")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
}
