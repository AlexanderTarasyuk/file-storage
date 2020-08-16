package com.example.clm.file;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

/**
 * The type File controller.
 */
@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileRepository fileRepository;

    /**
     * Gets file by id.
     *
     * @param id the id
     * @return the file by id
     */
    @GetMapping("/file/{id}")
    public FileModel getFileById(@PathVariable(value = "id") Integer id) {
        return fileService.getFileById(id);
    }

//    @GetMapping("/file")
//    public Page<FileModel> getAllFiles(Pageable page) {
//        return fileService.findAll(page);
//    }

    /**
     * Gets all files by tag.
     *
     * @param tags     the tags
     * @param page     the page
     * @param size     the size
     * @param pageable the pageable
     * @return the all files by tag
     */
    @GetMapping("/file")
    public Page<FileModel> getAllFilesByTag(@RequestParam(required = false) Optional<List<String>> tags,
                                            @RequestParam(required = false) Optional<Integer> page,
                                            @RequestParam(required = false) Optional<Integer> size,
                                            Pageable pageable) {
        return fileService.findAllByTags(tags, page, size, pageable);
    }

    /**
     * Gets all files by criteria.
     *
     * @param q        the q
     * @param pageable the pageable
     * @return the all files by criteria
     */
    @GetMapping("/file/criteria")
    public Page<FileModel> getAllFilesByCriteria(
            @RequestParam(required = false) Optional<String> q,
            Pageable pageable) {
        if (q.isPresent()) {
            return fileService.getAllFilesByCriteria(q, pageable);
        } else {
            return fileService.getAllFiles(pageable);
        }
    }


    /**
     * Create file response entity.
     *
     * @param fileModel the file model
     * @return the response entity
     */
    @PostMapping("/file")
    public ResponseEntity<?> createFile(@Valid @RequestBody FileModel fileModel) {
        int id = fileService.createFile(fileModel);
        return ResponseEntity.ok().body(id);
    }

    /**
     * Update file response entity.
     *
     * @param id   the id
     * @param tags the tags
     * @return the response entity
     */
    @PatchMapping("/file/{id}/tags")
    public ResponseEntity<?> updateFile(@PathVariable(value = "id") Integer id,
                                        @RequestBody ArrayList<String> tags) {
        fileService.updateFile(id, tags);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete file.
     *
     * @param id the id
     */
    @DeleteMapping("/file/{id}")
    @ResponseStatus(OK)
    public void deleteFile(@PathVariable(value = "id") int id) {
        fileService.deleteFile(id);
    }

    /**
     * Delete file tags.
     *
     * @param id   the id
     * @param tags the tags
     */
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
