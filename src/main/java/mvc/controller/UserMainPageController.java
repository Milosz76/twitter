package mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserMainPageController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/userpage")
    public ModelAndView test(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return new ModelAndView("userpage","login",
                authentication.getName() + " " + authentication.getAuthorities());
    }


}
