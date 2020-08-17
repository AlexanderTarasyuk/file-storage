package com.example.cml.file;

import com.example.cml.file.models.FileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The  File repository.
 */
@Repository
interface FileRepository extends ElasticsearchRepository<FileModel, Integer> {

    /**
     * Finds files by filtered tag query page.
     *
     * @param tags     the tags
     * @param pageable the pageable
     * @return the page
     */
    @Query("{\"bool\": {\"must\": " +
            "{\"match_all\": {}}, \"filter\": {\"term\": {\"tags\": \"?0\" }}}}")
    Page<FileModel> findByFilteredTagQuery(List<String> tags, Pageable pageable);
}
