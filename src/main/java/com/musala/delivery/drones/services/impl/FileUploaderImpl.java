package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.exceptions.InvalidRequestException;
import com.musala.delivery.drones.exceptions.ResourceNotFoundException;
import com.musala.delivery.drones.services.FileUploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploaderImpl implements FileUploaderService {

    @Override
    public String uploadFile(MultipartFile multipartFile) throws InvalidRequestException {
        String fileName = multipartFile.getOriginalFilename() + '_' +
                generateFileName().get();
        try {
            String location = "/";
            File file = new File(location + "//" + fileName);
            multipartFile.transferTo(file);
        } catch (IOException | IllegalStateException ex) {
            throw new InvalidRequestException("Bad file Image");
        }
        return fileName;
    }

    private Supplier<String> generateFileName() {
        return () -> UUID.randomUUID().toString().substring(6);
    }
}
