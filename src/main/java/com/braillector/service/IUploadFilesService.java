package com.braillector.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadFilesService {
    byte[] uploadFiles(MultipartFile file) throws Exception;
}
