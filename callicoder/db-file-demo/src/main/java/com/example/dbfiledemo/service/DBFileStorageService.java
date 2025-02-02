package com.example.dbfiledemo.service;

import com.example.dbfiledemo.exception.FileStorageException;
import com.example.dbfiledemo.exception.MyFileNotFoundException;
import com.example.dbfiledemo.model.DBFile;
import com.example.dbfiledemo.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DBFileStorageService {

    private DBFileRepository dbFileRepository;

    @Autowired
    public DBFileStorageService(DBFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    public DBFile storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
