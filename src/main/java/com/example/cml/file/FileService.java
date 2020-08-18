package com.example.cml.file;

import com.example.cml.file.exceptions.NoSuchFile;
import com.example.cml.file.exceptions.NoSuchTags;
import com.example.cml.file.models.CustomPageResult;
import com.example.cml.file.models.FileModel;
import joptsimple.internal.Strings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    /**
     * Finds all files by tags page.
     *
     * @param tags the tags
     * @param page the page
     * @param size the size
     * @param q
     * @return the page
     */
    public CustomPageResult findAllByTags(Optional<List<String>> tags,
                                          Optional<Integer> page,
                                          Optional<Integer> size,
                                          Optional<String> q,
                                          Pageable pageable) {

        Page<FileModel> fileModelPage;
        PageRequest pageRequest = PageRequest.of(page.orElse(0), size.orElse(10));
        List<FileModel> fileList;
        if (tags.isEmpty()) {
            fileModelPage = fileRepository.findAll(pageRequest);
        } else {
            fileModelPage = fileRepository.findByFilteredTagQuery(Strings.join(tags.orElse(Collections.emptyList()), " "), pageRequest);
        }
        fileList = q.map(s -> fileModelPage.getContent().stream().
                filter(fileModel -> !fileModel.getName().toLowerCase().contains(s.toLowerCase()))
                .collect(Collectors.toList())).orElseGet(fileModelPage::getContent);
        PagedListHolder pagedListHolder = new PagedListHolder(fileList);
        pagedListHolder.setPage(page.orElse(0));
        pagedListHolder.setPageSize(size.orElse(10));
        return new CustomPageResult(pagedListHolder);
    }

    /**
     * Deletes file by id.
     *
     * @param id the id
     */
    public void deleteFile(String id) {
        fileRepository.deleteById(id);
    }

    /**
     * Creates file int.
     *
     * @param fileModel the file model
     * @return the int
     */
    public String createFile(FileModel fileModel) {

        if (fileModel.getTags() != null && TagsAddUtil.getExtensionTag(fileModel.getName()) != null) {
            fileModel.getTags().add(TagsAddUtil.getExtensionTag(fileModel.getName()));
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
    public void updateFile(String id, @Valid List<String> tags) {
        Optional<FileModel> possibleFileModel = fileRepository.findById(id);
        FileModel fileModel = possibleFileModel.orElseThrow(() -> new NoSuchFile(Strings.join(tags, " ")));
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
    public FileModel deleteFileTags(String id, List<String> tags) {
        Optional<FileModel> possibleFileModel = fileRepository.findById(id);
        FileModel fileModel = possibleFileModel.orElseThrow(() -> new NoSuchFile(Strings.join(tags, " ")));
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
    public FileModel getFileById(String id) {
        return fileRepository.findById(id).orElseThrow(() -> new NoSuchFile(id));
    }

    /**
     * Gets all files by criteria.
     *
     * @param q the criteria of search
     * @return the all files by criteria
     */
    public Page<FileModel> getAllFilesByCriteria(Optional<String> q) {
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
