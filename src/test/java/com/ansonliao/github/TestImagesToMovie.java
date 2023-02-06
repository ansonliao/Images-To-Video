package com.ansonliao.github;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestImagesToMovie {

    @Test
    void testGetImages() throws IOException {
        ImagesToMovie imagesToMovie = new ImagesToMovie();
        Path testResourceDir = Paths.get("src", "test", "resources");
        assertEquals(3, imagesToMovie.getImages(testResourceDir.toFile().getPath()).size(), "Expected image files is 3.");
    }

    @Test
    void testCreateVideo1() throws IOException {
        ImagesToMovie imagesToMovie = new ImagesToMovie();
        Path testResourceDir = Paths.get("src", "test", "resources");
        String imageDir = testResourceDir.toFile().getPath();
        String videoFileName = Paths.get(imageDir, "video1.mp4").toFile().getPath();
        imagesToMovie.createVideo(imageDir, false, videoFileName);
        assertTrue(Paths.get(videoFileName).toFile().exists(), "Video file was not found.");
    }

    @Test
    void testCreateVideo2() throws IOException {
        ImagesToMovie imagesToMovie = new ImagesToMovie();
        Path testResourceDir = Paths.get("src", "test", "resources");
        String imageDir = testResourceDir.toFile().getPath();
        ArrayList<String> images = imagesToMovie.getImages(imageDir).parallelStream().map(File::getAbsolutePath).sorted().collect(toCollection(ArrayList::new));
        String videoFileName = Paths.get(imageDir, "video2.mp4").toFile().getPath();
        imagesToMovie.createVideo(images, videoFileName);
        assertTrue(Paths.get(videoFileName).toFile().exists(), "Video file was not found.");
    }

}
