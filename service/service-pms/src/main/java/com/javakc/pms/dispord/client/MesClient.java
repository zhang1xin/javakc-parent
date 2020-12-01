package com.javakc.pms.dispord.client;

import com.javakc.commonutils.api.APICODE;
import com.javakc.pms.dispord.entity.DispOrdRls;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: javakc-parent
 * @description:
 * @author: zhang yi xin
 * @create: 2020/12/1 13:08
 */
@FeignClient(name = "service-mes")
@Component
public interface MesClient {

    /**
     * 指定要调用的接口，保证与服务中的接口的一致性
     * @param dispOrdRls
     * @return
     */
    @PostMapping("/mes/dispordrls/savePmsDispOrdRls")
    public APICODE savePmsDispOrdRls(@RequestBody DispOrdRls dispOrdRls);
}
