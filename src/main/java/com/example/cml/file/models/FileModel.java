package com.example.cml.file.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type File model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "id")
@Validated
public class FileModel {

    private static AtomicInteger counter = new AtomicInteger(0);

    @Id
    private String id;
    @NotNull
    private String name;
    @Positive
    @NotNull
    private long size;
    private List<String> tags;

    /**
     * Instantiates a new File model.
     *
     * @param id   the id
     * @param name the name
     * @param size the size
     */
    public FileModel(String id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    /**
     * Instantiates a new File model.
     *
     * @param name the name
     * @param size the size
     * @param tags the tags
     */
    public FileModel(String name, long size, ArrayList<String> tags) {
        counter.getAndIncrement();
        this.name = name;
        this.size = size;
        this.tags = tags;
    }
}
