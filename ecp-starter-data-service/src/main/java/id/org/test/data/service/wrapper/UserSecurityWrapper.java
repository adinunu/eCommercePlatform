package id.org.test.data.service.wrapper;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserSecurityWrapper {
    private String username;
    private String password;
    private Boolean enabled;
    private List<String> authorities = new ArrayList<>();

    public UserSecurityWrapper() {
    }

    public void addAuthority(String authority) {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        authorities.add(authority);
    }
}
