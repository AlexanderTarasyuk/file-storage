package com.example.clm.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "file_storage")
@Validated
public class FileModel {

    private static AtomicInteger counter = new AtomicInteger(0);

    @Id
    @Field(type = FieldType.Integer)
    private int id;
    @Field(type = FieldType.Text, fielddata = true)
    @NonNull
    private String name;
    @Field(type = FieldType.Long, fielddata = true)
    @Positive
    private long size;
    @Field(type = FieldType.Keyword)
    private List<String> tags;

    public FileModel(int id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public FileModel(String name, long size, ArrayList<String> tags) {
        counter.getAndIncrement();
        id = counter.get();
        this.name = name;
        this.size = size;
        this.tags = tags;
    }
}
