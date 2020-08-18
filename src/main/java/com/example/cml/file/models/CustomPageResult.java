package com.example.cml.file.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.support.PagedListHolder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageResult {

    private long total;
    private long page;
    private List<FileModel> content;

    public CustomPageResult(PagedListHolder pagedListHolder) {
        this.total = pagedListHolder.getNrOfElements();
        this.page = pagedListHolder.getPageCount();
        this.content = pagedListHolder.getPageList();
    }

}
