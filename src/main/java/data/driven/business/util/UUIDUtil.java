package data.driven.business.util;

import org.bson.types.ObjectId;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author 何晋凯
 * @date 2018/06/04
 */
public class UUIDUtil {

    public static String getUUID(){
        return new ObjectId().toString();
    }

    public static void main(String[] args)throws Exception {
//        String sql = "INSERT INTO `reward_act_command` (`command_id`, `command`, `command_type`, `act_id`, `user_id`, `app_info_id`, `used`, `create_at`) VALUES ";
//        StringBuilder sb = new StringBuilder(sql);
//        for (int i = 0; i < 3000; i++){
//            String b = "('" + getUUID() + "', '￥dA7EbeKlVlO￥', '2', '5', 'fkxg', '5b699c9171c8a90ec8201702', '0', '2018-09-28 17:35:07')";
//            sb.append(b).append(",");
//        }
//        System.out.println(sb.delete(sb.length()-1,sb.length()));

//        System.out.println(getUUID());

        String pathA = "E:/tempbj/a.txt";
        String pathB = "E:/tempbj/b.txt";

        String a = Files.readAllLines(Paths.get(pathA)).get(0);
        String b = Files.readAllLines(Paths.get(pathB)).get(0);

        System.out.println(a.length());
        System.out.println(b.length());

    }
}
