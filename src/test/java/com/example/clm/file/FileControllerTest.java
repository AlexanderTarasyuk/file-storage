package com.example.clm.file;

import com.example.clm.TestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@TestRunner
@DirtiesContext
class FileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FileRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    void getFileById() throws Exception {
        FileModel fileModel = new FileModel(1, "myfile", 123 );
        Integer id = repository.save(fileModel).getId();
        mockMvc.perform(get("/file/id")
                .contentType("application/json")
                .content(fromResource("json/create-file.json")));
    }

    @Test
    void getAllFilesByTag() throws Exception {
        repository.deleteAll();
        FileModel fileModel1 = new FileModel(1, "myfile", 123 );
        FileModel fileModel2 = new FileModel(2, "myfileNew", 321 );
        repository.save(fileModel1);
        repository.save(fileModel2);
        mockMvc.perform(get("/file"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].size", notNullValue()))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", notNullValue()));
    }

    @Test
    void getAllFilesByCriteria() {
    }

    @Test
    void createFile() throws Exception {
        FileModel fileModel = new FileModel(1, "myfile", 123 );
        mockMvc.perform(post("/file")
                .contentType("application/json")
                .content(fromResource("json/create-file.json")))
                .andExpect(status().isOk());

    }

    @Test
    void updateFile() {
    }

    @Test
    void deleteFile() {
    }

    @Test
    void deleteFileTags() {
    }

    public String fromResource(String path) {
        try {
            File file = ResourceUtils.getFile("classpath:" + path);
            return Files.readString(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}