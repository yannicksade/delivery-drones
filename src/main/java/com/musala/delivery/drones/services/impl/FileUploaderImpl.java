package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.services.exceptions.InvalidRequestException;
import com.musala.delivery.drones.services.FileUploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploaderImpl implements FileUploaderService {

    @Override
    public String uploadFile(MultipartFile multipartFile) throws InvalidRequestException {
        String fileName = multipartFile.getOriginalFilename();
        String[] parts = fileName.split("\\.");
       int size = Arrays.asList(parts).size();
        log.error("Loading file {} of extension {} and size {} ",  fileName, multipartFile.getName(),  multipartFile.getSize());
        String ext = parts[size - 1];
        log.info("Loading file of extension {} " + ext);
        fileName = fileName.replace("."  + ext, "") + '_' + generateFileName().get() + '.' + ext;
        String windowsRelocation = "D:\\lib\\files";
        String linuxReLocation = "/usr/local/lib/files";
        try { // store file on linux environment
            File file = new File(linuxReLocation + "/" + fileName);
            fileName = storeFile(multipartFile, file);
        } catch (IOException | IllegalStateException lex) {
            try {// store file on windows environment
                File file = new File(windowsRelocation + "//" + fileName);
                fileName = storeFile(multipartFile, file);
            } catch (IOException | IllegalStateException wex) {
                throw new InvalidRequestException(wex.getMessage());
            }
        }
        return fileName;
    }

    private String storeFile(MultipartFile multipartFile, File file) throws IOException {
        multipartFile.transferTo(file);
        return file.getName();
    }

    private Supplier<String> generateFileName() {
        return () -> UUID.randomUUID().toString().substring(0, 6);
    }
}
