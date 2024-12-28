package com.braillector.controller;

import com.braillector.dto.request.UserCredentialsDto;
import com.braillector.dto.request.UserRegisterDto;
import com.braillector.dto.response.IUsersRespProjection;
import com.braillector.persitence.entity.UserConversionsEntity;
import com.braillector.persitence.entity.UsersEntity;
import com.braillector.service.IBraillectorService;
import com.braillector.service.IUploadFilesService;
import com.braillector.service.IDatabaseService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

@RestController
@CrossOrigin(origins = "http://localhost:4200/", allowCredentials = "true")
@RequestMapping("/api")
public class BraillectorController {

    private final IBraillectorService IBraillectorService;
    private final IDatabaseService iDatabaseService;
    private final IUploadFilesService uploadFilesService;

    @Autowired
    public BraillectorController(
        IBraillectorService IBraillectorService,
        IDatabaseService iDatabaseService,
        IUploadFilesService IUploadFIlesService
    ) {
        this.IBraillectorService = IBraillectorService;
        this.iDatabaseService = iDatabaseService;
        this.uploadFilesService = IUploadFIlesService;
    }

    @GetMapping("/text-to-braille")
    public ResponseEntity textToBraille(@RequestParam @NotNull String texto) {
        return ResponseEntity.ok(IBraillectorService.textoABraille(texto));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentialsDto data) {
        IUsersRespProjection response = iDatabaseService.getUser(data.getEmail(), data.getPassword());
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save-user")
    public UsersEntity saveUser(@RequestBody UserRegisterDto userDto) {
        return iDatabaseService.saveUser(userDto);
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=braillectorOutput.txt");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8");

        try {
            return new ResponseEntity<>(uploadFilesService.uploadFiles(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Error al generar el archivo " + e.getMessage()
            );
        }

    }

    @GetMapping("/conversions")
    public ResponseEntity conversions(@RequestParam @NotNull Long userId) {
        Long response = iDatabaseService.getUsersCount(userId);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save-conversions")
    public ResponseEntity saveConversions(@RequestParam @NotNull Long userId) {
        UserConversionsEntity response = iDatabaseService.saveConversion(userId);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response.getConversionId());
    }

    @PostMapping("/update-plan")
    public ResponseEntity updatePlan(@RequestParam @NotNull Long userId, @RequestParam @NotNull Long planId) {
        try {
            IUsersRespProjection updatedUser = iDatabaseService.updateUserPlan(userId, planId);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el plan: " + e.getMessage());
        }
    }
}
