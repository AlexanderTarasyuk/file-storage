package com.example.clm.file;

import com.example.clm.file.exceptions.NoSuchTags;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public Page<FileModel> findAll(Pageable page) {
        return fileRepository.findAll(page);
    }

    public void deleteFile(Integer id) {
        fileRepository.deleteById(id);
    }

    public int createFile(FileModel fileModel) {
        return fileRepository.save(fileModel).getId();
    }

    @Transactional
    public void updateFile(Integer id, @Valid ArrayList<String> tags) {
        Optional<FileModel> possibleFileModel = fileRepository.findById(id);
        FileModel fileModel = possibleFileModel.orElseThrow(NoSuchFieldError::new);
        fileModel.setTags(tags);
        fileRepository.save(fileModel);
    }

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

    public FileModel getFileById(Integer id) {
        return fileRepository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    public List<String> findAllFiles() {
        Query query = Query.findAll();
        SearchHits<FileModel> search = elasticsearchOperations.search(query, FileModel.class);
        List<String> listOfNameCard = new LinkedList<>();

        for (SearchHit<FileModel> sr : search) {
            listOfNameCard.add(sr.getContent().toString());
        }
        return listOfNameCard;
    }
}
