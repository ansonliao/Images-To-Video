package com.ansonliao.github.exceptions;

import java.io.IOException;

/**
 * @author ansonliao
 * @date 7/2/2023
 */
public interface ThrowExceptionFunction {

    /**
     * Throw exception
     *
     * @param message error message
     * @throws IOException
     */
    void throwMessage(String message) throws IOException;
}
