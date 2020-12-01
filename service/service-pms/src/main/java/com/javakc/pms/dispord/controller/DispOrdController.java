package com.javakc.pms.dispord.controller;

import com.alibaba.excel.EasyExcel;
import com.javakc.commonutils.api.APICODE;
import com.javakc.pms.dispord.entity.DispOrd;
import com.javakc.pms.dispord.listener.ExcelListener;
import com.javakc.pms.dispord.service.DispOrdService;
import com.javakc.pms.dispord.vo.DispOrdData;
import com.javakc.pms.dispord.vo.DispOrdQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: javakc-parent
 * @description:
 * @author: zhang yi xin
 * @create: 2020/11/23 19:30
 */
@Api(tags = "调度指令库控制器")
@RestController
@RequestMapping("/pms/dispord")
//@CrossOrigin
public class DispOrdController {
    @Autowired
    private DispOrdService dispOrdService;

    @ApiOperation("查询所有调度指令库数据")
    @GetMapping
    public APICODE findAll() {
        List<DispOrd> list = dispOrdService.findAll();
        return APICODE.ok().data("items", list);
    }

    @ApiOperation("带条件的分页查询 - 调度指令库")
    @PostMapping("{pageNum}/{pageSize}")
    public APICODE findPageDispOrd(@RequestBody(required = false) DispOrdQuery dispOrdQuery, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        Page<DispOrd> page = dispOrdService.findPageDispOrd(dispOrdQuery, pageNum, pageSize);
        // 当前页的数据集合
        List<DispOrd> list = page.getContent();
        // 总条数
        long totalElements = page.getTotalElements();
        return APICODE.ok().data("total", totalElements).data("items", list);
    }

    @ApiOperation("新增 - 调度指令库")
    @PostMapping("createDispOrd")
    public APICODE createDispOrd(@RequestBody DispOrd dispOrd) {
        dispOrdService.saveOrUpdate(dispOrd);
        return APICODE.ok();
    }

    @ApiOperation("根据调度指令库ID获取单条数据")
    @GetMapping("{dispOrdId}")
    public APICODE getDispOrdById(@PathVariable("dispOrdId") String dispOrdId) {
        DispOrd dispOrd = dispOrdService.getById(dispOrdId);
        return APICODE.ok().data("dispOrd", dispOrd);
    }

    @ApiOperation("修改 - 调度指令库")
    @PutMapping
    public APICODE updatedispOrd(@RequestBody DispOrd dispOrd) {
        dispOrdService.saveOrUpdate(dispOrd);
        return APICODE.ok();
    }

    @ApiOperation("删除 - 调度指令库")
    @DeleteMapping("{dispOrdId}")
    public APICODE deletedispOrdId(@PathVariable("dispOrdId") String dispOrdId) {
        dispOrdService.removeById(dispOrdId);
        return APICODE.ok();
    }

    @ApiOperation(value = "导出Excel", notes = "使用Alibaba的EasyExcel进行数据的导出")
    @GetMapping("exportEasyExcel")
    public void exportEasyExcel(HttpServletResponse response) {
        // 查询数据
        List<DispOrd> list = dispOrdService.findAll();

        // 创建导出的集合数据
        List<DispOrdData> exportList = new ArrayList<>();
        // 循环取出一行一行的数据
        for (DispOrd dispOrd : list) {
            // 创建一个空白数据对象
            DispOrdData dispOrdData = new DispOrdData();
            // 数据复制操作
            BeanUtils.copyProperties(dispOrd, dispOrdData);
            // 放置到集合当中
            exportList.add(dispOrdData);
        }

        // 文件名
        String fileName = "xxoo";
        try {
            // ## 设置响应信息
            response.reset();
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xlsx");
            // 导出
            EasyExcel.write(response.getOutputStream(), DispOrdData.class).sheet("指令库列表").doWrite(exportList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "导入Excel", notes = "使用Alibaba的EasyExcel进行数据的导入")
    @PostMapping("importEasyExcel")
    public void importEasyExcel(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DispOrdData.class, new ExcelListener(dispOrdService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "导出Excel", notes = "使用Apache的POI进行数据的导出")
    @GetMapping("exportExcel")
    public void exportExcel(HttpServletResponse response) {
        dispOrdService.exportExcel(response);
    }

    @ApiOperation(value = "导入Excel", notes = "使用Apache的POI进行数据的导入")
    @PostMapping("importExcel")
    public void importExcel(MultipartFile file) {
        dispOrdService.importExcel(file);
    }

}
