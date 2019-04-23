package data.driven.erm.vo.wechat;

import java.math.BigDecimal;

/**
 * 用于微信统计
 * @author hejinkai
 * @date 2018/7/5
 */
public class WechatTotalVO {
    /** 合计 **/
    private Long countNum;
    /** 平均数 **/
    private BigDecimal averageNum;
    /** 时间 **/
    private String groupTime;
    /** 留存率分组名称 **/
    private String retentionGroup;

    public WechatTotalVO(Long countNum, String groupTime) {
        this.countNum = countNum;
        this.groupTime = groupTime;
    }
    public WechatTotalVO() {
    }
    public Long getCountNum() {
        return countNum;
    }
    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }
    public String getGroupTime() {
        return groupTime;
    }
    public void setGroupTime(String groupTime) {
        this.groupTime = groupTime;
    }

    public String getRetentionGroup() {
        return retentionGroup;
    }

    public void setRetentionGroup(String retentionGroup) {
        this.retentionGroup = retentionGroup;
    }

    public BigDecimal getAverageNum() {
        return averageNum;
    }

    public void setAverageNum(BigDecimal averageNum) {
        this.averageNum = averageNum;
    }
}
