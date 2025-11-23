package com.identify.product.FamilyKart.commonservices;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileServices {


    String uploadImagesToServer(String path, MultipartFile productImageFile) throws IOException;
}
