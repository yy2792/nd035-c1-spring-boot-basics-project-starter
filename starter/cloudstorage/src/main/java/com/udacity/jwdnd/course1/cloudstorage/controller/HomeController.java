package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final NoteService noteService;
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
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

        return "home";
    }
}
