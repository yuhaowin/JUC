package com.yuhaowin.test;


/**
 * 概述：ETL业务类型，包含获取对应类型的数据源、delivery topic、类型标识、表名
 *
 * @author maoyuanming
 * @version v5.9.2
 * @date 2019-07-23
 */
public enum EtlBizTypeEnum {

    /**
     * 网络请求性能
     */
    PER_NET(1, "T_SDK_STAT_DRU_NETPERF"),
    PER_WEB_VIEW(2, "T_SDK_STAT_DRU_H5"),
    PER_CDN(3, "T_SDK_STAT_DRU_CDN"),
    PER_FLOW(4, "T_SDK_STAT_DRU_FLOW"),
    PER_VIISIT(5, "T_SDK_STAT_DRU_VISIT"),
    ERROR(6, "T_SDK_STAT_DRU_ERROR"),
    ERROR_SNAPSHOT(7, "T_SDK_STAT_ERROR_SNAPSHOT"),
    JSERROR(8, "T_SDK_STAT_DRU_JSERROR"),
    JSERROR_SNAPSHOT(9, "T_SDK_STAT_JSERROR_SNAPSHOT"),
    ERROR_CDN_SNAPSHOT(10, "T_SDK_STAT_ERROR_CDN_SNAPSHOT"),
    PER_NET_SNAPSHOT(11, "T_SDK_STAT_NETPERF_SNAPSHOT"),
    INTERACT(12, "T_SDK_STAT_DRU_INTERACT"),
    PER_KEY_ELEMENT(13, "T_SDK_STAT_DRU_KEYELEMENT"),
    ANR(14, "T_SDK_STAT_DRU_ANR"),
    LAG(15, "T_SDK_STAT_DRU_LAG"),
    LAG_SNAPSHOT(16, "T_SDK_STAT_LAG_SNAPSHOT"),
    CRASH(17, "T_SDK_STAT_DRU_CRASH"),
    CRASH_SNAPSHOT(18, "T_SDK_STAT_CRASH_SNAPSHOT"),
    PER_VIEW(19, "T_SDK_STAT_DRU_VIEW"),
    PER_KEY_SNAPSHOT(20, "T_SDK_STAT_KEY_SNAPSHOT"),
    PER_KEY_ERROR_SNAPSHOT(21, "T_SDK_STAT_KEY_ERROR_SNAPSHOT"),
    INTERACT_SNAPSHOT(22, "T_SDK_STAT_INTERACT_SNAPSHOT"),
    PER_OVERVIEW(23, "T_SDK_STAT_DRU_OVERVIEW"),
    SELF_CRASH_SNAPSHOT(24, "T_SDK_STAT_SELF_CRASH_SNAPSHOT"),
    ANR_SNAPSHOT(25, "T_SDK_STAT_ANR_SNAPSHOT"),
    H5_SLOW_SNAPSHOT(28, "T_SDK_STAT_H5_SNAPSHOT"),
    PER_DEVICE(29, "T_SDK_STAT_DRU_DEVICE"),
    SPEED(31, "T_SDK_STAT_DRU_SPEED"),
    ACTIVE(32, "T_SDK_STAT_DRU_ACTIVE"),
    LOG_SNAPSHOT(34, "T_SDK_TRACK_LOG"),
    SDKINFO(35, "T_SDK_STAT_DRU_SDKINFO"),
    CLIENT(36, "T_SDK_CONFIG_DRU_CLIENT"),
    ACCOUNT_SNAPSHOT(38, "T_SDK_STAT_ACCOUNT_SNAPSHOT"),
    ACTIVE_SNAPSHOT(42, "T_SDK_STAT_ACTIVE_SNAPSHOT"),
    VIEW_SNAPSHOT(43, "T_SDK_STAT_VIEW_SNAPSHOT"),

    // 操作
    OPERATION_EVENT(201, "T_SDK_STAT_OPERATION_SNAPSHOT"),

    /*
     * 行为直接从100开始
     */
    ACTION_USER_LOSTANDEXISTS(101, "T_ZEUS_SDK_ACT_USER_LOSTANDEXISTS"),
    ACTION_PROCESS(102, "T_ZEUS_SDK_ACT_PROCESS"),
    ACTION_USER_USAGE(103, "T_ZEUS_SDK_ACT_USER_USAGE"),
    ACTION_DIMENSION(104, "T_ZEUS_SDK_ACT_STAT_BAS"),
    ACTION_SOURCE_DEVICEINFO(105, "T_ZEUS_SDK_ACT_DEVICEINFO"),
    ACTION_EVENT(106, "T_ZEUS_SDK_ACT_EVENT"),
    ACTION_PAGE(107, "T_ZEUS_SDK_ACT_PAGE"),
    ;

    private final int number;
    private final String tableName;

    EtlBizTypeEnum(int number, String tableName) {
        this.number = number;
        this.tableName = tableName;
    }


    public final int getNumber() {
        return number;
    }

    public String getTableName() {
        return tableName;
    }

    public static EtlBizTypeEnum numberOf(int number) {
        for (EtlBizTypeEnum value : values()) {
            if (number == value.getNumber()) {
                return value;
            }
        }
        return null;
    }


}