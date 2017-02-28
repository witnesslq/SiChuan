package io.transwarp.scrcu.tag;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.sqlinxml.SqlKit;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by TANG_0722 on 2017-02-23.
 */
@RequiresAuthentication
public class TagController extends Controller {

    StringBuffer value = new StringBuffer(" ");

    //调用SQL，循环执行插入语句。
    public void rangToRangCommon(Object config) {
        String oper_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms").format(new Date());
        String oper_user = getPara("oper_user");
        String[] keys = getParaValues("key");
        for (int i = 0; i < keys.length; i++) {
            InceptorUtil.mapQuery(SqlKit.propSQL(config, ConditionUtil.rangToRang(keys[i], getPara("name" + i), getPara("start" + i), getPara("end" + i), oper_time, oper_user).toString()), false);
        }
    }

    public void checkCommon(Object config) {
        String oper_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms").format(new Date());
        String oper_user = getPara("oper_user");
        String[] names = getParaValues("names");
        StringBuffer content_name = new StringBuffer("");
        for (int i = 0; i < names.length; i++) {
            String[] checked = getParaValues(names[i]);
            if (checked != null) {
                for (int j = 0; j < checked.length; j++) {
                    content_name.append(checked[j]).append("、");
                }
                content_name.delete(content_name.length() - 1, content_name.length());
            }
            InceptorUtil.mapQuery(SqlKit.propSQL(config, ConditionUtil.check(getPara("key" + i), names[i], content_name.toString(), oper_time, oper_user).toString()), false);
            content_name.delete(0, content_name.length());
        }
    }

    public void selectCommon(Object config) {
        String oper_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms").format(new Date());
        String oper_user = getPara("oper_user");
        String[] contents = getParaValues("content_name");
        StringBuffer content_name = new StringBuffer("");
        for (int i = 0; i < contents.length; i++) {
            content_name.append(contents[i]).append("、");
        }
        content_name.delete(content_name.length() - 1, content_name.length());
        InceptorUtil.mapQuery(SqlKit.propSQL(config, ConditionUtil.select(getPara("key"), getPara("rank"), content_name.toString(), oper_time, oper_user).toString()), false);
    }

    //可配置标签列表页----start
    public void index() {
        List<String[][]> tradFeas = new ArrayList<>();
        List<String[][]> peoAttrs = new ArrayList<>();
        List<String[][]> creAttrs = new ArrayList<>();
        List<String[][]> opeAttrs = new ArrayList<>();
        String[][] tradFea = {{"差旅人群", "peoTravel"}, {"客户参与活动", "partActive"}, {"月交易次数", "tranNum"}, {"时间偏好", "timeHobby"}, {"支付偏好", "payHobby"}, {"行内外转账", "bankTran"}, {"转账类型偏好", "tranType"}, {"用户群体类型", "groupType"}, {"持有产品服务", "prodServer"}, {"产品功能消费偏好", "customHobby"}, {"月交易金额", "tranMoney"}, {"安全认证方式", "secAuth"}, {"汇路时效", "sendEff"}, {"交易偏好", "tranHobby"}, {"存款产品偏好", "prodHobby"}};
        String[][] peoAttr = {{"年龄", "age"}, {"职业", "career"}, {"社交关系", "socRelation"}};
        String[][] creAttr = {{"蜀信e注册年限", "regYear"}, {"资产负债", "proDebt"}};
        String[][] opeAttr = {{"网页操作行为", "opeBehavior"}, {"搜索引擎", "searchEng"}, {"活跃变化特征", "chaFeature"}, {"网银搜索关键字", "searchKey"}, {"使用时段", "useTime"}, {"终端", "terminal"}, {"活跃功能特征", "funcFeature"}};
        tradFeas.add(tradFea);
        peoAttrs.add(peoAttr);
        creAttrs.add(creAttr);
        opeAttrs.add(opeAttr);
        setAttr("tradFeas", tradFeas.get(0));
        setAttr("peoAttrs", peoAttrs.get(0));
        setAttr("creAttrs", creAttrs.get(0));
        setAttr("opeAttrs", opeAttrs.get(0));
    }
    //可配置标签列表页----end

