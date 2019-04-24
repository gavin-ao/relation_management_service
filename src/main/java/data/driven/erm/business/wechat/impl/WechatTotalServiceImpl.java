package data.driven.erm.business.wechat.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.wechat.WechatTotalService;
import data.driven.erm.common.Constant;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.util.DateFormatUtil;
import data.driven.erm.vo.wechat.WechatTotalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static data.driven.erm.util.JSONUtil.putMsg;

/**
 * 微信统计service
 * @author hejinkai
 * @date 2018/7/3
 */
@Service
public class WechatTotalServiceImpl implements WechatTotalService {

    /** 72小时的时间戳 **/
    private static final long three_day_time_long = 72 * 60 * 60 * 1000;

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    /**
     * 72小时之外按照天统计，72小时之内按照小时统计
     * @param start
     * @param end
     * @return
     */
    private String getMysqlDateFormat(Date start, Date end){
        long tempTime = end.getTime() - start.getTime();
        if(tempTime > three_day_time_long){
            return Constant.mysql_day_format;
        }else{
            return Constant.mysql_hour_format;
        }
    }

    /**
     * 根据grouptime分组统计，统计之后返回有序的数据
     * @param list
     */
    private void coventWechatTotalList(List<WechatTotalVO> list) {
        //按照grouptime分组统计
        Map<String, Long> map = list.stream().collect(Collectors.groupingBy(WechatTotalVO::getGroupTime, Collectors.summingLong(WechatTotalVO::getCountNum)));
        TreeMap<String, Long> treeMap = new TreeMap<String, Long>(map);
        List<String> keyList = new ArrayList<String>(treeMap.keySet());
        //为前台展示根据grouptime做排序
        Collections.sort(keyList);
        list.clear();
        for(String key : keyList){
            list.add(new WechatTotalVO(treeMap.get(key), key));
        }
    }

    /**
     * 活跃度
     * @param start
     * @param end
     * @return
     */
    private Integer totalActivityNum(Date start, Date end) {
        String sql = "select count(log_id) from wechat_login_log where login_at between ? and ?";
        return jdbcBaseDao.getCount(sql, start, end);
    }

    @Override
    public JSONObject coreData(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            result.put("activityNum", 0);
            result.put("spreadRangeNum", 0);
            result.put("fissionEffectNewPeopleNum", 0);
            result.put("sharePeopleNum", 0);
            result.put("success", false);
            return result;
        }
        //活跃度
        Integer activityNum = totalActivityNum(start, end);
        result.put("activityNum", activityNum);
        //活跃用户
        Integer spreadRangeNum = totalSpreadRangeNum(start, end);
        result.put("spreadRangeNum", spreadRangeNum);
        //新增人数
        Integer fissionEffectNewPeopleNum = totalFissionEffectNewPeopleNum(start, end);
        result.put("fissionEffectNewPeopleNum", fissionEffectNewPeopleNum);
        //分享人数
        Integer sharePeopleNum = totalSharePeopleNum(start, end);
        result.put("sharePeopleNum", sharePeopleNum);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalActivityNumView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String format = getMysqlDateFormat(start, end);
        String sql = "select count(log_id) as count_num,DATE_FORMAT(login_at,'" + format + "') as group_time from wechat_login_log where login_at between ? and ? group by DATE_FORMAT(login_at,'" + format + "')";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    /**
     * 统计用户数量
     * @param start
     * @param end
     * @return
     */
    private Integer totalSpreadRangeNum(Date start, Date end) {
        String sql = "select count(DISTINCT wechat_user_id) from wechat_login_log where login_at between ? and ?";
        return jdbcBaseDao.getCount(sql, start, end);
    }

