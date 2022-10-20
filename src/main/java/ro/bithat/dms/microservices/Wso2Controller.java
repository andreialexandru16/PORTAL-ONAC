package ro.bithat.dms.microservices;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Wso2Controller {
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "redirect:/oauth2/authorization/wso2";
    }

    @GetMapping("/userinfo")
    public String getUser(Authentication authentication, Model model) {
        model.addAttribute("authentication", authentication);
        return "userinfo";
    }
}
