package com.javakc.mes.dispordrls.controller;

import com.javakc.commonutils.api.APICODE;
import com.javakc.mes.dispordrls.entity.DispOrdRls;
import com.javakc.mes.dispordrls.service.DispOrdRlsService;
import com.javakc.mes.dispordrls.vo.DispOrdRlsQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: javakc-parent
 * @description: 调度指令管理
 * @author: zhang yi xin
 * @create: 2020/11/30 17:26
 */
@RestController
@RequestMapping("/mes/dispordrls")
@CrossOrigin
public class DispOrdRlsController {
    @Autowired
    private DispOrdRlsService dispOrdRlsService;

    @ApiOperation(("带条件的分页查询 - 调度指令管理"))
    @PostMapping("{pageNum}/{pageSize}")
    public APICODE findPageDispOrdRls(@RequestBody(required = false) DispOrdRlsQuery dispOrdRlsQuery, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        Page<DispOrdRls> page = dispOrdRlsService.findPageDispOrdRls(dispOrdRlsQuery, pageNum, pageSize);
        List<DispOrdRls> list = page.getContent();
        long totalElements = page.getTotalElements();
        return APICODE.ok().data("total", totalElements).data("items", list);
    }

    @ApiOperation("根据调度指令管理ID进行单条数据获取")
    @GetMapping("{dispOrdRlsId}")
    public APICODE view(@PathVariable("dispOrdRlsId") String dispOrdRlsId) {
        DispOrdRls dispOrdRls = dispOrdRlsService.getById(dispOrdRlsId);
        return APICODE.ok().data("dispOrdRls", dispOrdRls);
    }

    @ApiOperation("接收集团下达的指令信息")
    @PostMapping("savePmsDispOrdRls")
    public APICODE savePmsDispOrdRls(@RequestBody DispOrdRls dispOrdRls) {
        dispOrdRlsService.saveOrUpdate(dispOrdRls);
        return APICODE.ok();
    }
}
