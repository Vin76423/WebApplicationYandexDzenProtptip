import by.tms.interceptor.HomeInterсeptor;
import by.tms.interceptor.PostInterceptor;
import by.tms.interceptor.PostLikeInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "by.tms")
public class WebConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/pages/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }






    @Bean
    public ComboPooledDataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/web-application-db?serverTimezone=UTC");
            dataSource.setUser("root");
            dataSource.setPassword("my76sql423ol28eg28a_");

            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "my76sql423ol28eg28a_");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "UTF8");
            dataSource.setProperties(properties);

            dataSource.setMaxStatements(180);
            dataSource.setMaxStatementsPerConnection(180);
            dataSource.setMinPoolSize(50);
            dataSource.setAcquireIncrement(10);
            dataSource.setMaxPoolSize(60);
            dataSource.setMaxIdleTime(30);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }






    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PostInterceptor())
                .addPathPatterns("/post/create");
        registry.addInterceptor(new HomeInterсeptor())
                .addPathPatterns("/home/reg")
                .addPathPatterns("/home/auth");
        registry.addInterceptor(new PostLikeInterceptor())
                .addPathPatterns("/post/set-like")
                .addPathPatterns("/post/like");
    }
}
