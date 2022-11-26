package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;

public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer insertFile(File file){
        return fileMapper.insert(file);
    }

    public List<File> getFilesForUser(Integer userId){
        return fileMapper.getFileListByUserId(userId);
    }


}
