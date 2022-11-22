package com.danieljoanol.dontbuypets.service.cloud;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
    private String apiKey = System.getenv("CLOUDINARY_API_KEY");
    private String apiSecret = System.getenv("CLOUDINARY_API_SECRET");

    private Map params = ObjectUtils.asMap(
            "cloud_name", this.cloudName,
            "api_key", this.apiKey,
            "api_secret", this.apiSecret
        );

    private Cloudinary cloudinary = new Cloudinary(params);

    @Override
    public String uploadImage(MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException {

        if (image.isEmpty()) {
            String message = "The file is empty!";
            log.error(message);
            throw new EmptyImageException(message);
        } else if (!Objects.requireNonNull(image.getContentType()).equalsIgnoreCase("image/png") &&
                !Objects.requireNonNull(image.getContentType()).equalsIgnoreCase("image/jpeg") && 
                !Objects.requireNonNull(image.getContentType()).equalsIgnoreCase("image/jpg")) {
            String message = "Unsupported file format. The supported formats are png, jpg and jpeg";
            log.error(message);
            throw new InvalidImageFormatException(message);
        }

        Map response = cloudinary.uploader().upload(
                image.getBytes(), ObjectUtils.emptyMap());

        return response.get("secure_url").toString();
    }
}