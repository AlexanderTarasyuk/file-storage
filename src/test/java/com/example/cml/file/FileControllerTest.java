package com.example.cml.file;

import com.example.cml.TestRunner;
import com.example.cml.file.models.FileModel;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestRunner
@DirtiesContext
class FileControllerTest {
    private FileModel mockFileModel;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FileRepository repository;
    @Autowired
    FileService fileService;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll();
        mockFileModel = new FileModel();
        mockFileModel.setId("1");
        mockFileModel.setSize(1234);
        mockFileModel.setName("mockfile");
        mockFileModel.setTags(List.of("tag3"));
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    void getFileById() throws Exception {
        FileModel fileModel = new FileModel("1", "myfile", 123);
        String id = repository.save(fileModel).getId();
        mockMvc.perform(get("/file/id")
                .contentType("application/json")
                .content(fromResource("json/create-file.json")));
    }

    @Test
    void getAllFilesByTag() throws Exception {
        repository.deleteAll();
        FileModel fileModel1 = new FileModel("1", "myfile", 123);
        FileModel fileModel2 = new FileModel("2", "myfileNew", 321);
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
        FileModel fileModel = new FileModel("1", "myfile", 123);
        mockMvc.perform(post("/file")
                .contentType("application/json")
                .content(fromResource("json/create-file.json")))
                .andExpect(status().isOk());

    }

    @Test
    void updateFile() {
    }

    @Test
    void deleteFile() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/file/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteFileTags() throws Exception {
        mockFileModel = new FileModel();
        mockFileModel.setId("1");
        mockFileModel.setSize(1234);
        mockFileModel.setName("mockfile");
        mockFileModel.setTags(List.of("tag3"));
        repository.save(mockFileModel);
        Mockito.when(fileService.deleteFileTags(Mockito.any(), Mockito.anyList()))
                .thenReturn(mockFileModel);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/file/1/tags")
                .accept(MediaType.APPLICATION_JSON)
                .content("[\"tag1\", \"tag2\", \"tag3\"]")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
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