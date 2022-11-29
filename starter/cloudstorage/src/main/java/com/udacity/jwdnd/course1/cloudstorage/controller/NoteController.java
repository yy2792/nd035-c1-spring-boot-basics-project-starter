package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(NoteController.class);

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping()
    public String notePostRequest(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {

        note.setUserid(userService.getLoggedInUserId());

        try {
            if (note.getNoteid() == null) {
                noteService.insertNote(note);
                redirectAttributes.addFlashAttribute("nodeUploadSuccess", "Create notes successful!");
            } else {
                noteService.updateNote(note);
                redirectAttributes.addFlashAttribute("nodeUploadSuccess", "Update notes successful!");
            }
        }
        catch (Exception a){
            logger.error(a.toString());
            a.printStackTrace();
            redirectAttributes.addFlashAttribute("nodeUploadFailure", "Update/insert notes failed!");
        }

        return "redirect:/home";

    }


}
