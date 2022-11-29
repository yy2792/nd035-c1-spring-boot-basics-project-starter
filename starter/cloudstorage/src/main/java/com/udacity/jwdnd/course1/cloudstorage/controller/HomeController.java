package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;


    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String getHome(Model model) {
        List<File> fileList = fileService.getFileListByLoggedInUserId();
        boolean fileListEmpty = fileList.isEmpty();
        model.addAttribute("fileListEmpty", fileListEmpty);

        if (!fileListEmpty) {
            model.addAttribute("fileList", fileList);
        }

        List<Note> noteList = noteService.getNoteListByLoggedInUserId();
        boolean noteListEmpty = noteList.isEmpty();
        model.addAttribute("noteListEmpty", noteListEmpty);

        if (!noteListEmpty) {
            model.addAttribute("noteList", noteList);
        }

        List<Credential> credentialList = credentialService.getCredentialListByLoggedInUserId();
        boolean credentialListEmpty = credentialList.isEmpty();
        if (!credentialListEmpty) {
            model.addAttribute("credentialList", credentialList);
        }

        return "home";
    }

}
