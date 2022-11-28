package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;


@Controller
@RequestMapping({"/file"})
public class FileController {

    private UserService userService;
    private FileService fileService;

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes attributes) throws IOException {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/home";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (fileService.existsFilename(fileName)) {
            attributes.addFlashAttribute("warning", "The file \"" + fileName + "\" already exists!");
            return "redirect:/home";
        }

        String file_err = null;

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            Integer userId = userService.getUser(username).getUserId();
            File fileUpload = new File(fileName, file.getContentType(), String.valueOf(file.getSize()), userId, file.getBytes());

            fileService.insertFile(fileUpload);
        }
        catch (MaxUploadSizeExceededException a){
            String errorMessage = "file size limit exceed expception captured";
            logger.error(errorMessage);
            logger.error(a.toString());
            a.printStackTrace();
            file_err = errorMessage;
        }
        catch (Exception a){
            logger.error(a.toString());
            a.printStackTrace();
            file_err = a.toString();
        }

        if (file_err == null) {
            attributes.addFlashAttribute("uploadsuccess", "You successfully uploaded " + fileName + '!');
        }
        else {
            attributes.addFlashAttribute("uploaderror","You failed to upload " + fileName + '!');
        }
        return "redirect:/home";


    }


}