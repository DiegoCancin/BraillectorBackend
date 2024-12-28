package com.braillector.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileToolsService  {
    String checkExtensionFile(MultipartFile file) throws Exception;
    void checkMaxSize(MultipartFile file) throws Exception;
    List<String> readLines(MultipartFile file, String extensionFile) throws Exception;
    byte[] linesToBytes(List<String> lines) throws Exception;
}
