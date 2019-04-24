package data.driven.erm.business.wechat;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信统计servcie
 * @author hejinkai
 * @date 2018/7/3
 */
public interface WechatTotalService {

    /**
     * 统计小程序上面的四个指标
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject coreData(String startDate, String endDate);

    /**
     * 根据时间范围统计活跃度，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalActivityNumView(String startDate, String endDate);

    /**
     * 根据时间范围统计传播范围，返回数据走势图 - 时间段总用户数
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalSpreadRangeNumView(String startDate, String endDate);

    /**
     * 根据时间范围统计裂变效果新增人数，返回数据走势图
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalFissionEffectNewPeopleNumView(String startDate, String endDate);

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
     * 购买人数
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject bossCoreData(String startDate, String endDate);

    /**
     * 用户邀请排行 - 取前十个
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalInviteRankView(String startDate, String endDate);

    /**
     * 留存率
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalUserRetainView(String startDate, String endDate);

    /**
     * 平均客单价 ，平均客单价=销售额/有消费的用户数（同一统计周期内）
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalAverageUnitPrice(String startDate, String endDate);

    /**
     * 统计畅销分类
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalSalableCatg(String startDate, String endDate);

    /**
     * 订单地区分布
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalAreaDistribution(String startDate, String endDate);

    /**
     * 返利排行 - top10
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject totalRebateBanking(String startDate, String endDate);

    /**
     * ARPU(每用户平均收入)
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONObject saleCoreData(String startDate, String endDate);


}