    @Override
    public JSONObject totalSpreadRangeNumView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }

        String format = getMysqlDateFormat(start, end);
        String sql = "select 1 as count_num,DATE_FORMAT(login_at,'" + format + "') as group_time from wechat_login_log where login_at between ? and ? group by wechat_user_id,group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);

        coventWechatTotalList(list);

        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalFissionEffectNewPeopleNumView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }

        String format = getMysqlDateFormat(start, end);
        String sql = "select count(waum.wechat_map_id) as count_num,DATE_FORMAT(waum.create_at,'" + format + "') as group_time from wechat_app_user_mapping waum where waum.create_at between ? and ? group by group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    /**
     * 新增人数
     * @param start
     * @param end
     * @return
     */
    private Integer totalFissionEffectNewPeopleNum(Date start, Date end) {
        //统计助力数据
        String sql = "select count(waum.wechat_map_id) from wechat_app_user_mapping waum where waum.create_at between ? and ?";
        Integer countNum = jdbcBaseDao.getCount(sql, start, end);
        if(countNum == null){
            countNum = 0;
        }
        return countNum;
    }

    /**
     * 分享人数
     * @param start
     * @param end
     * @return
     */
    private Integer totalSharePeopleNum(Date start, Date end) {
        String sql = "select count(distinct wechat_user_id) from sys_user_invitation_info where create_at between ? and ?";
        Integer countNum = jdbcBaseDao.getCount(sql,  start, end);
        if(countNum == null){
            countNum = 0;
        }
        return countNum;
    }

    @Override
    public JSONObject totalSharePeopleNumView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        //利用union的特性，相同的只取一个，保证分享和助力数据统计人数时不重复
        String format = getMysqlDateFormat(start, end);
        String sql = "select 1 as count_num,DATE_FORMAT(create_at,'" + format + "') as group_time,wechat_user_id from sys_user_invitation_info where create_at between ? and ? group by wechat_user_id,group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql,  start, end);
        coventWechatTotalList(list);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalOldAndNewUser(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        //查询开始时间之前的用户，即为老用户
        String oldSql = "select count(distinct wechat_user_id) from wechat_login_log where login_at < ?";
        Integer oldUserNum = jdbcBaseDao.getCount(oldSql,  start);
        //查询开始时间之后，结束之前的用户，即为新用户
        String newUserSql = "select count(distinct wechat_user_id) from wechat_login_log where login_at between ? and ?";
        Integer newUserNum = jdbcBaseDao.getCount(newUserSql,  start, end);

        result.put("success", true);
        result.put("oldUserNum", oldUserNum);
        result.put("newUserNum", newUserNum);
        return result;
    }

    @Override
    public JSONObject totalFunnelView(String startDate, String endDate) {
        JSONObject result = putMsg(true, "200", "调用成功");
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }

        List<JSONObject> dataList = new ArrayList<JSONObject>();
        Integer spreadRangeNum = totalSpreadRangeNum(start, end);
        Integer sharePeopleNum = totalSharePeopleNum(start, end);
        Integer fissionEffectNewPeopleNum = totalFissionEffectNewPeopleNum(start, end);
        Integer buyNum = totalBuyNum(start, end);
        dealFunnel("访问人数", spreadRangeNum, dataList);
        dealFunnel("分享人数", sharePeopleNum, dataList);
        dealFunnel("新注册用户", fissionEffectNewPeopleNum, dataList);
        dealFunnel("购买人数", buyNum, dataList);
        result.put("data", dataList);
        return result;
    }

    /**
     * 统计漏斗图设置name和value
     * @param name
     * @param countNum
     * @param dataList
     */
    private void dealFunnel(String name, Integer countNum, List<JSONObject> dataList){
        JSONObject temp = new JSONObject();
        temp.put("name", name);
        temp.put("value", countNum);
        dataList.add(temp);
    }

    @Override
    public JSONObject bossCoreData(String startDate, String endDate){
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            result.put("activityNum", 0);
            result.put("fissionEffectNewPeopleNum", 0);
            result.put("orderNum", 0);
            result.put("turnover", 0);
            result.put("success", false);
            return result;
        }
        //活跃度
        Integer activityNum = totalActivityNum(start, end);
        result.put("activityNum", activityNum);
        //新增人数
        Integer fissionEffectNewPeopleNum = totalFissionEffectNewPeopleNum(start, end);
        result.put("fissionEffectNewPeopleNum", fissionEffectNewPeopleNum);
        //订单量
        Integer orderNum = totalBuyNum(start, end);
        result.put("orderNum", orderNum);
        //成交额
        BigDecimal turnover = totalTurnover(start, end);
        result.put("turnover", turnover);
        result.put("success", true);
        return result;
    }

    /**
     * 订单量
     * @param start
     * @param end
     * @return
     */
    private Integer totalBuyNum(Date start, Date end) {
        String sql = "select count(DISTINCT wechat_user_id) from order_info where state = 1 or state = 2 and create_at between ? and ?";
        Integer countNum = jdbcBaseDao.getCount(sql,  start, end);
        if(countNum == null){
            countNum = 0;
        }
        return countNum;
    }

    @Override
    public JSONObject totalInviteRankView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(waum.wechat_map_id) as count_num,u.nick_name as nickName from wechat_app_user_mapping waum left join wechat_user_info u on u.wechat_user_id = waum.inviter where waum.create_at between ? and ? group by waum.inviter order by count_num desc limit 10";
        List<Map<String, Object>> list = jdbcBaseDao.queryMapList(sql, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalUserRetainView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        //查询留存率 - 为0则是当天注册的且登录的,（这是特殊情况-注册场景必然会登录且记录到日志中）
        String sql = "SELECT count(DISTINCT u.wechat_user_id) as count_num, (CASE" +
                " TIMESTAMPDIFF(DAY, DATE_FORMAT(u.create_at, '%Y-%m-%d'), DATE_FORMAT(wll.login_at, '%Y-%m-%d'))" +
                " WHEN 0 THEN 'nowGroup' WHEN 1 THEN 'nextGroup' WHEN 7 THEN 'sevenGroup' WHEN 30 THEN 'thirtyGroup' ELSE 'otherGroup' END) as retention_group,DATE_FORMAT(u.create_at, '%Y年%m月%d日') group_time FROM wechat_user_info u" +
                " LEFT JOIN wechat_login_log wll ON u.wechat_user_id = wll.wechat_user_id" +
                " where u.create_at between ? and ? and TIMESTAMPDIFF(DAY, DATE_FORMAT(u.create_at, '%Y-%m-%d'), DATE_FORMAT(wll.login_at, '%Y-%m-%d')) in (0, 1, 7, 30) GROUP BY retention_group,group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        dealUserRetainJson(result, list);
        result.put("success", true);
        return result;
    }

    /**
     * 计算留存率并添加
     * @param result
     * @param list
     */
    private void dealUserRetainJson(JSONObject result, List<WechatTotalVO> list){
        List<JSONObject> dataList = new ArrayList<JSONObject>();
        if(list != null && list.size() > 0){
            Map<String, List<WechatTotalVO>> groupMap = list.stream().collect(Collectors.groupingBy(o -> o.getGroupTime()));
            for(Map.Entry<String, List<WechatTotalVO>> entry : groupMap.entrySet()){
                JSONObject data = new JSONObject();
                WechatTotalVO nowGroup = entry.getValue().stream().filter(o -> "nowGroup".equals(o.getRetentionGroup())).findFirst().get();
                Map<String, BigDecimal> totalMap = entry.getValue().stream().filter(o -> !"nowGroup".equals(o.getRetentionGroup())).collect(Collectors.toMap(o -> o.getRetentionGroup(), o -> {
                    BigDecimal bigDecimal = new BigDecimal(o.getCountNum());
                    return bigDecimal.divide(new BigDecimal(nowGroup.getCountNum())).setScale(2);
                }));
                data.put("groupTime", entry.getKey());
                data.put("nowGroup", nowGroup.getCountNum());
                data.putAll(totalMap);
                dataList.add(data);
            }
            Collections.sort(dataList, (o1, o2) -> o1.getString("groupTime").compareTo(o2.getString("groupTime")));
        }
        result.put("data", dataList);
    }

    /**
     * 成交额
     * @param start
     * @param end
     * @return
     */
    private BigDecimal totalTurnover(Date start, Date end) {
        String sql = "select sum(odi.real_payment) from order_detail_info odi left join order_info o on o.order_id = odi.order_id where o.state = 1 or o.state = 2 and o.create_at between ? and ?";
        Object countNum = jdbcBaseDao.getColumn(sql,  start, end);
        if(countNum != null){
            return new BigDecimal(countNum.toString());
        }
        return new BigDecimal(0);
    }

    @Override
    public JSONObject totalAverageUnitPrice(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String format = getMysqlDateFormat(start, end);
        //获取销售额
        String sql = "select sum(odi.real_payment)/count(distinct o.order_id) as average_num,DATE_FORMAT(o.create_at,'" + format + "') as group_time from order_detail_info odi left join order_info o on o.order_id = odi.order_id where o.state = 1 or o.state = 2 and o.create_at between ? and ? group by group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalSalableCatg(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(distinct o.order_id) as count_num,cci.catg_name as group_time from order_detail_info odi" +
                " left join order_info o on o.order_id = odi.order_id" +
                " left join commodity_info ci on ci.commodity_id = odi.commodity_id" +
                " left join commodity_catg_info cci on cci.catg_id = ci.catg_id" +
                " where o.state = 1 or o.state = 2 and o.create_at between ? and ? and cci.catg_name is not null group by group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalAreaDistribution(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(distinct o.order_id) as count_num,haddr.province as group_time from order_detail_info odi" +
                " left join order_info o on o.order_id = odi.order_id" +
                " left join order_history_receive_addr haddr on haddr.order_id = o.order_id" +
                " where o.state = 1 or o.state = 2 and o.create_at between ? and ? group by group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalRebateBanking(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select sum(ori.rebate_money) as average_num,u.nick_name as group_time from order_rebate_info ori" +
                " left join wechat_user_info u on u.wechat_user_id = ori.wechat_user_id" +
                " where ori.rebate_at between ? and ? group by group_time order by average_num desc limit 10";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject saleCoreData(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            result.put("turnover", 0);
            result.put("arpuNum", 0);
            result.put("cacNum", 0);
            result.put("averageRebateNum", 0);
            result.put("success", false);
            return result;
        }
        //成交额
        BigDecimal turnover = totalTurnover(start, end);
        result.put("turnover", turnover);
        //ARPU(每用户平均收入)
        BigDecimal arpuNum = totalARPU(start, end);
        result.put("arpuNum", arpuNum);
        //CAC(获客成本)
        BigDecimal cacNum = totalCAC(start, end);
        result.put("cacNum", cacNum);
        //平均返点
        BigDecimal averageRebateNum = totalAverageRebate(start, end);
        result.put("averageRebateNum", averageRebateNum);

        result.put("success", true);
        return result;
    }

    /**
     * ARPU(每用户平均收入)    ARPU=销售额/总用户数（同一统计周期内）
     * @param start
     * @param end
     * @return
     */
    private BigDecimal totalARPU(Date start, Date end) {
        //销售额
        BigDecimal turnover = this.totalTurnover(start, end);
        BigDecimal arpu = null;
        if(turnover.doubleValue() > 0){
            //总用户数
            Integer sharePeopleNum = this.totalSharePeopleNum(start, end);
            if(sharePeopleNum != null && sharePeopleNum > 0){
                BigDecimal second = new BigDecimal(sharePeopleNum);
                arpu = turnover.divide(second).setScale(2);
            }
        }
        if(arpu == null){
            return new BigDecimal(0);
        }
        return arpu;
    }

    /**
     * CAC(获客成本) CAC=返利金额/新用户数（同一统计周期内）
     * @param start
     * @param end
     * @return
     */
    private BigDecimal totalCAC(Date start, Date end) {
        BigDecimal rebateMoney = totalRebate(start, end);
        if(rebateMoney.doubleValue() > 0){
            Integer fissionEffectNewPeopleNum = totalFissionEffectNewPeopleNum(start, end);
            if(fissionEffectNewPeopleNum > 0){
                return rebateMoney.divide(new BigDecimal(fissionEffectNewPeopleNum)).setScale(2);
            }
        }
        return new BigDecimal(0);
    }

    /**
     * 获取返利金额
     * @param start
     * @param end
     * @return
     */
    private BigDecimal totalRebate(Date start, Date end) {
        //返利金额
        String sql = "select sum(ori.rebate_money) as rebate_money from order_rebate_info ori where ori.rebate_at between ? and ?";
        Object obj = jdbcBaseDao.getColumn(sql, start, end);
        if(obj != null){
            return new BigDecimal(obj.toString());
        }
        return new BigDecimal(0);
    }

    /**
     * 平均返点，平均返点=销售额/返利*100%（同一统计周期内）
     * @param start
     * @param end
     * @return
     */
    private BigDecimal totalAverageRebate(Date start, Date end) {
        BigDecimal turnover = totalTurnover(start, end);
        if(turnover.doubleValue() > 0){
            BigDecimal rebateMoney = totalRebate(start, end);
            if(rebateMoney.doubleValue() > 0){
                    return turnover.divide(rebateMoney).multiply(new BigDecimal(100)).setScale(2);
            }
        }
        return new BigDecimal(0);
    }

    @Override
    public JSONObject totalSalableCommodity(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(distinct o.order_id) as count_num,ci.commodity_name as group_time,cci.catg_name as catg_group from order_detail_info odi" +
                " left join order_info o on o.order_id = odi.order_id" +
                " left join commodity_info ci on ci.commodity_id = odi.commodity_id" +
                " left join commodity_catg_info cci on cci.catg_id = ci.catg_id" +
                " where o.state = 1 or o.state = 2 and o.create_at between ? and ? and ci.commodity_name is not null and cci.catg_name is not null group by ci.commodity_name,group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        if(list != null && list.size() > 0){
            Map<String, List<WechatTotalVO>> groupMap = list.stream().collect(Collectors.groupingBy(o -> o.getCatgGroup()));
            List<JSONObject> dataList = new ArrayList<JSONObject>();
            for (Map.Entry<String, List<WechatTotalVO>> entry : groupMap.entrySet()){
                List<JSONObject> childList = new ArrayList<JSONObject>();
                JSONObject data = new JSONObject();
//                data.put("name" : )

            }
        }

        result.put("data", list);
        result.put("success", true);
        return result;
    }
}
