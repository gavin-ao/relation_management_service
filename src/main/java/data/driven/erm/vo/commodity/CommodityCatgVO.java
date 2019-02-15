package data.driven.erm.vo.commodity;

import data.driven.erm.entity.commodity.CommodityCatgEntity;
import data.driven.erm.util.DateFormatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: relation_management_service
 * @description:
 * @author: Logan
 * @create: 2019-02-15 15:50
 **/

public class CommodityCatgVO extends CommodityCatgEntity {
    private String fmtCreateTime;
    private String fmtUpdateTime;


    @Override
    public void setCreateAt(Date createAt) {

        super.setCreateAt(createAt);
        fmtCreateTime = DateFormatUtil.getLocal(DateFormatUtil.timePattern).format(createAt);
    }
    @Override
    public void setUpdateAt(Date updateAt) {
       super.setUpdateAt(updateAt);
        fmtUpdateTime = DateFormatUtil.getLocal(DateFormatUtil.timePattern).format(updateAt);
    }
    public String getFmtCreateTime() {
        return fmtCreateTime;
    }

    public void setFmtCreateTime(String fmtCreateTime) {
        this.fmtCreateTime = fmtCreateTime;
    }

    public String getFmtUpdateTime() {
        return fmtUpdateTime;
    }

    public void setFmtUpdateTime(String fmtUpdateTime) {
        this.fmtUpdateTime = fmtUpdateTime;
    }
}
