package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.dto.CommercialDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.model.Commercial;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.OrderRepository;
import com.ecommerce.library.service.AdminService;
import com.ecommerce.library.service.CommercialService;
import com.ecommerce.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;
    private  final CommercialService commercialService;
    private final ProductService productService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @RequestMapping(value={"/login","/"})
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        List<Product> productsCompletion=productService.nearingCompletion();
        int nbC=customerRepository.nbrCustomers();
        int nbO=orderRepository.nbrOrders();
        Double earnings=orderRepository.earning();
        model.addAttribute("nbrCommercials",nbC);
        System.out.println(nbC);
        model.addAttribute("nbrOrders",nbO);
        System.out.println(nbO);
        model.addAttribute("earnings",earnings);
        System.out.println(earnings);
        List<Object[]>cities= customerRepository.findCitiesWithOrdersOrderByOrderCountDesc();
        System.out.println(cities);
        model.addAttribute("cities",cities);
        int i=0;
        session.setAttribute("count",i);
        model.addAttribute("products",productsCompletion);
        List<Product> bestSeller=productService.bestSeller();
        model.addAttribute("bestproducts",bestSeller);
        List<Product> LeastSeller=productService.LeastSeller();
        model.addAttribute("lessproducts",LeastSeller);
        return "index";
    }




    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewadmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model) {

        try {

            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("admin not null");
                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getConfirmationPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("adminDto", adminDto);
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";

    }

}
