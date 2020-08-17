package com.example.cml.file.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageResult {

    private long total;
    private long page;
    private List<FileModel> content;

}
