package giraffe.auth.db;

import giraffe.auth.db.domains.CloudUsers;
import giraffe.auth.db.mappers.CloudUsersDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * 单元测试
 * 生产环境下通过{@code @EnableVigorousOrm}注解来集成到SpringBoot环境，
 * 本模块本身并不包含spring context，所以不需要{@code @SpringBootTest}注解测试类
 * 测试使用JUnit5来进行测试开发,{@code init_factory}为初始化方法新建{@code SqlSessionFactory}工厂类
 * 配置文件为<strong>mybatis-config-config-test.xml</strong>
 * @author 田庆斌
 * @version 0.0.1
 */
@DisplayName("vigorous-orm junit5:")
class VigorousOrmApplication {
    private static SqlSessionFactory sqlSessionFactory = null;

    @BeforeAll
    static void init_factory(TestReporter testReporter) {
        try{
            String resource = "mybatis-config-test.xml";
            InputStream is = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            testReporter.publishEntry("mybatis会话工厂初始化成功");
        }
        catch (IOException ex){
            Assertions.fail(ex.getMessage());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class UserDao_class{
        @Test
        void selectByPrimaryKey_ok(){
            try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
                CloudUsersDao userDao = sqlSession.getMapper(CloudUsersDao.class);
                CloudUsers user = userDao.selectByPrimaryKey(1L);
                Assertions.assertEquals(user.getId(), 1);
            }
        }
    }
}
