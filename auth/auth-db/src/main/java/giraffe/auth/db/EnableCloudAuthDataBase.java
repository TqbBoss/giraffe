package giraffe.auth.db;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 通过{@code @EnableVigorousOrmModule}启动Orm框架
 * @author 田庆斌
 * @version 0.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GiraffeAuthDbConfiguration.class)
public @interface EnableCloudAuthDataBase {
}
