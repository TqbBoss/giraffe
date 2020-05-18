package giraffe.auth.db.domains;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * cloud_users
 * @author 
 */
@Data
public class CloudUsers implements Serializable {
    private Long id;

    private String name;

    private String password;

    private Date createAt;

    private Date updateAt;

    private Integer roleId;

    private Integer groupId;

    private static final long serialVersionUID = 1L;
}