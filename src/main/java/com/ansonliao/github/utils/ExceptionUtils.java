package com.ansonliao.github.utils;

import com.ansonliao.github.exceptions.ThrowExceptionFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author ansonliao
 * @date 7/2/2023
 */
public class ExceptionUtils {

    public static ThrowExceptionFunction checkDirectoryExistedOrNot(String dir) {
        return errorMessage -> {
            if (!Files.exists(new File(dir).toPath())){
                throw new IOException(errorMessage);
            }
        };
    }

    public static ThrowExceptionFunction checkFileExistedOrNot(String fileLink) {
        return errorMessage -> {
            if (!Files.exists(new File(fileLink).toPath())) {
                throw new FileNotFoundException(errorMessage);
            }
        };
    }
}
