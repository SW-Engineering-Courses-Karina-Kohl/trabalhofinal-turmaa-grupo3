package br.edu.ufrgs.controller;

import br.edu.ufrgs.dto.PaginatedResultDto;
import br.edu.ufrgs.dto.UploadDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    @PostMapping
    public ResponseEntity<UploadDto> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {

        UploadDto response = new UploadDto(
                UUID.randomUUID().toString(),
                file.getOriginalFilename(),
                LocalDateTime.now().toString(),
                file.getSize() / (1024.0 * 1024.0),
                "Processing"
        );

        return ResponseEntity.ok(response);
    }
    
    @GetMapping
public PaginatedResultDto<UploadDto> listUploads() {

    var uploads = List.of(
            new UploadDto(
                    "1",
                    "Q2_Final_Sales_Batch.csv",
                    "2023-10-24T14:32:00Z",
                    4.2,
                    "Completed"
            ),
            new UploadDto(
                    "2",
                    "Mid_Month_Draft_Global.csv",
                    "2023-10-25T09:15:00Z",
                    12.8,
                    "Processing"
            )
    );

    return new PaginatedResultDto<>(
            uploads,
            1,
            10,
            uploads.size(),
            1
    );
}
}