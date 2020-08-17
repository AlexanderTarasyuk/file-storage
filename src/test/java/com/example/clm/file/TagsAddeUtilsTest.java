package com.example.clm.file;

import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;

class TagsAddeUtilsTest {

    @Test
    void getExtensionTag() {
        String expectedDocument = "document";
        String actual1 = TagsAddeUtils.getExtensionTag("my.doc");
        assertEquals("Strings should be equal", expectedDocument, actual1);

        String expectedImage = "image";
        String actual2 = TagsAddeUtils.getExtensionTag("my.png");
        assertEquals("Strings should be equal", expectedImage, actual2);

        String expectedAudio = "audio";
        String actual3 = TagsAddeUtils.getExtensionTag("my.mp3");
        assertEquals("Strings should be equal", expectedAudio, actual3);
    }
}