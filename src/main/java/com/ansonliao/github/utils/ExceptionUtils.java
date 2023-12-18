package com.ansonliao.github.utils;

import com.ansonliao.github.ImagesToMovie;
import com.ansonliao.github.exceptions.ThrowExceptionFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class ExceptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    public static ThrowExceptionFunction checkDirectoryExistedOrNot(String dir) {
        return errorMessage -> {
            logger.info("Checking the directory existed or not: {}", dir);
            if (!Files.exists(new File(dir).toPath())){
                throw new IOException(errorMessage);
            }
            logger.info("The directory is existed: {}", dir);
        };
    }

    public static ThrowExceptionFunction checkFileExistedOrNot(String fileLink) {
        return errorMessage -> {
            logger.info("Checking the file existed or not: {}", fileLink);
            if (!Files.exists(new File(fileLink).toPath())) {
                throw new FileNotFoundException(errorMessage);
            }
            logger.info("The file is existed: {}", fileLink);
        };
    }
}
