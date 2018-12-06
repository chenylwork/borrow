package com.work.borrow.util.baidu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.borrow.dao.redis.RedisDao;
import com.work.borrow.dao.redis.RedisDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
@Service("authService")
public class AuthService {

    //可以换成@Inject
    @Autowired
    private RedisDao redisDaoAutowired;

    private static RedisDao redisDao;

    @PostConstruct
    public void init() {
        this.redisDao = redisDaoAutowired;
    }

    /**
     * 获取权限token
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
       // StringRedisTemplate redisTemplate = new StringRedisTemplate();
        String result = redisDao.get(TOKEN);
        if (result != null && !result.equals("")) {
            return result;
        }
        // 官网获取的 API Key 更新为你注册的
        String clientId = "xwBO73nwxHCTiPA2QLsFR47f";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "xAaMPpiz9gv4F6bpgZ3iMQV6c8pvx4y0";
        result = getAuth(clientId, clientSecret);
        redisDao.set(TOKEN,result,OUT_TIME-BEFORE_TIME);
//        redisDao.set(TOKEN,"24.c7074ca207e8ad64f7f0629172d6bc4a.2592000.1544749071.282335-14699690",OUT_TIME-BEFORE_TIME);
        return result;
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.err.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            ObjectMapper objectMapper = new ObjectMapper();
            Map resultMap = objectMapper.readValue(result, Map.class);
//            JSONObject jsonObject = new JSONObject(result);
            String access_token = (String) resultMap.get("access_token");
//            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
//             e.printStackTrace(System.err);
        }
        return null;
    }

    private static final String TOKEN = "baidu_token";
    private static final long OUT_TIME = 2592000;
    private static final long BEFORE_TIME = 60*60*24;

}
