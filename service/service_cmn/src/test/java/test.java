import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author scx
 * @Date 2022/3/4 20:50
 * @Description
 */

public class test {

    @Test
    public void test() throws UnsupportedEncodingException {
        String fileName = URLEncoder.encode("数据字典", "UTF-8");
        System.out.println("fileName = " + fileName);
    }

}
