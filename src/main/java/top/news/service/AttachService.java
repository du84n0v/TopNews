package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.news.dto.attach.AttachDTO;
import top.news.dto.attach.AttachShortInfoDTO;
import top.news.entity.AttachEntity;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.repository.AttachRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AttachService {

    @Autowired
    private AttachRepository attachRepository;
    @Value("${attache.folder}")
    private String attacheFolder;

    @Value("${server.url}")
    private String attachUrl;

    public AttachDTO upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ItemNotFoundException("File not found");
        }

        try {
            String pathFolder = getYmDString();
            String key = UUID.randomUUID().toString();
            String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));

            File folder = new File(attacheFolder + "/" + pathFolder);
            if (!folder.exists()) {
                boolean t = folder.mkdirs();
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attacheFolder + "/" + pathFolder + "/" + key + "." + extension);
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setVisible(true);
            attachRepository.save(entity);

            return toDTO(entity);
        } catch (IOException e) {
            throw new AppBadRequestException("Upload went wrong");
        }
    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOrigenName());
        attachDTO.setSize(entity.getSize());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedData(entity.getCreatedDate());
        attachDTO.setUrl(openURL(entity.getId()));
        return attachDTO;
    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String openURL(String fileId) {
        return attachUrl + "/attaches/open/" + fileId;
    }

    public ResponseEntity<Resource> open(String id) {
        AttachEntity entity = getEntity(id);
        Path filePath = Paths.get(attacheFolder + "/" + entity.getPath() + "/" + entity.getId()).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + filePath);
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public AttachEntity getEntity(String id) {
        return attachRepository.findById(id).orElseThrow(() -> new AppBadRequestException("File not found"));
    }

    public Resource download(String fileId) {
        AttachEntity attach = getEntity(fileId);
        Resource resource = null;
        try{
            Path filePath = Paths.get(attacheFolder + "/" + attach.getPath() + "/" + fileId).normalize();
            resource = new UrlResource(filePath.toUri());

            if(resource.exists() && resource.isReadable()){
                return resource;
            }
            else{
                throw new AppBadRequestException("File is not valid");
            }
        }catch (RuntimeException e){
            throw new AppBadRequestException(e.getMessage());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<AttachDTO> getContent(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttachEntity> pages = attachRepository.findAll(pageable);
        List<AttachDTO> content = new LinkedList<>();
        for (AttachEntity attach : pages) {
            content.add(toDTO(attach));
        }
        return new PageImpl<>(content, pageable, pages.getTotalElements());
    }

    public String deleteContent(String fileId) {
        AttachEntity attach = getEntity(fileId);

        Path filePath = Paths.get(attacheFolder + "/" + attach.getPath() + "/" + fileId);
        boolean isDelete = false;
        try{
            isDelete = Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!isDelete){
            throw new AppBadRequestException("File cannot be deleted");
        }

        attachRepository.delete(attach);

        Path parentFolder = filePath.getParent();
        if (parentFolder != null && Files.isDirectory(parentFolder)) {
            try (var stream = Files.newDirectoryStream(parentFolder)) {
                if (!stream.iterator().hasNext()) {
                    Files.delete(parentFolder);
                }
            }
            catch (RuntimeException | IOException e){
                throw new RuntimeException(e.getMessage());
            }
        }

        return "File successfully deleted";
    }

    public AttachShortInfoDTO openDTO(String id) {
        return new AttachShortInfoDTO(id, attachUrl + "/attach/open/" + id);
    }
}
