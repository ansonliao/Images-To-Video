package com.ansonliao.github.utils;

import org.apache.commons.io.FilenameUtils;

import static com.ansonliao.github.Configurations.getVideoConfigs;
import static java.util.stream.Collectors.toList;

public class ImageUtils {

    public static boolean isSupportedImageFormat(String image) {
        return getVideoConfigs().imageSupportedFormats().parallelStream()
                .map(String::toLowerCase)
                .collect(toList())
                .contains(FilenameUtils.getExtension(image).toLowerCase());
    }
}
