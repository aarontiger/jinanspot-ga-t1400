package com.bingoyes.gat1400.common.result;

import lombok.Data;

/**
 *
 * @date 2020-06-24
 **/
@Data
public class PageResult<T> extends Result {

    private long total;

    public static <T> PageResult<T> success(T data, Long total) {
        PageResult<T> pageResult = new PageResult();
        pageResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        pageResult.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        pageResult.setData(data);
        pageResult.setTotal(total);
        return pageResult;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
