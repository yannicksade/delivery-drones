package com.musala.delivery.drones.services;

import com.musala.delivery.drones.services.exceptions.InvalidRequestException;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderService {
    String uploadFile(MultipartFile multipartFile) throws InvalidRequestException;
}
