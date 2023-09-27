package com.tslcompany.controllers;

import com.tslcompany.email.EmailForm;
import com.tslcompany.email.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-reminder")
    public String reminderForm(Model model) {
        model.addAttribute("emailForm", new EmailForm());
        return "reminder-form";
    }

    @PostMapping("/send-email")
    public String sendReminder(@ModelAttribute("emailForm") EmailForm emailForm) {
        String recipientAddress = emailForm.getRecipientAddress();
        String emailSubject = emailForm.getEmailSubject();
        String emailText = emailForm.getEmailText();
        emailService.sendEmail(recipientAddress, emailSubject, emailText);
        return "success-page";
    }
}
