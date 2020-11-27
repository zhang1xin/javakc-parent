package com.javakc.pms.dispord.service;

import com.javakc.commonutils.jpa.base.service.BaseService;
import com.javakc.commonutils.jpa.dynamic.SimpleSpecificationBuilder;
import com.javakc.pms.dispord.dao.DispOrdDao;
import com.javakc.pms.dispord.entity.DispOrd;
import com.javakc.pms.dispord.vo.DispOrdQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: javakc-parent
 * @description:
 * @author: zhang yi xin
 * @create: 2020/11/23 19:31
 */
@Service
public class DispOrdService extends BaseService<DispOrdDao, DispOrd> {
    @Autowired
    private DispOrdDao dispOrdDao;

    public List<DispOrd> findAll() {
        List<DispOrd> list = dispOrdDao.findAll();
        return list;
    }

    /**
     * 带条件的分页查询 - 调度指令库
     *
     * @param dispOrdQuery
     * @param pageNum
     * @param pageSize
     * @return page
     */
    public Page<DispOrd> findPageDispOrd(DispOrdQuery dispOrdQuery, int pageNum, int pageSize) {
        SimpleSpecificationBuilder<DispOrd> simpleSpecificationBuilder = new SimpleSpecificationBuilder();
        if (!StringUtils.isEmpty(dispOrdQuery.getOrderName())) {
            /**
             * 可用操作符
             * = 等值、!= 不等值 (字符串、数字)
             * >=、<=、>、< (数字)
             * ge，le，gt，lt(字符串)
             * :表示like %v%
             * l:表示 v%
             * :l表示 %v
             * null表示 is null
             * !null表示 is not null
             */
            simpleSpecificationBuilder.and("orderName", ":", dispOrdQuery.getOrderName());
        }
        // 创建时间 - 区间查询
        if (!StringUtils.isEmpty(dispOrdQuery.getBeginDate())) {
            simpleSpecificationBuilder.and("gmtCreate", "ge", dispOrdQuery.getBeginDate());
        }
        if (!StringUtils.isEmpty(dispOrdQuery.getEndDate())) {
            simpleSpecificationBuilder.and("gmtCreate", "lt", dispOrdQuery.getEndDate());
        }
        Page page = dao.findAll(simpleSpecificationBuilder.getSpecification(), PageRequest.of(pageNum - 1, pageSize));
        return page;
    }
}
