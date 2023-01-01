package com.stone.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
@Getter
@Setter
@ToString
public class PageResult<T> {
/**
* 当前页码
*/
private Long    current;
/**
* 每页显示的条数
*/
private Long    size;
/**
* 总条数
*/
// private Long    totalCount;
private Long    total;
/**
* 当前页结果集数据
*/
// private List<T> list;
private List<T> records;
/**
* 上一页页码
*/
private Long    prevPage;
/**
* 下一页页码
*/
private Long    nextPage;
/**
* 总页数/末页页码
*/
private Long    totalPage;

public PageResult(Page<T> page) {
this.current = page.getCurrent();
this.size    = page.getSize();
// this.totalCount = page.getTotal();
this.total = page.getTotal();
// this.list       = page.getRecords();
this.records   = page.getRecords();
this.prevPage  = page.hasPrevious() ? page.getCurrent() - 1 : page.getCurrent();
this.nextPage  = page.hasNext() ? page.getCurrent() + 1 : page.getCurrent();
this.totalPage = page.getPages();
}
}
