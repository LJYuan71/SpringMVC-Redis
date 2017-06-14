package tk.ljyuan71.common.bean;

import java.util.List;

/**
 * Created by LJYuan71 on 2017-6-13.
 */
public class ResultPager<T> {

    private Integer total;

    private List<T> rows;

    public ResultPager() {

    }

    public ResultPager(Integer total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
