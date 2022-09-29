package cn.ofpp.core;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.util.List;

import static cn.hutool.json.JSONUtil.parseObj;

/**
 * <a href="https://lbs.amap.com">使用的高德</a>
 * @author DokiYolo
 * Date 2022-08-22
 */
public class GaodeUtil {
    private static final String key = "d8e7d2c3ea77b2fe74def48c88cd7954";

    private static final String GEO_API = "https://restapi.amap.com/v3/geocode/geo?key=%s&address=%s&city=%s";
    private static final String WEATHER_API = "https://restapi.amap.com/v3/weather/weatherInfo?key=%s&city=%d&extensions=%s";

    public static Integer getAdcCode(String province, String city) {
        HttpResponse response = HttpUtil.createGet(String.format(GEO_API, key, province, city))
                .setConnectionTimeout(3000)
                .setReadTimeout(4000)
                .execute();
        return parseObj(parseObj(response.body()).getJSONArray("geocodes").get(0)).getInt("adcode");
    }

    public static WeatherInfo getNowWeatherInfo(Integer adcCode) {
        HttpResponse response = HttpUtil.createGet(String.format(WEATHER_API, key, adcCode, "base"))
                .setConnectionTimeout(3000)
                .setReadTimeout(4000)
                .execute();
        List<WeatherInfo> lives =
                parseObj(response.body()).getJSONArray("lives").toList(WeatherInfo.class);
        return lives.get(0);
    }

}
