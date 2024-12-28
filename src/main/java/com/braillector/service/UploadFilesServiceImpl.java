package com.braillector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UploadFilesServiceImpl implements IUploadFilesService {
    private final IFileToolsService fileToolsService;

    @Autowired
    public UploadFilesServiceImpl(
        IFileToolsService fileToolsService
    ) {
        this.fileToolsService = fileToolsService;
    }

    @Override
    public byte[] uploadFiles(MultipartFile file) throws Exception {
        final String extensionFile = fileToolsService.checkExtensionFile(file);
        fileToolsService.checkMaxSize(file);
        List<String> data = fileToolsService.readLines(file, extensionFile);

        return fileToolsService.linesToBytes(data);
    }

}
