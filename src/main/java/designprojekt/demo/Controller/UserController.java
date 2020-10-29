package designprojekt.demo.Controller;

import designprojekt.demo.Model.User;
import designprojekt.demo.Repository.HashRepository;
import designprojekt.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HashRepository hashRepository;

    @GetMapping("/createUser")
    public String createUser(Model model){
        User user = new User();
        model.addAttribute(user);
        return "createUser";
    }

    @PostMapping("/createUser")
    public String makeUser(@ModelAttribute User user) {
        if (hashRepository.usernameExists(user)){
            return "redirect:"+"/createUser";
        } else {
            int newId = userRepository.createUser(user);
            return "redirect:"+'/'+newId;
        }
    }

    @GetMapping("/delete/{userId}")
    public String deleteForm(@PathVariable int userId, Model model){
        User user = userRepository.findUserById(userId);
        model.addAttribute("user", user);
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute User user){
        userRepository.deleteUser(user.getUserId());
        return "redirect:"+'/';
    }
}