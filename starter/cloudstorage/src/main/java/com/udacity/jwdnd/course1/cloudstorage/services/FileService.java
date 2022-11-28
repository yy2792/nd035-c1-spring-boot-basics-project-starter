package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public Integer insertFile(File file){
        return fileMapper.insert(file);
    }

    public boolean existsFilename(String filename) {
        return fileMapper.existsFilename(filename) != null;
    }

    public List<File> getFilesByUserId(Integer userId){
        return fileMapper.getFileListByUserId(userId);
    }

    public List<File> getFileListByLoggedInUserId() {
        return getFilesByUserId(userService.getLoggedInUserId());
    }

}
