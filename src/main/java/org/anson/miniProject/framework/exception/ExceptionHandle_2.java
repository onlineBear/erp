package org.anson.miniProject.framework.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获 - 只捕获 controller 层的异常
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle_2 {
}
