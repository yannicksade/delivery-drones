package com.musala.delivery.drones.services.impl;

import com.musala.delivery.drones.services.exceptions.InvalidRequestException;
import com.musala.delivery.drones.services.FileUploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
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
            String linuxReLocation = "/usr/local/lib/files";
            String windowsRelocation = "C:\\";
            File file = new File(windowsRelocation + "//" + fileName);
            multipartFile.transferTo(file);
        } catch (IOException | IllegalStateException ex) {
            try {
                String linuxReLocation = "/usr/local/lib/files";
                String windowsRelocation = "C:\\";

            } catch (ex) {
            throw new InvalidRequestException(ex.getMessage());}
        }
        return fileName;
    }
    private String store(String fileName) {
        File file = new File(fileName + "//" + fileName);
        multipartFile.transferTo(file);
    }
    private void validateImageUrl(String imageUrl) throws InvalidRequestException {
        try {
            Image image = ImageIO.read(new URL(imageUrl));
            if (image == null) {
                throw new InvalidRequestException("No image url found for this medication");
            }
        } catch (IOException ioe) {
            throw new InvalidRequestException("Invalid image url or path");
        }
    }

    private Supplier<String> generateFileName() {
        return () -> UUID.randomUUID().toString().substring(6);
    }
}
