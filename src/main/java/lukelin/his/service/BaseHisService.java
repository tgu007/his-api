package lukelin.his.service;

import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.Query;
import lukelin.common.springboot.service.BaseService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BaseHisService extends BaseService {
    protected Integer filterPageNum(Integer pageNum) {
        return pageNum != null && pageNum >= 1 ? pageNum : 1;
    }

    protected <T> PagedList<T> findPagedList(ExpressionList<T> expressionList, int pageNum, int pageSize) {
        if (pageSize == 0)
            return findPagedList(expressionList, pageNum);
        else
            return expressionList.setFirstRow(pageSize * (this.filterPageNum(pageNum) - 1)).setMaxRows(pageSize).findPagedList();
    }

    protected <T> PagedList<T> findPagedList(Query<T> query, int pageNum, int pageSize) {
        if (pageSize == 0)
            return findPagedList(query, pageNum);
        else
            return query.setFirstRow(pageSize * (this.filterPageNum(pageNum) - 1)).setMaxRows(pageSize).findPagedList();
    }

    protected Date addDays(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, days); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime(); //这个时间就是日期往后推一天的结果
    }
}
