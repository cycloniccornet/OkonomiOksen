package designprojekt.demo.Controller;

import designprojekt.demo.Model.Expense;
import designprojekt.demo.Model.Income;
import designprojekt.demo.Model.User;
import designprojekt.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {



    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    ChartRepository chartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HashRepository hashRepository;

    @GetMapping("/")
    public String index(Model model) {
        Expense expense = new Expense();
        model.addAttribute(expense);
        Income income = new Income();
        model.addAttribute(income);
        User user = new User();
        model.addAttribute(user);
        return "index";
    }

    @GetMapping("/{userId}")
    public String loginIndex(@PathVariable int userId, Model model) {
        if (userId == 0) {
            return "redirect:"+'/';
        }
        User user = userRepository.findUserById(userId);
        model.addAttribute(user);
        Expense expense = new Expense();
        model.addAttribute(expense);
        Income income = new Income();
        model.addAttribute(income);
        return "index";
    }

    @PostMapping("/login")
    public String postIndexLogin(@ModelAttribute User loginUser, Model model) {
        User user = userRepository.checkLogin(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            model.addAttribute(user);
            return "redirect:"+'/'+user.getUserId();
        } else {
            return "redirect:"+'/';
        }
    }

    @PostMapping("/{userId}")
    public String calculation(Model model, @ModelAttribute Expense expense, @ModelAttribute Income income, @PathVariable int userId) {
        int currentUserId = 0;
        if (userId != 0) {
            currentUserId = userId;
        }
        int id = incomeRepository.createIncomeTable(income, currentUserId);
        expenseRepository.createExpenseTable(expense, id);
        User user = new User();
        if (userId != 0) {
            user = userRepository.findUserById(userId);
        }
        model.addAttribute(user);
        return "redirect:/overview/{userId}/"+id;
    }

    @GetMapping("/overview/{userId}/{overviewId}")
    public String overview(@PathVariable int userId, @PathVariable int overviewId, Model model) {
        Expense currentExpense = expenseRepository.getExpenseById(overviewId);
        Income currentIncome = incomeRepository.getIncomeById(overviewId);
        User user = new User();
        if (userId != 0) {
            user = userRepository.findUserById(userId);
        }
        Expense avgExpense = expenseRepository.getAvgExpense();
        Income avgIncome = incomeRepository.getAvgIncome();
        model.addAttribute(user);
        model.addAttribute("income", currentIncome);
        model.addAttribute("avgIncome",avgIncome);
        model.addAttribute("expense", currentExpense);
        model.addAttribute("avgExpense",avgExpense);
        return "overview";
    }

    @GetMapping("/findOverview/{userId}")
    public String findOverview(Model model, @PathVariable int userId) {
        model.addAttribute(userRepository.findUserById(userId));
        Income income = incomeRepository.getLastIncomeByUserId(userId);
        if (income == null) {
            return "noOverview";
        } else {
            Expense expense = expenseRepository.getExpenseById(income.getId());
            model.addAttribute(income);
            model.addAttribute(expense);
            return "redirect:"+ "overview" +'/'+userId+'/'+income.getId();
        }
    }
}
