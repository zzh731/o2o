package cn.im731.o2o.util;

public class PageCalculator {
    /**
     * 给定当前页码和每页条数，计算出当前页起始行数
     * @param pageIndex 当前页码,从1开始
     * @param pageSize 每页条数
     * @return 当前页起始行数
     */
    public static int convertPageToRow(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1)*pageSize : 0;
    }
}
