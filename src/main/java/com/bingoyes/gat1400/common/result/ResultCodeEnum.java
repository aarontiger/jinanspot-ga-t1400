package com.bingoyes.gat1400.common.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @date 2020-06-23
 **/
//@AllArgsConstructor
@NoArgsConstructor
public enum ResultCodeEnum implements  IResultCode, Serializable {



    SUCCESS("00000","操作成功"),

    CLIENT_ERROR("A0001","用户端错误"),
    CLIENT_REGISTER_ERROR("A0100","设备未注册"),
    CLIENT_REQUEST_PARAM_ERROR("A0400","用户请求参数错误"),
    CLIENT_REQUEST_PARAM_IS_BLANK("A0410","请求必填参数为空"),
    CLIENT_UPLOAD_FILE_ERROR("A0700","用户上传文件异常"),
    CLIENT_UPLOAD_FILE_TYPE_NOT_MATCH("A0701","用户上传文件类型不匹配"),
    CLIENT_UPLOAD_FILE_SIZE_EXCEEDS("A0702","用户上传文件太大"),
    CLIENT_UPLOAD_IMAGE_SIZE_EXCEEDS("A0703","用户上传图片太大"),

    SYSTEM_EXECUTION_ERROR("B0001","系统执行出错"),
    SYSTEM_EXECUTION_TIMEOUT("B0100","系统执行超时"),
    SYSTEM_ORDER_PROCESSING_TIMEOUT("B0100","系统订单处理超时"),
    SYSTEM_DISASTER_RECOVERY_TRIGGER("B0200","系统容灾功能被出发"),
    SYSTEM_LIMITING("B0210","系统限流"),
    SYSTEM_FUNCTION_DEGRADATION("B0220","系统功能降级"),
    SYSTEM_RESOURCE_ERROR("B0300","系统资源异常"),
    SYSTEM_RESOURCE_EXHAUSTION("B0310","系统资源耗尽"),
    SYSTEM_RESOURCE_ACCESS_ERROR("B0320","系统资源访问异常"),
    SYSTEM_READ_DISK_FILE_ERROR("B0321","系统读取磁盘文件失败"),


    CALL_THIRD_PARTY_SERVICE_ERROR("C0001","调用第三方服务出错"),
    MIDDLEWARE_SERVICE_ERROR("C0100","中间件服务出错"),
    INTERFACE_NOT_EXIST("C0113","接口不存在"),

    MESSAGE_SERVICE_ERROR("C0120","消息服务出错"),
    MESSAGE_DELIVERY_ERROR("C0121","消息投递出错"),
    MESSAGE_CONSUMPTION_ERROR("C0122","消息消费出错"),
    MESSAGE_SUBSCRIPTION_ERROR("C0123","消息订阅出错"),
    MESSAGE_GROUP_NOT_FOUND("C0124","消息分组未查到"),

    DATABASE_ERROR("C0300","数据库服务出错"),
    DATABASE_TABLE_NOT_EXIST("C0311","表不存在"),
    DATABASE_COLUMN_NOT_EXIST("C0312","列不存在"),
    DATABASE_DUPLICATE_COLUMN_NAME("C0321","多表关联中存在多个相同名称的列"),
    DATABASE_DEADLOCK("C0331","数据库死锁"),
    DATABASE_PRIMARY_KEY_CONFLICT("C0341","主键冲突") ;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    private ResultCodeEnum(String code,String value){
        this.code = code;
        this.msg = msg;
    }
}
