package com.example.clm.file;

import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;

class TagsAddeUtilsTest {

    @Test
    void getExtensionTag() {
        String expectedDocument = "document";
        String actualDocument = TagsAddeUtils.getExtensionTag("my.doc");
        assertEquals("Strings should be equal", expectedDocument, actualDocument);

        String expectedImage = "image";
        String actualImage = TagsAddeUtils.getExtensionTag("my.png");
        assertEquals("Strings should be equal", expectedImage, actualImage);

        String expectedAudio = "audio";
        String actualAudio = TagsAddeUtils.getExtensionTag("my.mp3");
        assertEquals("Strings should be equal", expectedAudio, actualAudio);
    }
}