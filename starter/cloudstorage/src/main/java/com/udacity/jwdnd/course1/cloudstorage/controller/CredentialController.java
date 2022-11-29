package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/credential"})
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping()
    public String createOrUpdateCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes) {
        credential.setUserId(userService.getLoggedInUserId());

        if (credential.getCredentialId() == null) {
            credentialService.createCredential(credential);
            redirectAttributes.addFlashAttribute("success", "Create credentials successful!");
        } else {
            credentialService.updateCredential(credential);
            redirectAttributes.addFlashAttribute("success", "Update credentials successful!");
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, RedirectAttributes redirectAttrs) {

        if (!credentialService.deleteCredential(credentialId)) {
            String errorMessage = "An error occurred while deleting the credential!";
            logger.error(errorMessage);
            redirectAttrs.addFlashAttribute("error", errorMessage);
        }

        return "redirect:/home";
    }

}
