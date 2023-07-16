package com.ecommerce.customer.controller;


import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InvoiceController {

    @Autowired
    private CustomerService customerService;
   /* @GetMapping("/invoices")
       public String getInvoices(Principal principal,
                              Model model){

        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer=customerService.findByUsername(principal.getName());
            List<Invoice> invoices=invoiceService.getInvoices(customer);
            for(Invoice invoice: invoices) {
               Double totalPrice= invoice.getOrder().getTotalPrice();
               System.out.println(totalPrice);

            }
            model.addAttribute("invoices",invoices);
            return "invoice";
        }


    }


    */
}
