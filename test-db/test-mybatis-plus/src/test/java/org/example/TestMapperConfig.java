package org.example;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.MppSqlInjector;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @date 2022/2/27 14:01
 */
// @SpringBootTest
// @RunWith(SpringRunner.class)
// @SpringBootConfiguration
public class TestMapperConfig {

    @Autowired
    DataSource dataSource;

    @Test
    public void testConfig() throws Exception{
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        dataSource = new DriverManagerDataSource("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "");

        sqlSessionFactoryBean.setDataSource(dataSource);
        // sqlSessionFactoryBean.setTypeAliasesPackage(this.getTypeAliasesPackage());
        // sqlSessionFactoryBean.setPlugins(this.getPlugins());
        // if(mapperLocations() != null) {
        String mapperLocations = "classpath*:/mapper/**/*.xml";
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        // }


        MybatisConfiguration configuration = new MybatisConfiguration();
        GlobalConfigUtils.getGlobalConfig(configuration).setSqlInjector(new MppSqlInjector());
        sqlSessionFactoryBean.setConfiguration(configuration);

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.getConfiguration().setLogImpl(Slf4jImpl.class);

    }
}
