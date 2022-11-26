package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/file"})
public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/home";
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            attributes.addFlashAttribute("warning", "Your file cannot exceed 5MB!");
            return "redirect:/home";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileService.existsFilename(fileName)) {
            attributes.addFlashAttribute("warning", "The file \"" + fileName + "\" already exists!");
            return "redirect:/home";
        }

        Integer userId = userService.getUserId();
        File file = new File(fileName, fileUpload.getContentType(), String.valueOf(fileUpload.getSize()), userId, fileUpload.getBytes());

        fileService.createFiles(file);



    }


}
