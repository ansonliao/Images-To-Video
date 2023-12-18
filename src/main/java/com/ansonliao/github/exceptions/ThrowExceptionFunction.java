package com.ansonliao.github.exceptions;

import java.io.IOException;

public interface ThrowExceptionFunction {
    /**
     * Throw exception
     *
     * @param message error message
     * @throws IOException
     */
    void throwMessage(String message) throws IOException;
}
