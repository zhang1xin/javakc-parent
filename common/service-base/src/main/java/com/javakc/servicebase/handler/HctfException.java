package com.javakc.servicebase.handler;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: javakc-parent
 * @description:
 * @author: zhang yi xin
 * @create: 2020/11/23 22:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HctfException extends RuntimeException{

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;
}
