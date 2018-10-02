package data.driven.erm.vo.order;

import data.driven.erm.entity.order.OrderDetailEntity;

/**
 * @author hejinkai
 * @date 2018/10/3
 */
public class OrderDetailVO extends OrderDetailEntity {

    /** 图片 **/
    public String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
