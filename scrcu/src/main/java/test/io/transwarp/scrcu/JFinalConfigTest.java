package test.io.transwarp.scrcu;

import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import io.transwarp.scrcu.app.*;
import io.transwarp.scrcu.base.controller.IndexController;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.custom.Custom;
import io.transwarp.scrcu.portal.EventAnalysisController;
import io.transwarp.scrcu.portal.SiteAnalysisController;
import io.transwarp.scrcu.portal.UserAnalysisController;
import io.transwarp.scrcu.portal.VisitSourceController;
import io.transwarp.scrcu.portrait.PortraitController;
import io.transwarp.scrcu.sqlinxml.SqlInXmlPlugin;
import io.transwarp.scrcu.system.nav.NavController;
import io.transwarp.scrcu.system.nav.SysNav;
import io.transwarp.scrcu.system.resource.ResourceController;
import io.transwarp.scrcu.system.resource.SysRes;
import io.transwarp.scrcu.system.role.RoleController;
import io.transwarp.scrcu.system.role.SysRole;
import io.transwarp.scrcu.system.role.SysRoleRes;
import io.transwarp.scrcu.system.users.UserRoles;
import io.transwarp.scrcu.system.users.Users;
import io.transwarp.scrcu.system.users.UsersController;
import io.transwarp.scrcu.tag.TagController;

/**
 * @author admin
 * @date 2017/3/21
 */
public class JFinalConfigTest extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        loadPropertyFile("scrcu_test.properties");

        InceptorUtil.devMode = PropKit.getBoolean("inceptor.devMode");
        InceptorUtil.encache = PropKit.getBoolean("inceptor.encache");
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class, "index");
        //用户画像
        me.add("/portrait", PortraitController.class);
        me.add("/tag", TagController.class);
        //系统设置
        me.add("/system/users", UsersController.class, "system/users");
        me.add("/system/role", RoleController.class, "system/role");
        me.add("/system/Resource", ResourceController.class, "system/Resource");
        me.add("/system/nav", NavController.class, "system/nav");
        // 门户
        me.add("/portal/userAnalysis", UserAnalysisController.class, "portal/userAnalysis");
        me.add("/portal/visitSource", VisitSourceController.class, "portal/visitSource");
        me.add("/portal/siteAnalysis", SiteAnalysisController.class, "portal/siteAnalysis");
        me.add("/portal/eventAnalysis", EventAnalysisController.class, "portal/eventAnalysis");

        // 移动
        me.add("/app/userAnalysis", AppUserController.class, "app/userAnalysis");
        me.add("/app/behavior", AppBehaviorController.class, "app/behavior");
        me.add("/app/appAnalysis", AppController.class, "app/appAnalysis");
        me.add("/app/channel", AppChannelController.class, "app/channel");
        me.add("/app/memberAnalysis", AppMemberController.class, "app/memberAnalysis");
        me.add("/app/event", AppEventController.class, "app/event");
    }

    @Override
    public void configPlugin(Plugins me) {

        //获取数据库的配置
        String jdbcUrl = getProperty("jdbcUrl");
        String driver = getProperty("driverClass");
        String username = getProperty("username");
        String password = getProperty("password");

        // Druid
        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, username, password, driver);
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType(getProperty("dbType"));
        druidPlugin.addFilter(wallFilter);
        me.add(druidPlugin);

        // ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin("testDruid", druidPlugin);
        arp.setDialect(new MysqlDialect());
        arp.addMapping("sys_users", Users.class);
        arp.addMapping("sys_user_roles", UserRoles.class);
        arp.addMapping("custom", Custom.class);
        arp.addMapping("sys_res", SysRes.class);
        arp.addMapping("sys_role", SysRole.class);
        arp.addMapping("sys_nav", SysNav.class);
        arp.addMapping("sys_role_res", SysRoleRes.class);
        me.add(arp);

        me.add(new SqlInXmlPlugin());
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 8080, "/", 5);
    }

}
