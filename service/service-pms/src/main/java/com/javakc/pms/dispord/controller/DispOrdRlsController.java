package com.javakc.pms.dispord.controller;

import com.javakc.commonutils.api.APICODE;
import com.javakc.pms.dispord.client.MesClient;
import com.javakc.pms.dispord.entity.DispOrdRls;
import com.javakc.pms.dispord.service.DispOrdRlsService;
import com.javakc.pms.dispord.vo.DispOrdRlsQuery;
import com.javakc.servicebase.handler.HctfException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @program: javakc-parent
 * @description: 调度指令管理
 * @author: zhang yi xin
 * @create: 2020/11/30 17:26
 */
@RestController
@RequestMapping("/pms/dispordrls")
//@CrossOrigin
public class DispOrdRlsController {
    @Autowired
    private DispOrdRlsService dispOrdRlsService;

    @Autowired
    MesClient mesClient;

    @ApiOperation(("带条件的分页查询 - 调度指令管理"))
    @PostMapping("{pageNum}/{pageSize}")
    public APICODE findPageDispOrdRls(@RequestBody(required = false) DispOrdRlsQuery dispOrdRlsQuery, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        Page<DispOrdRls> page = dispOrdRlsService.findPageDispOrdRls(dispOrdRlsQuery, pageNum, pageSize);
        List<DispOrdRls> list = page.getContent();
        long totalElements = page.getTotalElements();
        return APICODE.ok().data("total", totalElements).data("items", list);
    }

    @ApiOperation("根据调度指令还礼ID进行单条数据获取")
    @GetMapping("{dispOrdRlsId}")
    public APICODE view(@PathVariable("dispOrdRlsId") String dispOrdRlsId) {
        DispOrdRls dispOrdRls = dispOrdRlsService.getById(dispOrdRlsId);
        return APICODE.ok().data("dispOrdRls", dispOrdRls);
    }

    @ApiOperation("下达集团调度指令")
    @GetMapping("updateRelease/{dispOrdRlsId}")
    public APICODE updateRelease(@PathVariable("dispOrdRlsId") String dispOrdRlsId) {
        DispOrdRls dispOrdRls = dispOrdRlsService.getById(dispOrdRlsId);
        // 改变为已下达状态
        dispOrdRls.setIsRelease(1);
        dispOrdRls.setReleaseTime(new Date());

        // 调用 mes 服务，来进行数据的下达
        APICODE apicode = mesClient.savePmsDispOrdRls(dispOrdRls);
        if (apicode.getCode() == 20001) {
            throw new HctfException(20001, apicode.getMessage());
        }

        // 修改
        dispOrdRlsService.saveOrUpdate(dispOrdRls);
        return APICODE.ok();
    }
}
