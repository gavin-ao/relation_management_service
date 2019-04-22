package data.driven.erm.business.wechat.impl;

import com.alibaba.fastjson.JSONObject;
import data.driven.erm.business.wechat.WechatTotalService;
import data.driven.erm.common.Constant;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.util.DateFormatUtil;
import data.driven.erm.util.JSONUtil;
import data.driven.erm.vo.wechat.WechatTotalTrajectoryVO;
import data.driven.erm.vo.wechat.WechatTotalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String sql = "select ifnull(count(1),0) from (select wechat_user_id from wechat_login_log where login_at between ? and ? group by wechat_user_id) as t";
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
        String helpSql = "select count(waum.wechat_map_id) from wechat_app_user_mapping waum where waum.create_at between ? and ?";
        Integer countHelpNum = jdbcBaseDao.getCount(helpSql, start, end);
        if(countHelpNum == null){
            countHelpNum = 0;
        }
        result.put("success", true);
        result.put("countNum", countHelpNum);
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
        String helpSql = "select count(invitation_id) from sys_user_invitation_info where create_at between ? and ? group by wechat_user_id";
        Integer countHelpNum = jdbcBaseDao.getCount(helpSql,  start, end);
        if(countHelpNum == null){
            countHelpNum = 0;
        }

        result.put("success", true);
        result.put("countNum", countHelpNum);
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
    public JSONObject totalSpreadTrajectory(String startDate, String endDate) {
        return totalSpreadTrajectory(startDate, endDate, 1, 0);
    }

    @Override
    public JSONObject totalSpreadTrajectory(String startDate, String endDate, Integer type, Integer sortType) {
        JSONObject result = new JSONObject();
        Date start = DateFormatUtil.getTime(startDate);
        Date end = DateFormatUtil.toEndDate(endDate);
        if(start == null || end == null){
            return JSONUtil.putMsg(false, "102", "时间获取失败，请检查时间格式");
        }
        String helpSql = "select t.help_id as totalId,fu.wechat_user_id as from_user_id,fu.nick_name as from_user,tu.wechat_user_id as to_user_id,tu.nick_name as to_user,t.help_at as total_date from wechat_help_detail t" +
                " left join wechat_help_info p on t.help_id = p.help_id" +
                " left join wechat_user_info fu on t.form_wechat_user_id = fu.wechat_user_id" +
                " left join wechat_user_info tu on t.to_wechat_user_id = tu.wechat_user_id" +
                " where t.p.create_at between ? and ? and t.status = 1 and fu.wechat_user_id is not null and tu.wechat_user_id is not null order by t.help_at";
        List<WechatTotalTrajectoryVO> helpList = jdbcBaseDao.queryList(WechatTotalTrajectoryVO.class, helpSql,  start, end);
        List<WechatTotalTrajectoryVO> dataList = new ArrayList<WechatTotalTrajectoryVO>();
        if(helpList != null && helpList.size() > 0){
            dataList.addAll(helpList);
        }
        if(type == null || (type != 0 && type != 1)){
            type = 1;
        }
        if(dataList.size() > 0){
            List<WechatTotalTrajectoryVO> firstTrajectoryList = getFirstTrajectory(dataList, type);
            List<String> parentExistList = new ArrayList<String>();
            List<WechatTotalTrajectoryVO> resultList = new ArrayList<WechatTotalTrajectoryVO>();
            List<String> existList = firstTrajectoryList.stream().collect(Collectors.mapping(o -> o.getToUserId(), Collectors.toList()));
            //按照fromUserId分组
            Map<String, List<WechatTotalTrajectoryVO>> trajectoryMap = dataList.stream().collect(Collectors.groupingBy(o -> o.getFromUserId()));

            for(WechatTotalTrajectoryVO first : firstTrajectoryList){
                if(parentExistList.contains(first.getToUserId())){
                    continue;
                }
                parentExistList.add(first.getToUserId());
                if(type == 1){
                    existList.clear();
                    existList.add(first.getToUserId());
                }
                dealLevelTrajectory(first, trajectoryMap, first, 1, -1, existList, type);
                resultList.add(first);
            }
            if(sortType != null && sortType.intValue() == 1){
                //去除子集
                resultList.stream().forEach(o -> o.setChildList(null));
                Collections.sort(resultList, (o1, o2) -> {
                    if(o2 == null){
                        return 1;
                    }
                    if(o1.getMaxPeople() == null && o2.getMaxPeople() == null){
                        return 0;
                    }else if(o2.getMaxPeople() == null){
                        return 1;
                    }else if(o1.getMaxPeople() == null){
                        return -1;
                    }
                    return o1.getMaxPeople() - o2.getMaxPeople();
                });

            }else{
                Collections.sort(resultList);
            }
            Collections.reverse(resultList);
            result.put("data", resultList);
        }
        result.put("success", true);
        return result;
    }

    /**
     * 判断是否是第一个根节点，判断依据，往前推没有人是我的from，并且之前也没统计过from为我的，就认定为第一个根节点。
     * @param result
     * @return
     */
    private List<WechatTotalTrajectoryVO> getFirstTrajectory(List<WechatTotalTrajectoryVO> result, int type){
        List<String> existList = new ArrayList<String>();
        List<WechatTotalTrajectoryVO> list = new ArrayList<WechatTotalTrajectoryVO>();
        for(WechatTotalTrajectoryVO wechatTotalTrajectoryVO : result){
            if(existList.contains(wechatTotalTrajectoryVO.getFromUserId())){
                if(type == 0){
                    existList.add(wechatTotalTrajectoryVO.getToUserId());
                }
                continue;
            }
            existList.add(wechatTotalTrajectoryVO.getFromUserId());
            if(type == 0){
                existList.add(wechatTotalTrajectoryVO.getToUserId());
            }
            WechatTotalTrajectoryVO fVo = new WechatTotalTrajectoryVO();
            fVo.setTotalId(wechatTotalTrajectoryVO.getTotalId());
            fVo.setFromUser("0");
            fVo.setToUserId(wechatTotalTrajectoryVO.getFromUserId());
            fVo.setToUser(wechatTotalTrajectoryVO.getFromUser());
            fVo.setTotalDate(wechatTotalTrajectoryVO.getTotalDate());
            list.add(fVo);
        }
        return list;
    }

    /**
     * 处理传播轨迹图，如果这个人被统计过，不管出于什么节点，都不统计
     * @param root 第一个节点
     * @param trajectoryMap
     * @param parent
     * @param currentLevel
     * @param maxLevel
     * @param existList
     */
    private int dealLevelTrajectory(WechatTotalTrajectoryVO root, Map<String, List<WechatTotalTrajectoryVO>> trajectoryMap, WechatTotalTrajectoryVO parent, int currentLevel, int maxLevel, List<String> existList, int type){
        List<WechatTotalTrajectoryVO> childList = parent.getChildList();
        if(childList == null){
            childList = new ArrayList<WechatTotalTrajectoryVO>();
            parent.setChildList(childList);
        }
        parent.setLevel(currentLevel);
        String currentUserId = parent.getToUserId();
        List<WechatTotalTrajectoryVO> tempTrajectoryList = trajectoryMap.get(currentUserId);
        if(tempTrajectoryList == null || tempTrajectoryList.size() < 1){
            return 1;
        }
        Iterator<WechatTotalTrajectoryVO> iterator = tempTrajectoryList.iterator();

        while (iterator.hasNext()){
            WechatTotalTrajectoryVO wechatTotalTrajectoryVO = iterator.next();
            if(existList.contains(wechatTotalTrajectoryVO.getToUserId())){
                continue;
            }
            if(type == 0){
                childList.add(wechatTotalTrajectoryVO);
                existList.add(wechatTotalTrajectoryVO.getToUserId());
            }else{
                if(parent.getTotalId().equals(wechatTotalTrajectoryVO.getTotalId())){
                    childList.add(wechatTotalTrajectoryVO);
                    existList.add(wechatTotalTrajectoryVO.getToUserId());
                }
            }
        }

        if(childList.size() < 1){
            return 1;
        }

        if(root.getMaxPeople() == null){
            root.setMaxPeople(childList.size());
        }else{
            root.setMaxPeople(root.getMaxPeople() + childList.size());
        }
        if(currentLevel > 1){
            parent.setMaxPeople(childList.size());
        }
        if(currentLevel >= maxLevel && maxLevel != -1){
            return 1;
        }
        int tempLevel = 0;

        for(WechatTotalTrajectoryVO wechatTotalTrajectoryVO : childList){
            if(wechatTotalTrajectoryVO.getFromUserId().equals(wechatTotalTrajectoryVO.getToUserId())){
                wechatTotalTrajectoryVO.setToUserId(wechatTotalTrajectoryVO.getToUserId() + "-repeat");
                continue;
            }
            int level = dealLevelTrajectory(root, trajectoryMap, wechatTotalTrajectoryVO, currentLevel + 1, maxLevel, existList, type);
            tempLevel = level;
        }
        parent.setMaxLevel(tempLevel);
        return tempLevel + 1;
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
        //统计进入小程序的数量
        totalFunnelViewLoginNum( start, end, dataList);
        //统计参与活动的人数/发起助力的人数
        totalFunnelViewActAndHelpNum( start, end, dataList);
        //统计完成任务的人数
        totalFunnelViewFinishTask( start, end, dataList);
        //统计领取奖励的人数
        totalFunnelViewHelpCommand( start, end, dataList);
//        //统计到达落地页人数
//        totalFunnelViewLastNum( start, end, dataList);

        result.put("data", dataList);
        return result;
    }

    /**
     * 统计进入小程序的数量
     * @param start
     * @param end
     * @param dataList
     */
    private void totalFunnelViewLoginNum(Date start, Date end, List<JSONObject> dataList){
        JSONObject loginNumData = new JSONObject();
        String loginSql = "select count(distinct wechat_user_id) from wechat_login_log where login_at between ? and ?";
        Integer loginNum = jdbcBaseDao.getCount(loginSql,  start, end);
        loginNumData.put("name", "进入小程序");
        loginNumData.put("value", loginNum);
        dataList.add(loginNumData);
    }

    /**
     * 统计参与活动的人数/发起助力的人数
     * @param start
     * @param end
     * @param dataList
     */
    private void totalFunnelViewActAndHelpNum(Date start, Date end, List<JSONObject> dataList){
        int actNum = 0;
        //统计助力数据
        String helpSql = "select distinct wechat_user_id from wechat_help_info where create_at between ? and ?";
        List<String> countHelpList = jdbcBaseDao.getColumns(String.class, helpSql,  start, end);
        //统计助力详情数据
        String helpDetailSql = "select distinct whd.to_wechat_user_id from wechat_help_detail whd where whd.whd.help_at between ? and ? and whd.status = 1";
        List<String> countHelpDetailList = jdbcBaseDao.getColumns(String.class, helpDetailSql,  start, end);

        int countHelpActNum = 0;
        int countHelpNum = 0;
        if(countHelpList != null && countHelpList.size() > 0){
            countHelpActNum = countHelpList.size();
        }
        countHelpNum = countHelpActNum;
        //排除发起人又是助力者的情况
        if(countHelpDetailList != null && countHelpDetailList.size() > 0){
            if(countHelpActNum > 0){
                countHelpDetailList.removeAll(countHelpList);
            }
            countHelpActNum += countHelpDetailList.size();
        }
        actNum += countHelpActNum;
        //参与活动的人数
        JSONObject actNumData = new JSONObject();
        actNumData.put("name", "参与活动的");
        actNumData.put("value", actNum);
        dataList.add(actNumData);
        //发起助力的人数
        JSONObject helpData = new JSONObject();
        helpData.put("name", "发起助力的人数");
        helpData.put("value", countHelpNum);
        dataList.add(helpData);
    }

    /**
     * 统计完成任务的人数
     * @param start
     * @param end
     * @param dataList
     */
    private void totalFunnelViewFinishTask(Date start, Date end, List<JSONObject> dataList){
        JSONObject finishTaskNumData = new JSONObject();
        String finishTaskSql = "select count(whd.help_detail_id) AS count_num,tmair.partake_num from wechat_help_detail whd" +
                " left join (" +
                "   SELECT min(mair.partake_num) as partake_num,mair.act_id FROM mat_activity_initiator_reward mair where mair.mair.type = 1 GROUP BY mair.act_id" +
                " ) tmair on tmair.act_id = whd.act_id" +
                " where whd.whd.help_at between ? and ?" +
                " group by whd.help_id,tmair.act_id,tmair.partake_num" +
                " having count_num >= ifnull(tmair.partake_num, 3)";
        List<Object> finishTaskList = jdbcBaseDao.getColumns(Object.class, finishTaskSql,   start, end);
        int finishTaskNum = 0;
        if(finishTaskList != null && finishTaskList.size() > 0){
            finishTaskNum = finishTaskList.size();
        }
        finishTaskNumData.put("name", "完成任务的人数");
        finishTaskNumData.put("value", finishTaskNum);
        dataList.add(finishTaskNumData);
    }

    /**
     * 统计领取奖励的人数
     * @param start
     * @param end
     * @param dataList
     */
    private void totalFunnelViewHelpCommand(Date start, Date end, List<JSONObject> dataList){
        JSONObject numData = new JSONObject();
        String sql = "select count(id) from behavior_analysis_help_command where create_at between ? and ? group by wechat_user_id";
        List<Object> list = jdbcBaseDao.getColumns(Object.class, sql,  start, end);
        int num = 0;
        if(list != null && list.size() > 0){
            num = list.size();
        }
        numData.put("name", "领取奖励的人数");
        numData.put("value", num);
        dataList.add(numData);
    }

    /**
     * 统计到达落地页人数
     * @param start
     * @param end
     * @param dataList
     */
    private void totalFunnelViewLastNum(Date start, Date end, List<JSONObject> dataList){
        JSONObject numData = new JSONObject();
        String sql = "select count(id) from behavior_analysis_help_open_url where create_at between ? and ? group by kf_open_id";
        List<Object> list = jdbcBaseDao.getColumns(Object.class, sql,  start, end);
        int num = 0;
        if(list != null && list.size() > 0){
            num = list.size();
        }
        numData.put("name", "到达落地页人数");
        numData.put("value", num);
        dataList.add(numData);
    }

}
