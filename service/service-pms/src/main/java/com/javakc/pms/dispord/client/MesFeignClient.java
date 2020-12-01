package com.javakc.pms.dispord.client;

import com.javakc.commonutils.api.APICODE;
import com.javakc.pms.dispord.entity.DispOrdRls;
import org.springframework.stereotype.Component;

/**
 * @program: javakc-parent
 * @description:
 * @author: zhang yi xin
 * @create: 2020/12/1 14:08
 */
@Component
public class MesFeignClient implements MesClient {
    @Override
    public APICODE savePmsDispOrdRls(DispOrdRls dispOrdRls) {
        return APICODE.ERROR().message("MES 服务调用失败 - 下达");
    }
}
