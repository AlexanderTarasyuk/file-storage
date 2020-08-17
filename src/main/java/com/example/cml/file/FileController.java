package com.example.cml.file;

import com.example.cml.file.models.CustomPageResult;
import com.example.cml.file.models.FileModel;
import com.example.cml.file.models.Tags;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

/**
 * File controller.
 */
@RestController
@AllArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;

    /**
     * Gets file by id.
     *
     * @param id the id
     * @return the file by id
     */
    @GetMapping("/file/{id}")
    public FileModel getFileById(@PathVariable(value = "id") Integer id) {
        log.info("GET file by ID {}", id);
        return fileService.getFileById(id);
    }

    /**
     * Gets all files by tag.
     *
     * @param tags the tags
     * @param page the page
     * @param size the size
     * @return the all files by tag
     */
    @GetMapping("/file")
    public CustomPageResult getAllFilesByTag(@RequestParam(required = false) Optional<List<String>> tags,
                                             @RequestParam(required = false) Optional<Integer> page,
                                             @RequestParam(required = false) Optional<Integer> size) {
        log.info("GET all files by tags {} ", tags.orElse(Collections.emptyList()).toString());
        return fileService.findAllByTags(tags, page, size);
    }

    /**
     * Gets all files by criteria.
     *
     * @param q        criteria
     * @param pageable the pageable
     * @return the all files as a page by criteria
     */
    @GetMapping("/file/criteria")
    public Page<FileModel> getAllFilesByCriteria(
            @RequestParam(required = false) Optional<String> q,
            Pageable pageable) {
        log.info("GET file by ID {}", q.orElse("No criteria"));

        if (q.isPresent()) {
            return fileService.getAllFilesByCriteria(q, pageable);
        } else {
            return fileService.getAllFiles(pageable);
        }
    }


    /**
     * Creates file
     *
     * @param fileModel the file model
     * @return the response entity
     */
    @PostMapping("/file")
    public ResponseEntity<?> createFile(@Valid @RequestBody FileModel fileModel) {

        int id = fileService.createFile(fileModel);
        log.info("File is created with id {}", fileModel.getId());

        return ResponseEntity.ok().body("unique id :" + id);
    }

    /**
     * Updates file by id and given tags
     *
     * @param id   the id
     * @param tags the tags
     * @return the response entity
     */
    @PatchMapping("/file/{id}")
    public ResponseEntity<?> updateFile(@PathVariable(value = "id") Integer id,
                                        @RequestBody Tags tags) {
        fileService.updateFile(id, tags.getTags());
        log.info("File with id {} is updated with tags {}", id, String.join(",", tags.getTags()));
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes file by given id.
     *
     * @param id the id
     */
    @DeleteMapping("/file/{id}")
    @ResponseStatus(OK)
    public void deleteFile(@PathVariable(value = "id") int id) {

        log.info("File with id is deleted " + id);

        fileService.deleteFile(id);
    }

    /**
     * Deletes file by given  tags.
     *
     * @param id   the id
     * @param tags the tags
     */
    @DeleteMapping("/file/{id}/tags")
    @ResponseStatus(OK)
    @ResponseBody
    public void deleteFileTags(@PathVariable(value = "id") Integer id,
                               @RequestBody ArrayList<String> tags) {
        log.info("File with id" + id + " tags are deleted");


        fileService.deleteFileTags(id, tags);
    }
}
