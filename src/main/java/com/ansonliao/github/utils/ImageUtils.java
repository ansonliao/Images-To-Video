package com.ansonliao.github.utils;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ansonliao.github.Configurations.getVideoConfigs;
import static com.ansonliao.github.utils.ExceptionUtils.checkDirectoryExistedOrNot;
import static java.util.stream.Collectors.toList;

public class ImageUtils {
    public static boolean isSupportedImageFormat(String image) {
        return getVideoConfigs().imageSupportedFormats().parallelStream()
                .map(String::toLowerCase)
                .collect(toList())
                .contains(FilenameUtils.getExtension(image).toLowerCase());
    }

    public static List<File> getImagesFromDirectory(String imageDir) throws IOException {
        checkDirectoryExistedOrNot(imageDir).throwMessage("The directory is not existed, path: " + imageDir);
        ArrayList<File> images = new ArrayList<>();
        File f = new File(imageDir);
        Arrays.stream(f.listFiles())
                .filter(file -> isSupportedImageFormat(file.getName()))
                .forEach(images::add);
        return images;
    }

    public static Map<ImageResolution, Integer> getTheMaxResolutionOfImage(List<File> images) throws IOException {
        List<BufferedImage> bufferedImages = new ArrayList<>();
        for (File image : images) {
            bufferedImages.add(ImageIO.read(image));
        }
        int maxHeight = bufferedImages.stream().map(BufferedImage::getHeight).max(Integer::compare).orElse(0);
        int maxWidth = bufferedImages.stream().map(BufferedImage::getWidth).max(Integer::compare).orElse(0);

        return new HashMap() {{
            put(ImageResolution.HEIGHT, maxHeight);
            put(ImageResolution.WIDTH, maxWidth);
        }};
    }

    private static Map<ImageResolution, Integer> getImageResolution(File imageFile) throws IOException {
        EnumMap<ImageResolution, Integer> imageResolution = new EnumMap<>(ImageResolution.class);
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        imageResolution.put(ImageResolution.HEIGHT, bufferedImage.getHeight());
        imageResolution.put(ImageResolution.WIDTH, bufferedImage.getWidth());
        return imageResolution;
    }

    public enum ImageResolution {
        HEIGHT, WIDTH;
    }
}
