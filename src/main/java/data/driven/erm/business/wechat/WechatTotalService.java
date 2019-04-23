package data.driven.erm.business.wechat;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信统计servcie
 * @author hejinkai
 * @date 2018/7/3
 */
public interface WechatTotalService {

    /**
     * 根据时间范围统计活跃度
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalActivityNum(String startDate, String endDate);

    /**
     * 根据时间范围统计活跃度，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalActivityNumView(String startDate, String endDate);

    /**
     * 根据时间范围统计传播范围
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalSpreadRangeNum(String startDate, String endDate);
    /**
     * 根据时间范围统计传播范围，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalSpreadRangeNumView(String startDate, String endDate);

    /**
     * 根据时间范围统计裂变效果新增人数
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalFissionEffectNewPeopleNum(String startDate, String endDate);

    /**
     * 根据时间范围统计裂变效果新增人数，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalFissionEffectNewPeopleNumView(String startDate, String endDate);

    /**
     * 根据时间范围统计分享次数
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalSharePeopleNum(String startDate, String endDate);

    /**
     * 根据时间范围统计分享人数，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalSharePeopleNumView(String startDate, String endDate);

    /**
     * 根据时间范围统计新老用户占比
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalOldAndNewUser(String startDate, String endDate);

    /**
     * 漏斗图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalFunnelView(String startDate, String endDate);

    /**
     * 用户邀请排行 - 取前十个
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalInviteRankView(String startDate, String endDate);

    /**
     * 用户邀请排行 - 取前十个
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalUserRetainView(String startDate, String endDate);


}
