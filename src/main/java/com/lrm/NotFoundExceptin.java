package com.lrm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义异常
 * ResponseStatus ：异常注解，  NOT_FOUND意味这是 404
 * 会把这个NotFoundExceptin意义为找不到404的状态去找  404页面
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExceptin extends RuntimeException{
    public NotFoundExceptin() {
    }

    public NotFoundExceptin(String message) {
        super(message);
    }

    public NotFoundExceptin(String message, Throwable cause) {
        super(message, cause);
    }
}
