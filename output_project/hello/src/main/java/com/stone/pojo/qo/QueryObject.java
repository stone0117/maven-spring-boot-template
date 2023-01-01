package com.stone.pojo.qo;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
public class QueryObject {
  public static final int     CURRENT_PAGE = 1;
  public static final int     PAGE_SIZE    = 10;
  // 当前页码，要跳转到哪一页的页码(需要给默认值)
  private             Integer currentPage;
  // 每页显示条数(需要给默认值)
  private             Integer pageSize;

  public Integer getStart() {
    return (getCurrentPage() - 1) * getPageSize();
  }

  public Integer getCurrentPage() {
    return currentPage == null || currentPage == 0 ? CURRENT_PAGE : currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Integer getPageSize() {
    return pageSize == null || pageSize == 0 ? PAGE_SIZE : pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}