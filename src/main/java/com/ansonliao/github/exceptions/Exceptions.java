package com.ansonliao.github.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class Exceptions {
    private static final Logger logger = LoggerFactory.getLogger(Exceptions.class);

    public static ThrowExceptionFunction checkIfDirectoryExisted(String dir) {
        return errorMessage -> {
            logger.info("Checking the directory existed or not: {}", dir);
            if (!Files.exists(new File(dir).toPath())) {
                throw new IOException(errorMessage);
            }
            logger.info("The directory is existed: {}", dir);
        };
    }

    public static ThrowExceptionFunction checkIfFileExisted(String fileLink) {
        return errorMessage -> {
            logger.info("Checking the file existed or not: {}", fileLink);
            if (!Files.exists(new File(fileLink).toPath())) {
                throw new FileNotFoundException(errorMessage);
            }
            logger.info("The file is existed: {}", fileLink);
        };
    }

    public static ThrowExceptionFunction checkIfDirectory(String directory) {
        return errorMessage -> {
            logger.info("Checking the path is directory or not: {}", directory);
            if (Files.isDirectory(new File(directory).toPath())) {
                throw new FileNotFoundException(errorMessage);
            }
            logger.info("The path is the directory: {}", directory);
        };
    }

    public static ThrowExceptionFunction checkIfFile(String filePath) {
        return errorMessage -> {
            logger.info("Checking the path is file or not: {}", filePath);
            if (Files.isDirectory(new File(filePath).toPath())) {
                throw new FileNotFoundException(errorMessage);
            }
            logger.info("The path is the directory: {}", filePath);
        };
    }
}
