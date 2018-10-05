package data.driven.erm.business.order.impl;

import data.driven.erm.business.order.OrderReceiveAddrService;
import data.driven.erm.dao.JDBCBaseDao;
import data.driven.erm.entity.order.OrderReceiveAddrEntity;
import data.driven.erm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author hejinkai
 * @date 2018/10/2
 */
@Service
public class OrderReceiveAddrServiceImpl implements OrderReceiveAddrService {

    @Autowired
    private JDBCBaseDao jdbcBaseDao;

    @Override
    public List<OrderReceiveAddrEntity> findAddrList(String wechatUserId) {
        String sql = "select addr_id,alias,country,province,city,region,detail_addr,addressee,phone_number,telephone,last_choose,default_addr from order_receive_addr where wechat_user_id = ? order by create_at desc,addr_id";
        List<OrderReceiveAddrEntity> addrList = jdbcBaseDao.queryList(OrderReceiveAddrEntity.class, sql, wechatUserId);
        return addrList;
    }

    @Override
    public OrderReceiveAddrEntity getAddr(String addrId) {
        String sql = "select addr_id,alias,country,province,city,region,detail_addr,addressee,phone_number,telephone,last_choose,default_addr from order_receive_addr where addr_id = ? ";
        List<OrderReceiveAddrEntity> addrList = jdbcBaseDao.queryList(OrderReceiveAddrEntity.class, sql, addrId);
        if(addrList != null && addrList.size() > 0){
            return addrList.get(0);
        }
        return null;
    }

    @Override
    public OrderReceiveAddrEntity getDefaultAddr(String wechatUserId) {
        String sql = "select addr_id,alias,country,province,city,region,detail_addr,addressee,phone_number,telephone from order_receive_addr where wechat_user_id = ? order by default_addr desc,create_at desc limit 1";
        List<OrderReceiveAddrEntity> addrList = jdbcBaseDao.queryList(OrderReceiveAddrEntity.class, sql, wechatUserId);
        if(addrList != null && addrList.size() > 0){
            return addrList.get(0);
        }
        return null;
    }

    @Override
    public void updateAddr(OrderReceiveAddrEntity addr) {
        if(addr.getAddrId() == null){
            addr.setAddrId(UUIDUtil.getUUID());
            addr.setCreateAt(new Date());
            jdbcBaseDao.insert(addr, "order_receive_addr");
        }else{
            jdbcBaseDao.update(addr, "order_receive_addr", "addr_id", false);
        }
    }

    @Override
    public void deleteAddr(String ids, String wechatUserId) {
        StringBuilder sqlWhere = new StringBuilder();
        List<String> idList = Arrays.asList(ids.split(","));
        for(String str : idList){
            sqlWhere.append(",?");
        }
        sqlWhere.delete(0,1);
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(wechatUserId);
        paramList.addAll(idList);
        String sql = "delete from order_receive_addr where wechat_user_id = ? and addr_id in (" + sqlWhere + ")";
        jdbcBaseDao.executeUpdateWithListParam(sql, paramList);
    }
}
