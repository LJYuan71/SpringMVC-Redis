package tk.ljyuan71.common.bean;

/**
 * Created by LJYuan71 on 2017-6-13.
 */
public class Pager {

    private int pageSize;

    private int pageNo;

    private int totalRecords;

    public Pager() {

    }

    public Pager(int pageNo, int pageSize, int totalRecords) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getBeginIndex() {
        return (pageNo - 1) * pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return getTotalRecords() % pageSize == 0 ? getTotalRecords() / pageSize : getTotalRecords() / pageSize + 1;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", totalRecords=" + totalRecords +
                '}';
    }

}
