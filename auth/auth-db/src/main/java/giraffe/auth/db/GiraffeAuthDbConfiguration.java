package giraffe.auth.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * springboot集成配置，该配置定义了项目所需的：
 * {@code myBatisDataSource}
 * {@code myBatisTransactionManager}
 * {@code myBatisSqlSessionFactory}
 * @author 田庆斌
 * @since 0.0.1
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"giraffe.auth.db.services"})
@MapperScan("giraffe.auth.db.mappers")
public class GiraffeAuthDbConfiguration {
    @Bean(name = "giraffeCloudDataSource")
    @ConfigurationProperties(prefix = "giraffe.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .type(BasicDataSource.class)
                .build();
    }

    @Bean("giraffeCloudTransactionManager")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("giraffeCloudDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("giraffeCloudSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("giraffeCloudDataSource") DataSource dataSource) throws Exception{
        Resource resource = new ClassPathResource("mybatis-config.xml");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(resource);
        return sqlSessionFactoryBean.getObject();
    }
}

