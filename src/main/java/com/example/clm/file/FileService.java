package com.example.clm.file;

import com.example.clm.file.exceptions.NoSuchTags;
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
 * The type File service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * Find all by tags page.
     *
     * @param tags     the tags
     * @param page     the page
     * @param size     the size
     * @param pageable the pageable
     * @return the page
     */
    public Page<FileModel> findAllByTags(Optional<List<String>> tags, Optional<Integer> page, Optional<Integer> size, Pageable pageable) {

        Pageable paramPageable = PageRequest.of(page.orElse(0), size.orElse(2));
        Page<FileModel> fileModelPage;
        if (tags.isEmpty()) {
            fileModelPage = fileRepository.findAll(paramPageable);

        } else {

            fileModelPage = fileRepository.findByFilteredTagQuery(tags.orElse(Collections.emptyList()), paramPageable);
        }
        return fileModelPage;
    }

    /**
     * Delete file.
     *
     * @param id the id
     */
    public void deleteFile(Integer id) {
        fileRepository.deleteById(id);
    }

    /**
     * Find by tag using declared query page.
     *
     * @param tag  the tag
     * @param page the page
     * @return the page
     */
    public Page<FileModel> findByTagUsingDeclaredQuery(List<String> tag, PageRequest page) {
        return fileRepository.findByFilteredTagQuery(tag, page);
    }

    /**
     * Create file int.
     *
     * @param fileModel the file model
     * @return the int
     */
    public int createFile(FileModel fileModel) {
        String fileName = fileModel.getName();

        if (fileModel.getTags() != null) {
            fileModel.getTags().add(TagsAddeUtils.getExtensionTag(fileModel.getName()));
        }

        return fileRepository.save(fileModel).getId();
    }

    /**
     * Update file.
     *
     * @param id   the id
     * @param tags the tags
     */
    @Transactional
    public void updateFile(Integer id, @Valid ArrayList<String> tags) {
        Optional<FileModel> possibleFileModel = fileRepository.findById(id);
        FileModel fileModel = possibleFileModel.orElseThrow(NoSuchFieldError::new);
        fileModel.setTags(tags);
        fileRepository.save(fileModel);
    }

    /**
     * Delete file tags.
     *
     * @param id   the id
     * @param tags the tags
     */
    public void deleteFileTags(Integer id, ArrayList<String> tags) {
        Optional<FileModel> possibleFileModel = fileRepository.findById(id);
        FileModel fileModel = possibleFileModel.orElseThrow(NoSuchFieldError::new);
        if (!fileModel.getTags().equals(tags)) {
            throw new NoSuchTags(tags);
        } else {
            fileModel.setTags(Collections.EMPTY_LIST);
        }
        fileRepository.save(fileModel);
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
     * Find all page.
     *
     * @param page the page
     * @return the page
     */
    public Page<FileModel> findAll(Pageable page) {
        return fileRepository.findAll(page);
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
