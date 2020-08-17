package com.example.cml.file;

import com.example.cml.file.exceptions.NoSuchFile;
import com.example.cml.file.exceptions.NoSuchTags;
import com.example.cml.file.models.CustomPageResult;
import com.example.cml.file.models.FileModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * Finds all files by tags page.
     *
     * @param tags the tags
     * @param page the page
     * @param size the size
     * @return the page
     */
    public CustomPageResult findAllByTags(Optional<List<String>> tags, Optional<Integer> page, Optional<Integer> size) {

        Pageable paramPageable = PageRequest.of(page.orElse(0), size.orElse(10));
        Page<FileModel> fileModelPage;
        if (tags.isEmpty()) {
            fileModelPage = fileRepository.findAll(paramPageable);

        } else {
            fileModelPage = fileRepository.findByFilteredTagQuery(tags.orElse(Collections.emptyList()), paramPageable);
        }
        return new CustomPageResult(fileModelPage.getTotalElements(), fileModelPage.getTotalPages(), fileModelPage.getContent());
    }

    /**
     * Deletes file by id.
     *
     * @param id the id
     */
    public void deleteFile(Integer id) {
        fileRepository.deleteById(id);
    }


    /**
     * Creates file int.
     *
     * @param fileModel the file model
     * @return the int
     */
    public int createFile(FileModel fileModel) {
        String fileName = fileModel.getName();

        if (fileModel.getTags() != null) {
            fileModel.getTags().add(TagsAddUtil.getExtensionTag(fileModel.getName()));
        } else {
            fileModel.setTags(List.of(Objects.requireNonNull(TagsAddUtil.getExtensionTag(fileModel.getName()))));
        }

        return fileRepository.save(fileModel).getId();
    }

    /**
     * Updates file.
     *
     * @param id   the id
     * @param tags the tags
     */
    @Transactional
    public void updateFile(Integer id, @Valid List<String> tags) {
        Optional<FileModel> possibleFileModel = fileRepository.findById(id);
        FileModel fileModel = possibleFileModel.orElseThrow(() -> new NoSuchFile(tags));
        List<String> temp = fileModel.getTags();
        if (fileModel.getTags() != null) {
            List<String> newList = Stream.concat(temp.stream(), tags.stream())
                    .collect(Collectors.toList());
            fileModel.setTags(newList);
        } else {
            fileModel.setTags(tags);
        }
        fileRepository.save(fileModel);
    }

    /**
     * Deletess file tags.
     *
     * @param id   the id
     * @param tags the tags
     */
    public FileModel deleteFileTags(Integer id, List<String> tags) {
        Optional<FileModel> possibleFileModel = fileRepository.findById(id);
        FileModel fileModel = possibleFileModel.orElseThrow(() -> new NoSuchFile(tags));
        if (!fileModel.getTags().equals(tags)) {
            throw new NoSuchTags(tags);
        } else {
            fileModel.setTags(Collections.EMPTY_LIST);
        }
        fileRepository.save(fileModel);
        return fileModel;
    }

    /**
     * Gets file by id.
     *
     * @param id the id
     * @return the file by id
     */
    public FileModel getFileById(Integer id) {
        return fileRepository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    /**
     * Find all files list.
     *
     * @return the list
     */
    public List<String> findAllFiles() {
        Query query = Query.findAll();
        SearchHits<FileModel> search = elasticsearchOperations.search(query, FileModel.class);
        List<String> listOfNameCard = new LinkedList<>();

        for (SearchHit<FileModel> sr : search) {
            listOfNameCard.add(sr.getContent().toString());
        }
        return listOfNameCard;
    }

    /**
     * Gets all files by criteria.
     *
     * @param q        the q
     * @param pageable the pageable
     * @return the all files by criteria
     */
    public Page<FileModel> getAllFilesByCriteria(Optional<String> q, Pageable pageable) {
        Stream<FileModel> fileModelStream = (Stream<FileModel>) fileRepository.findAll();
        List<FileModel> fileList = fileModelStream.
                filter(fileModel -> !fileModel.getName().toLowerCase().contains(q.get().toLowerCase()))
                .collect(Collectors.toList());
        return new PageImpl<>(fileList);
    }

    /**
     * Gets all files.
     *
     * @param pageable the pageable
     * @return the all files
     */
    public Page<FileModel> getAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }
}
