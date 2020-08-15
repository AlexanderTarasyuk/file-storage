package com.example.clm.file;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/file/{id}")
    public FileModel getFileById(@PathVariable(value = "id") Integer id) {
        return fileService.getFileById(id);
    }

    @GetMapping("/file")
    public Page<FileModel> getAllFiles(Pageable page) {
        return fileService.findAll(page);
    }

    @PostMapping("/file")
    public ResponseEntity<?> createFile(@Valid @RequestBody FileModel fileModel) {
        int id = fileService.createFile(fileModel);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/file/{id}/tags")
    public ResponseEntity<?> updateFile(@PathVariable(value = "id") Integer id,
                                        @RequestBody ArrayList<String> tags) {
        fileService.updateFile(id, tags);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/file/{id}")
    @ResponseStatus(OK)
    public void deleteFile(@PathVariable(value = "id") int id) {
        fileService.deleteFile(id);
    }

    @DeleteMapping("/file/{id}/tags")
    @ResponseStatus(OK)
    @ResponseBody
    public void deleteFileTags(@PathVariable(value = "id") Integer id,
                                 @RequestBody ArrayList<String> tags) {
        fileService.deleteFileTags(id, tags);
    }

//    @ExceptionHandler
//    @ResponseStatus(BAD_REQUEST)
//    public String noSuchFile(NoSuchFile ex) {
//        return ex.toString();
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(BAD_REQUEST)
//    public void noSuchFile(NoSuchTags ex) {
//    }
}
