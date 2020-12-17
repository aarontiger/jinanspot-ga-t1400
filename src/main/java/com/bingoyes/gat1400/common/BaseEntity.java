package com.bingoyes.gat1400.common;



import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;



    private String create_by;

    // @TableField(fill = FieldFill.INSERT)

    private Date create_time;

    // @TableField(fill = FieldFill.INSERT_UPDATE)

    private String update_by;

    // @TableField(fill = FieldFill.INSERT_UPDATE)

    private Date update_time;
}
