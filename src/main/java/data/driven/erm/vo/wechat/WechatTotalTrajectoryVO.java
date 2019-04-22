package data.driven.erm.vo.wechat;

import java.util.Date;
import java.util.List;

/**
 * 用于微信统计 - 轨迹图
 * @author hejinkai
 * @date 2018/7/18
 */
public class WechatTotalTrajectoryVO implements Comparable<WechatTotalTrajectoryVO>{
    private String totalId;
    private String actId;
    private String fromUserId;
    private String fromUser;
    private String toUserId;
    private String toUser;
    private Date totalDate;
    private Integer frequency;
    private Integer level;
    private Integer maxLevel;
    private Integer maxPeople;
    private List<WechatTotalTrajectoryVO> childList;

    @Override
    public int compareTo(WechatTotalTrajectoryVO o) {
        if(o == null){
            return 1;
        }
        if(maxLevel == null && o.getMaxLevel() == null){
            return 0;
        }else if(o.getMaxLevel() == null){
            return 1;
        }else if(maxLevel == null){
            return -1;
        }
        return maxLevel - o.getMaxLevel();
    }

    public String getTotalId() {
        return totalId;
    }

    public void setTotalId(String totalId) {
        this.totalId = totalId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public Date getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(Date totalDate) {
        this.totalDate = totalDate;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public List<WechatTotalTrajectoryVO> getChildList() {
        return childList;
    }

    public void setChildList(List<WechatTotalTrajectoryVO> childList) {
        this.childList = childList;
    }
}
