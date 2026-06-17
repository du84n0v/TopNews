package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.news.dto.attach.AttachDTO;
import top.news.service.AttachService;

@RestController
@RequestMapping("/attaches")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(attachService.upload(file));
    }

    @GetMapping("/open/{fileId}")
    public ResponseEntity<Resource> open(@PathVariable String fileId) {
        return attachService.open(fileId);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable String fileId){
        Resource resource = attachService.download(fileId);
        String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok().
                contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<Page<AttachDTO>> pagination(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "size", defaultValue = "2") Integer size){
        return ResponseEntity.ok(attachService.getContent(page-1, size));
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<String> delete(@PathVariable String fileId){
        return ResponseEntity.ok(attachService.deleteContent(fileId));
    }


}
