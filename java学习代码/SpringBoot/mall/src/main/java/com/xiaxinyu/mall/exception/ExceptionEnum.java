package com.xiaxinyu.mall.exception;

/**
 * 异常枚举类
 */
public enum ExceptionEnum {

    NEED_USER_NAME(10001,"用户名不能为空"),
    NEED_PASSWORD(10002,"密码不能为空"),
    PASSWORD_TOO_SHORT(10003,"密码长度不能小于八位"),
    NAME_EXISTED(10004,"不允许重名"),
    INSERT_FAIL(10005,"插入失败，请重试"),
    WRONG_PASSWORD(10006,"密码错误"),
    NEED_LOGIN(10007,"用户未登陆"),
    UPDATE_FAILED(10008,"更新失败"),
    NEED_ADMIN(10009,"无管理员权限"),
    PARA_NOT_NULL(10010,"参数不能为空"),
    CREATE_FAILED(10011,"新增失败"),
    REQUEST_PARAM_ERROR(10012,"参数错误"),
    DELETE_FAILED(10013,"删除失败"),
    MKDIR_FAILED(10014,"文件夹上传失败"),
    UPLOAD_FAILED(10015,"图片上传失败"),
    PRODUCT_NOT_EXISTS(10016,"商品不存在"),
    NOT_SALE(10016,"商品状态不可售"),
    NOT_ENOUGH(10017,"商品库存不足"),
    CART_EMPTY(10018,"购物车中已勾选商品为空"),
    NO_ENUM(10019,"未找到对应的枚举类"),
    NO_ORDER(10020,"订单不存在"),
    NO_YOUR_ORDER(10021,"订单不属于你"),
    WRONG_ORDER_STATUS(10022,"订单状态不符"),

    SYSTEM_ERROR(20000,"系统异常");


    private Integer code;
    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
