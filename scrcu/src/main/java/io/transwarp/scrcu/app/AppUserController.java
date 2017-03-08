package io.transwarp.scrcu.app;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.i18n.I18n;
import com.jfinal.i18n.Res;
import io.transwarp.scrcu.base.controller.BaseController;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.ChartUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.sqlinxml.SqlKit;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.ArrayList;
import java.util.List;

@RequiresAuthentication
public class AppUserController extends BaseController {

    Res res = I18n.use("i18n", "zh_CN");

    /**
     * app活跃用户数据
     */
    @SuppressWarnings("unchecked")
    @RequiresPermissions("/app/userAnalysis/activeUser")
    public void activeUser() {

        if (BaseUtils.isAjax(getRequest())) {

            List<List<String>> dataTime = new ArrayList<>();
            List<List<String>> dataPhone = new ArrayList<>();
            List<List<String>> dataChannel = new ArrayList<>();
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 得到查询条件
            String condition = InceptorUtil.getQueryCondition(getRequest());
            String type = getPara("dateType");
            if (type != null) {
                if (type.equals("day")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_activeUser_day, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_phone_day, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_channel_day, condition));
                }
                if (type.equals("week")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_activeUser_week, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_phone_week, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_channel_week, condition));
                }
                if (type.equals("month")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_activeUser_month, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_phone_month, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_channel_month, condition));
                }
                if (type.equals("quarter")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_activeUser_quarter, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_phone_quarter, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_channel_quarter, condition));
                }
                if (type.equals("year")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_activeUser_year, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_phone_year, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser_channel_year, condition));
                }
            }
            //定义图例名称
            Object[] nameList = new Object[]{res.get("app.startUser")};
            // 返回结果
            List<Object> xAxisList = new ArrayList<>();
            List<Object> dataList = new ArrayList<>();
            for (List<String> list : dataTime) {
                xAxisList.add(list.get(0));
                dataList.add(list.get(1));
            }
            String activeUserChart = ChartUtils.genAppMultiLineCharts(type, xAxisList, nameList, dataList);
            result.put("timeChart", activeUserChart);

           /* // 返回结果
            List<Object> phoneXAxisList = new ArrayList<>();
            List<Object> phoneList = new ArrayList<>();
            Object[] phoneNames = new Object[]{"Android", "IOS"};
            for (List<String> list : dataPhone) {
                if (!xAxisList.contains(list.get(0))) {
                    phoneXAxisList.add(list.get(0));
                }
                if (list.get(1).contains("Android")) {
                    phoneList.add(list.get(2));
                }
                if (list.get(1).toUpperCase().contains("IOS")) {
                    phoneList.add(list.get(2));
                }
            }
            String phoneChart = ChartUtils.genAppMultiLineCharts(type, phoneXAxisList, phoneNames, phoneList);
            result.put("phoneChart", phoneChart);

            // 返回结果
            List<Object> channelXAxisList = new ArrayList<>();
            List<Object> channelList = new ArrayList<>();
            Object[] channelNames = new Object[]{"启动用户数"};
            for (List<String> list : dataChannel) {
                channelXAxisList.add(list.get(0));
                channelList.add(list.get(2));
            }
            String channelChart = ChartUtils.genAppMultiLineCharts(type, channelXAxisList, channelNames, channelList);
            result.put("channelChart", channelChart);*/

            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);
        }

    }

    /**
     * app留存用户数据
     */
    @SuppressWarnings("unchecked")
    @RequiresPermissions("/app/userAnalysis/retainUser")
    public void retainUser() {
        if (BaseUtils.isAjax(getRequest())) {

            List<List<String>> dataTime = new ArrayList<>();
            List<List<String>> dataPhone = new ArrayList<>();
            List<List<String>> dataChannel = new ArrayList<>();
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 得到查询条件
            String condition = InceptorUtil.getQueryCondition(getRequest());
            String type = getPara("dateType");
            if (type != null) {
                if (type.equals("day")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_retainUser_day, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_phone_day, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_channel_day, condition));
                }
                if (type.equals("week")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_retainUser_week, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_phone_week, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_channel_week, condition));
                }
                if (type.equals("month")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_retainUser_month, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_phone_month, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_channel_month, condition));
                }
                if (type.equals("quarter")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_retainUser_quarter, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_phone_quarter, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_channel_quarter, condition));
                }
                if (type.equals("year")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_retainUser_year, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_phone_year, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser_channel_year, condition));
                }
            }

            //返回结果
            List<Object> xAxisList = new ArrayList<>();
            List<Object> dataList = new ArrayList<>();
            for (List<String> list : dataTime) {
                xAxisList.add(list.get(0));
                dataList.add(list.get(1));
            }
            String str = ChartUtils.genLineChart(res.get("app.newAddUser"), xAxisList, dataList);
            result.put("chartOption", str);
            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);
        }
    }

    /**
     * app注册用户数据
     */
    @SuppressWarnings("unchecked")
    @RequiresPermissions("/app/userAnalysis/regUser")
    public void regUser() {
        if (BaseUtils.isAjax(getRequest())) {

            List<List<String>> dataPhone = new ArrayList<>();
            List<List<String>> dataChannel = new ArrayList<>();
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 得到查询条件
            String condition = InceptorUtil.getQueryCondition(getRequest());
            String dateType = getPara("dateType");
            if (dateType != null) {
                if (dateType.equals("day")) {
                    dataChannel = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_regUser_channel_day, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_regUser_os_day, condition));
                }
                if (dateType.equals("month")) {
                    dataChannel = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_regUser_channel_month, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_regUser_os_month, condition));
                }
                if (dateType.equals("quarter")) {
                    dataChannel = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_regUser_channel_quarter, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_regUser_os_quarter, condition));
                }
                if (dateType.equals("year")) {
                    dataChannel = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_regUser_channel_year, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_regUser_os_year, condition));
                }
            }
            // 返回结果
            List<Object> xAxisList = new ArrayList<>();
            List<Object> androidList = new ArrayList<>();
            List<Object> iosList = new ArrayList<>();
            for (List<String> list : dataPhone) {
                if (!xAxisList.contains(list.get(0))) {
                    xAxisList.add(list.get(0));
                }
                String os = list.get(1);
                if (os.contains("Android")) {
                    androidList.add(list.get(2));
                }
                if (os.toUpperCase().contains("IOS")) {
                    iosList.add(list.get(2));
                }
            }
            Object[] nameList = new Object[]{"android", "ios"};
            String str = ChartUtils.genAppMultiLineCharts(dateType, xAxisList, nameList, androidList, iosList);
            result.put("chartOption", str);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);

        }
    }

    /**
     * app登录用户数据
     */
    @SuppressWarnings("unchecked")
    @RequiresPermissions("/app/userAnalysis/loginUser")
    public void loginUser() {
        if (BaseUtils.isAjax(getRequest())) {

            List<List<String>> dataTime = new ArrayList<>();
            List<List<String>> dataPhone = new ArrayList<>();
            List<List<String>> dataChannel = new ArrayList<>();
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 得到查询条件
            String condition = InceptorUtil.getQueryCondition(getRequest());
            String type = getPara("dateType");
            if (type != null) {
                if (type.equals("day")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_loginUser_day, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser_os_day, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser_channel_day, condition));
                }
                if (type.equals("month")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_loginUser_month, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser_os_month, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser_channel_month, condition));
                }
                if (type.equals("quarter")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_loginUser_quarter, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser_os_quarter, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser_channel_quarter, condition));
                }
                if (type.equals("year")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_loginUser_year, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser_os_year, condition));
                    dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser_channel_year, condition));
                }
            }

            // 返回结果
            List<Object> xAxisList = new ArrayList<>();
            List<Object> startCntList = new ArrayList<>();
            List<Object> loginCntList = new ArrayList<>();
            for (List<String> list : dataTime) {
                if (!xAxisList.contains(list.get(0))) {
                    xAxisList.add(list.get(0));
                }
                startCntList.add(list.get(1));
                loginCntList.add(list.get(2));
            }
            Object[] nameList = new Object[]{res.get("app.loginUser"), res.get("app.startUser")};
            String str = ChartUtils.genAppMultiLineCharts(type, xAxisList, nameList, startCntList, loginCntList);
            result.put("chartOption", str);
            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);
        }
    }

    /**
     * app新增用户数据
     */
    @SuppressWarnings("unchecked")
    @RequiresPermissions("/app/userAnalysis/newUser")
    public void newUser() {
        if (BaseUtils.isAjax(getRequest())) {

            List<List<String>> dataTime = new ArrayList<>();
            List<List<String>> dataPhone = new ArrayList<>();
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 得到查询条件
            String condition = InceptorUtil.getQueryCondition(getRequest());
            String type = getPara("dateType");
            if (type != null) {
                if (type.equals("day")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_newUser_day, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_newUser_phone_day, condition));
                }
                if (type.equals("week")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_newUser_week, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_newUser_phone_week, condition));
                }
                if (type.equals("month")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_newUser_month, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_newUser_phone_month, condition));
                }
                if (type.equals("quarter")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_newUser_quarter, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_newUser_phone_quarter, condition));
                }
                if (type.equals("year")) {
                    dataTime = InceptorUtil.queryCache(SqlKit.propSQL(SQLConfig.app_newUser_year, condition), false);
                    dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_newUser_phone_year, condition));
                }
            }

            //返回结果
            List<Object> xAxisList = new ArrayList<>();
            List<Object> dataList = new ArrayList<>();
            for (List<String> list : dataTime) {
                xAxisList.add(list.get(0));
                dataList.add(list.get(1));
            }

            Object[] name = new Object[]{res.get("app.newAddUser")};
            String str = ChartUtils.genAppMultiLineCharts(type, xAxisList, name, dataList);
            result.put("chartOption", str);
            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            renderJson(result);
        }
    }

}

