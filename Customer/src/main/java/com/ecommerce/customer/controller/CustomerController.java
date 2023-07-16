package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;


    @RequestMapping(value = { "/profile"}, method = RequestMethod.GET)
    public String profile(Model model, Principal principal,HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            CustomerDto customer = customerService.getCustomer(username);
            String firstName=customer.getFirstName();
            String lastName=customer.getLastName();
            Customer customer1=customerService.findByUsername(username);
            ShoppingCart shoppingCart=customer1.getCart();
            List<Category> categories = categoryService.findAllByActivatedTrue();
            model.addAttribute("categories", categories);
            model.addAttribute("customer", customer);
            model.addAttribute("title", "Profile");
            model.addAttribute("page", "Profile");
            session.setAttribute("userName",firstName+" "+lastName);
                if (shoppingCart!=null) {
                    session.setAttribute("totalItems", shoppingCart.getTotalItems());
                }

        }
        return "customer-information";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                BindingResult result,
                                RedirectAttributes attributes,
                                Model model,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            CustomerDto customer = customerService.getCustomer(username);
            if (result.hasErrors()) {
                return "customer-information";
            }
            customerService.update(customerDto);
            CustomerDto customerUpdate = customerService.getCustomer(principal.getName());
            attributes.addFlashAttribute("success", "Update successfully!");
            model.addAttribute("customer", customerUpdate);
            return "redirect:/profile";
        }
    }

    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("repeatNewPassword") String repeatPassword,
                             RedirectAttributes attributes,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.getCustomer(principal.getName());
            if (passwordEncoder.matches(oldPassword, customer.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, customer.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 5) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                customerService.changePass(customer);
                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "Your password is wrong");
                return "change-password";
            }
        }
    }
}
