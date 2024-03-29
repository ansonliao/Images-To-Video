package com.ansonliao.github;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.ansonliao.github.utils.ImageUtils.getImagesFromDirectory;
import static java.util.stream.Collectors.toCollection;

public class TestImagesToMovie {

    @Test
    public void testGetImages() throws IOException {
        ImagesToMovie imagesToMovie = new ImagesToMovie();
        Path testResourceDir = Paths.get("src", "test", "resources");
        Assert.assertEquals("Expected image files is 3.",
                            getImagesFromDirectory(testResourceDir.toFile().getPath()).size(), 3);
    }

    @Test
    public void testCreateVideo1() throws IOException {
        ImagesToMovie imagesToMovie = new ImagesToMovie();
        Path testResourceDir = Paths.get("src", "test", "resources");
        String imageDir = testResourceDir.toFile().getPath();
        String videoFileName = Paths.get(imageDir, "video1.mp4").toFile().getPath();
        imagesToMovie.createVideo(imageDir, false, videoFileName);
        Assert.assertTrue("Video file was not found.",
                          Paths.get(videoFileName).toFile().exists());
    }

    @Test
    public void testCreateVideo2() throws IOException {
        ImagesToMovie imagesToMovie = new ImagesToMovie();
        Path testResourceDir = Paths.get("src", "test", "resources");
        String imageDir = testResourceDir.toFile().getPath();
        ArrayList<String> images = getImagesFromDirectory(imageDir).parallelStream()
                .map(File::getAbsolutePath)
                .sorted().collect(toCollection(ArrayList::new));
        String videoFileName = Paths.get(imageDir, "video2.mp4").toFile().getPath();
        imagesToMovie.createVideo(images, videoFileName);
        Assert.assertTrue("Video file was not found.",
                          Paths.get(videoFileName).toFile().exists());
    }
}
