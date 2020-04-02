package giraffe.cloud.framework.config.utf8;

import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PropertiesHandle implements PropertySourceLoader {
    @Override
    public String[] getFileExtensions() {
        return new String[]{ "properties", "xml" };
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        Map<String, ?> properties = loadProperties(resource);
        if (properties.isEmpty()){
            return Collections.emptyList();
        }
        return Collections.singletonList(new OriginTrackedMapPropertySource(name, properties));
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    private Map<String, ?> loadProperties(Resource resource) throws IOException{
        String fileName = resource.getFilename();
        if (!StringUtils.isEmpty(fileName) && fileName.endsWith(".xml")){
            return (Map) PropertiesLoaderUtils.loadProperties(resource);
        }
        return new PropertiesLoader(resource).load();
    }
}
