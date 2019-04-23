package data.driven.erm.business.wechat.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.wechat.WechatTotalService;
import data.driven.erm.common.Constant;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.util.DateFormatUtil;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.vo.wechat.WechatTotalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 微信统计service
 * @author hejinkai
 * @date 2018/7/3
 */
@Service
public class WechatTotalServiceImpl implements WechatTotalService {

    /** 72小时的时间戳 **/
    private static final long three_day_time_long = 72 * 60 * 60 * 1000;


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

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public JSONObject totalActivityNum(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(log_id) from wechat_login_log where login_at between ? and ?";
        Integer countNum = jdbcBaseDao.getCount(sql, start, end);
        result.put("success", true);
        result.put("countNum", countNum);
        return result;
    }

    @Override
    public JSONObject totalActivityNumView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String format = getMysqlDateFormat(start, end);
        String sql = "select count(log_id) as count_num,DATE_FORMAT(login_at,'" + format + "') as group_time from wechat_login_log where login_at between ? and ? group by DATE_FORMAT(login_at,'" + format + "')";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalSpreadRangeNum(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(DISTINCT wechat_user_id) from wechat_login_log where login_at between ? and ?";
        Integer countNum = jdbcBaseDao.getCount(sql, start, end);
        result.put("success", true);
        result.put("countNum", countNum);
        return result;
    }

    @Override
    public JSONObject totalSpreadRangeNumView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
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
    public JSONObject totalFissionEffectNewPeopleNum(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        //统计助力数据
        String sql = "select count(waum.wechat_map_id) from wechat_app_user_mapping waum where waum.create_at between ? and ?";
        Integer countNum = jdbcBaseDao.getCount(sql, start, end);
        if(countNum == null){
            countNum = 0;
        }
        result.put("success", true);
        result.put("countNum", countNum);
        return result;
    }

    @Override
    public JSONObject totalFissionEffectNewPeopleNumView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }

        String format = getMysqlDateFormat(start, end);
        String sql = "select count(waum.wechat_map_id) as count_num,DATE_FORMAT(waum.create_at,'" + format + "') as group_time from wechat_app_user_mapping waum where waum.create_at between ? and ? group by group_time";
        List<WechatTotalVO> list = jdbcBaseDao.queryList(WechatTotalVO.class, sql,  start, end, start, end);
        result.put("data", list);
        result.put("success", true);
        return result;
    }

    @Override
    public JSONObject totalSharePeopleNum(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(invitation_id) from sys_user_invitation_info where create_at between ? and ? group by wechat_user_id";
        Integer countNum = jdbcBaseDao.getCount(sql,  start, end);
        if(countNum == null){
            countNum = 0;
        }

        result.put("success", true);
        result.put("countNum", countNum);
        return result;
    }

    @Override
    public JSONObject totalSharePeopleNumView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
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
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
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
        JSONObject result = JSONUtil.putMsg(true, "200", "调用成功");
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }

        List<JSONObject> dataList = new ArrayList<JSONObject>();
        JSONObject spreadRangeNumJson = totalSpreadRangeNum(startDate, endDate);
        JSONObject sharePeopleNumJson = totalSharePeopleNum(startDate, endDate);
        JSONObject fissionEffectNewPeopleNumJson = totalFissionEffectNewPeopleNum(startDate, endDate);
        JSONObject buyNumJson = totalBuyNum(startDate, endDate);
        dealFunnelJson("访问人数", spreadRangeNumJson, dataList);
        dealFunnelJson("分享人数", sharePeopleNumJson, dataList);
        dealFunnelJson("新注册用户", fissionEffectNewPeopleNumJson, dataList);
        dealFunnelJson("购买人数", buyNumJson, dataList);
        result.put("data", dataList);
        return result;
    }

    /**
     * 统计漏斗图设置name和value
     * @param name
     * @param result
     * @param dataList
     */
    private void dealFunnelJson(String name, JSONObject result, List<JSONObject> dataList){
        Integer value = 0;
        if(result.getBoolean("success")){
            value = result.getInteger("countNum");
        }
        JSONObject temp = new JSONObject();
        temp.put("name", name);
        temp.put("value", value);
        dataList.add(temp);
    }

    /**
     * 购买人数
     * @param startDate
     * @param endDate
     * @return
     */
    private JSONObject totalBuyNum(String startDate, String endDate){
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(DISTINCT wechat_user_id) from order_info where state = 1 or state = 2 create_at between ? and ?";
        Integer countNum = jdbcBaseDao.getCount(sql,  start, end);
        if(countNum == null){
            countNum = 0;
        }

        result.put("success", true);
        result.put("countNum", countNum);
        return result;
    }

    @Override
    public JSONObject totalInviteRankView(String startDate, String endDate) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String sql = "select count(waum.wechat_map_id) as countNum,u.nick_name as nickName from wechat_app_user_mapping waum left join wechat_user_info u on u.wechat_user_id = waum.inviter where waum.create_at between ? and ? group by waum.inviter limit 10";
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
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
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
}