    //年龄----start
    public void age() {
        List<Map<String, Object>> ages = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.age_label), false);
        setAttr("ages", ages);
    }

    public void ageConfig() {
        rangToRangCommon(SQLConfig.age_label_config);
        redirect("/tag");
    }
    //年龄----end

    //职业----start
    public void career() {
        List<Map<String, Object>> jobs = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.job_label), false);
        List<String> contents = new ArrayList<>();
        String[] checked = {"军人", "其他", "未知", "国家机关", "党群组织", "企业事业单位", "专业技术人员", "商业服务业人员", "办事人员和有关人员", "农林牧渔水利业生产人员", "生产运输设备操作人员及有关人员"};
        for (int i = 0; i < checked.length; i++) {
            contents.add(checked[i]);
        }
        setAttr("contents", contents);
        setAttr("jobs", jobs);
    }

    public void careerConfig() {
        checkCommon(SQLConfig.job_label_config);
        redirect("/tag");
    }
    //职业----end

    //蜀信e注册年限----start
    public void regYear() {
        List<Map<String, Object>> regYears = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.reg_year_label), false);
        setAttr("regYears", regYears);
    }

    public void regYearConfig() {
        rangToRangCommon(SQLConfig.reg_year_label_config);
        redirect("/tag");
    }
    //蜀信e注册年限----end

    //资产负债----start
    public void proDebt() {
        List<Map<String, Object>> proDebts = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.pro_debt_label), false);
        setAttr("proDebts", proDebts);
    }

    public void proDebtConfig() {
        rangToRangCommon(SQLConfig.pro_debt_label_config);
        redirect("/tag");
    }
    //资产负债----end

    //差旅人群----start----未完成
    public void peoTravel() {

    }
    //差旅人群----end

    //持有产品服务----start
    public void prodServer() {
        List<Map<String, Object>> prodServers = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.part_active_label), false);
        setAttr("prodServers", prodServers);
    }

    public void prodServerConfig() {
        rangToRangCommon(SQLConfig.part_active_label_config);
        redirect("/tag");
    }
    //持有产品服务----end

    //客户参与活动----start
    public void partActive() {
        List<Map<String, Object>> partActives = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.part_active_label), false);
        setAttr("partActives", partActives);
    }

    public void partActiveConfig() {
        rangToRangCommon(SQLConfig.part_active_label_config);
        redirect("/tag");
    }
    //客户参与活动----end

    //产品功能消费偏好----start
    public void customHobby() {
        List<Map<String, Object>> customHobbys = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.custom_hobby_label), false);
        String[] select = {"红包", "AA", "游戏爱好者", "彩票达人", "车票达人", "机票达人", "电信缴费", "电力缴费", "水费缴费", "燃气缴费", "签到达人", "收藏达人"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("customHobbys", customHobbys);
    }

    public void customHobbyConfig() {
        selectCommon(SQLConfig.custom_hobby_label_config);
        redirect("/tag");
    }
    //产品功能消费偏好----end

    //月交易次数----start
    public void tranNum() {
        List<Map<String, Object>> tranNums = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.tran_num_label), false);
        setAttr("tranNums", tranNums);
    }

    public void tranNumConfig() {
        rangToRangCommon(SQLConfig.tran_num_label_config);
        redirect("/tag");
    }
    //月交易次数----end

    //月交易金额----start
    public void tranMoney() {
        List<Map<String, Object>> tranMoneys = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.tran_money_label), false);
        setAttr("tranMoneys", tranMoneys);
    }

    public void tranMoneyConfig() {
        rangToRangCommon(SQLConfig.tran_money_label_config);
        redirect("/tag");
    }
    //月交易金额----end

    //时间偏好----start
    public void timeHobby() {
        List<Map<String, Object>> timeHobbys = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.time_hobby_label), false);
        setAttr("timeHobbys", timeHobbys);
    }

    public void timeHobbyConfig() {
        rangToRangCommon(SQLConfig.time_hobby_label_config);
        redirect("/tag");
    }
    //时间偏好----end

    //安全认证方式----start
    public void secAuth() {
        List<Map<String, Object>> secAuths = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.sec_auth_label), false);
        List<String> contents = new ArrayList<>();
        String[] checked = {"UK一代认证", "UK二代认证", "UK三代认证", "SE证书认证", "刮刮卡认证", "邮箱认证", "合作平台认证", "安全问题认证", "手势密码认证", "二维码认证", "登录密码认证", "预留信息认证", "手机短信认证", "支付密码认证", "文件证书认证", "设备绑定认证", "软令牌认证", "硬令牌认证"};
        for (int i = 0; i < checked.length; i++) {
            contents.add(checked[i]);
        }
        setAttr("contents", contents);
        setAttr("secAuths", secAuths);
    }

    public void secAuthConfig() {
        checkCommon(SQLConfig.sec_auth_label_config);
        redirect("/tag");
    }
    //安全认证方式----end

    //支付偏好----start
    public void payHobby() {
        List<Map<String, Object>> payHobbys = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.pay_hobby_label), false);
        String[] select = {"财付通", "京东支付", "银联在线支付", "支付宝快捷支付", "蜀信e转账支付"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("payHobbys", payHobbys);
    }

    public void payHobbyConfig() {
        selectCommon(SQLConfig.pay_hobby_label_config);
        redirect("/tag");
    }
    //支付偏好----start

    //汇路时效----start
    public void sendEff() {
        List<Map<String, Object>> sendEffs = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.send_eff_label), false);
        String[] select = {"普通汇款", "快速汇款", "实时汇款"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("sendEffs", sendEffs);
    }

    public void sendEffConfig() {
        selectCommon(SQLConfig.send_eff_label_config);
        redirect("/tag");
    }
    //汇路时效----start

    //行内外转账----start
    public void bankTran() {
        List<Map<String, Object>> bankTrans = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.bank_tran_label), false);
        String[] select = {"行内转账", "跨行转账"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("bankTrans", bankTrans);
    }

    public void bankTranConfig() {
        selectCommon(SQLConfig.bank_tran_label_config);
        redirect("/tag");
    }
    //行内外转账----end

    //交易偏好----start
    public void tranHobby() {
        List<Map<String, Object>> tranHobbys = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.tran_hobby_label), false);
        String[] select = {"存款", "取款", "转账", "消费", "贷款", "费用套餐", "理财", "支付"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("tranHobbys", tranHobbys);
    }

    public void tranHobbyConfig() {
        selectCommon(SQLConfig.tran_hobby_label_config);
        redirect("/tag");
    }
    //交易偏好----end

    //转账类型偏好----start
    public void tranType() {
        List<Map<String, Object>> tranTypes = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.tran_type_label), false);
        String[] select = {"随E转", "好友转账", "普通转账", "电子回单", "转帐通讯录", "资金归集"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("tranTypes", tranTypes);
    }

    public void tranTypeConfig() {
        selectCommon(SQLConfig.tran_type_label_config);
        redirect("/tag");
    }
    //转账类型偏好----end

    //存款产品偏好----start
    public void prodHobby() {
        List<Map<String, Object>> prodHobbys = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.prod_hobby_label), false);
        String[] select = {"双整", "零整", "定活两便", "整零", "通知存款", "智能通知存款", "约期定期", "定活宝", "定存宝"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("prodHobbys", prodHobbys);
    }

    public void prodHobbyConfig() {
        selectCommon(SQLConfig.prod_hobby_label_config);
        redirect("/tag");
    }
    //存款产品偏好----end

    //用户群体类型----start
    public void groupType() {
        List<Map<String, Object>> groupTypes = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.group_type_label), false);
        setAttr("groupTypes", groupTypes);
    }

    public void groupTypeConfig() {
        rangToRangCommon(SQLConfig.group_type_label_config);
        redirect("/tag");
    }
    //用户群体类型----end

    //社交关系----start
    public void socRelation() {

    }

    public void socRelationConfig() {

    }
    //社交关系----end

    //网页操作行为----start
    public void opeBehavior() {

    }

    public void opeBehaviorConfig() {

    }
    //网页操作行为----end

    //使用时段----start
    @RequiresPermissions("/tag/useTime")
    public void useTime() {
        List<Map<String, Object>> useTimes = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.use_time_label), false);
        setAttr("useTimes", useTimes);
    }

    @RequiresPermissions("/tag/useTimeConfig")
    public void useTimeConfig() {
        rangToRangCommon(SQLConfig.use_time_label_config);
        redirect("/tag");
    }
    //使用时段----end

    //搜索引擎----start
    public void searchEng() {
        List<Map<String, Object>> searchEngs = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.search_eng_label), false);
        String[] select = {"百度", "360", "谷歌", "搜狗", "Bing"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("searchEngs", searchEngs);
    }

    public void searchEngConfig() {
        selectCommon(SQLConfig.search_eng_label_config);
        redirect("/tag");
    }
    //搜索引擎----end

    //终端----start
    public void terminal() {
        List<Map<String, Object>> terminals = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.terminal_label), false);
        String[] select = {"PC", "平板电脑", "安卓手机", "苹果手机"};
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            contents.add(select[i]);
        }
        setAttr("contents", contents);
        setAttr("terminals", terminals);
    }

    public void terminalConfig() {
        selectCommon(SQLConfig.terminal_label_config);
        redirect("/tag");
    }
    //终端----end


    //活跃变化特征----start
    public void chaFeature() {

    }

    public void chaFeatureCoonfig() {

    }
    //活跃变化特征----end

    //活跃功能特征----start----未完成
    public void funcFeature() {

    }
    //活跃功能特征----end

    //网银搜索关键字----start----未完成
    public void searchKey() {

    }
    //网银搜索关键字----end
}
