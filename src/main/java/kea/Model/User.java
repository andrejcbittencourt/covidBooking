package kea.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {

    @Id
    private Long UserID;
    private String Username;
    private String Password;
    private String FirstName;
    private String LastName;

}
