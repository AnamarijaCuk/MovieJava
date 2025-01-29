package moviereviewjava.moviereviewjava.controllers;

import jakarta.validation.Valid;
import moviereviewjava.moviereviewjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import moviereviewjava.moviereviewjava.models.User;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String register (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register_user")
    public String register_user (@Valid User user, BindingResult result, Model model) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            result.addError(new FieldError("user", "email", "Već postoji korisnik registriran s ovim email-om. Pokušajte s drugim."));
        }

        if (!user.getPassword().equals(user.getPasswordRepeat())) {
            result.addError(new FieldError("user", "passwordRepeat", "Lozinke se ne podudaraju"));
            result.addError(new FieldError("user", "password", "Lozinke se ne podudaraju"));
        }

        boolean errors = result.hasErrors();
        if (errors) {
            model.addAttribute("user", user);
            model.addAttribute("success", false);
            return "register";
        }

        // enkodiraj lozinku i postavi ulogu guest za nove korisnike
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);

        // resetiraj formu i prikaži poruku za uspješnu registraciju
        model.addAttribute("user", new User());
        model.addAttribute("success", true);
        return "register";
    }
}