package com.hp.config.security;

import com.hp.pojo.Account;
import com.hp.service.IAccountService;
import com.hp.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@SpringBootConfiguration //是一个java类用来替代applicationContext.xml
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private HpAuthenticationSuccessHandler hpAuthenticationSuccessHandler;
    @Autowired
    private HpAuthenticationFailureHandler hpAuthenticationFailureHandler;
    @Autowired
    private HplogoutSuccessHandler hplogoutSuccessHandler;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    //放行的资源配置
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/imagegs/**",
                "/css/**",
                "/js/**",
                "/lib/**",
                "/error/**"
        );
    }

    //配置表单登录
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf
        http.csrf().disable()
                //允许ifream 页面嵌套
                .headers().frameOptions().disable()
                //配置表单登录
                .and()
                    .formLogin()
                    .usernameParameter("userName") //登录页面的用户名变量名
                    .passwordParameter("password") //登录页面的密码变量名
                    .loginPage("/index") //登录页面
                    .loginProcessingUrl("/login")//表单提交地址
                    .successHandler(hpAuthenticationSuccessHandler) //成功的处理
                    .failureHandler(hpAuthenticationFailureHandler)  //失败的处理类
                .and()
                    .logout()
                    .logoutUrl("/signout")  //登出url
                    .logoutSuccessHandler(hplogoutSuccessHandler) //登出成功处理类
                .and()
                    .rememberMe()
                    .rememberMeParameter("rememberMe") //免登录前端传递过来的参数名
                    .rememberMeCookieName("remember-me-cookie") //保存到cookie中的名字
                    .tokenValiditySeconds(7 * 24 * 60 * 60) //7天免登录 (单位是秒)
                    .tokenRepository(persistentTokenRepository)
                .and()
                    .authorizeRequests().antMatchers("/index","/login").permitAll()
                    .anyRequest().authenticated();
    }

    @Resource
    private IPermissionService permissionService;
    @Bean
    //实现UserDetailsService接口 -- 用户登录
    //设置用户拥有的权限
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Account account = accountService.findAccountByUserName(username);
                if(null == account){
                    throw new UsernameNotFoundException("用户名不存在");
                }
                //存在,设置用户所拥有的权限
                //根据用户名，查询用户所拥有的权限 -- 集合是权限值
                List<String> authorities = permissionService.findAuthorityByUserName(username);
                //根据用户查询扮演的角色（查询该用户所拥有的角色 ---> 权限）
                account.setGrantedAuthority(
                        AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authorities))
                );
                return account;
            }
        };
    }

    //配置密码加密
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    //应用密码加密的配置
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
    }


    //这里一定要使用 @Resource注解,用来自动注入数据源
    //@Autowired根据类型自动装配(接口下有一个实现类，那么自动注入接口，那么会根据类型找到接口的实现),如果接口下有两个实现类则不能直接使用@Autowired
    //@Resouce(name="xxx") 按照name自动装配,如果接口下直接一个实现类，直接使用@esource，那么它就会直接找该接口下的实现，如果有多个实现类
    @Resource
    private DataSource dataSource;

    //免登录配置
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        //通过jdbc从数据库中获取token
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //自动在数据库中生成保存token的表（这行代码我们只能运行一次，表结构一旦生成，再次运行这行代码会报错）
       // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
