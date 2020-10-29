package designprojekt.demo.Controller;

import designprojekt.demo.Model.Loan;
import designprojekt.demo.Model.User;
import designprojekt.demo.Repository.UserRepository;
import designprojekt.demo.Service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @Author Patrick Jønsson
 */
@Controller
public class LoanController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoanService loanService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/loanPayment")
    public String afbetalMonth(Model model){
        User user = new User();
        Loan loan = new Loan();
        model.addAttribute(loan);
        model.addAttribute(user);
        return "afbetalLån";
    }

    @PostMapping("/loanPayment")
    public String postAfbetalMonth(@ModelAttribute Loan loan, Model model) {
        User user = new User();
        model.addAttribute(user);
        int k = (int) loanService.amountToPayBack((int) loan.getLoanAmount(), loan.getAnnualInterestRate(), loan.getYearOnLoan());
        return "redirect:"+"/loanPayment"+'/'+ k;
    }

    @GetMapping("/loanPayment/{k}")
    public String amountPerMonth(@PathVariable int k, Model model){
        Loan loan = new Loan();
        loan.setK(k);
        model.addAttribute(loan);
        User user = new User();
        model.addAttribute(user);
        return "afbetallån";
    }

    @GetMapping("/loanPayment/userLoan/{userId}")
    public String afbetalMonthligUser(@PathVariable int userId ,Model model){
        User user = userRepository.findUserById(userId);
        Loan loan = new Loan();
        model.addAttribute(loan);
        model.addAttribute(user);

        return "afbetalLån";
    }

    @PostMapping("/loanPayment/userLoan/{userId}")
    public String postAfbetalMonthUser(@ModelAttribute Loan loan, @PathVariable int userId, Model model) {

        User user = userRepository.findUserById(userId);
        model.addAttribute(user);
        int k = (int) loanService.amountToPayBack((int) loan.getLoanAmount(), loan.getAnnualInterestRate(), loan.getYearOnLoan());

        return "redirect:"+"/loanPayment/userLoan"+'/'+ userId +'/'+ k;
    }

    @GetMapping("/loanPayment/userLoan/{userId}/{k}")
    public String amountPerMonthUser(@PathVariable int userId ,@PathVariable int k, Model model){
        User user = userRepository.findUserById(userId);
        Loan loan = new Loan();
        loan.setK(k);
        model.addAttribute(loan);
        model.addAttribute(user);
        return "afbetallån";
    }
}
