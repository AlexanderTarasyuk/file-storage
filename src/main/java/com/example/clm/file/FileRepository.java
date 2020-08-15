package com.example.clm.file;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FileRepository extends ElasticsearchRepository<FileModel, Integer> {
}
