package com.identify.product.FamilyKart.commonservices;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileServices{



    @Override
    public String uploadImagesToServer(String path, MultipartFile productImageFile) throws IOException {
        String originalImageFileName = productImageFile.getOriginalFilename();
        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(originalImageFileName.substring(originalImageFileName.lastIndexOf('.')));
        String filePath= path + File.separator+ fileName;
        File folder=new File(path);
        if (!folder.exists())
            folder.mkdir();

        //upload to server
        Files.copy(productImageFile.getInputStream(), Paths.get(filePath));
        return fileName;

    }
}
