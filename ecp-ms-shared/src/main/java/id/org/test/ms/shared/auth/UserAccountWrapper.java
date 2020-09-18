package id.org.test.ms.shared.auth;

public class UserAccountWrapper {
    private Long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private Integer accountAge;
    private String accountSex;
    private String accountAddress;
    private String accountContactNo;
    private String accountEmail;

    private String userUsername;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAccountAge() {
        return accountAge;
    }

    public void setAccountAge(Integer accountAge) {
        this.accountAge = accountAge;
    }

    public String getAccountSex() {
        return accountSex;
    }

    public void setAccountSex(String accountSex) {
        this.accountSex = accountSex;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getAccountContactNo() {
        return accountContactNo;
    }

    public void setAccountContactNo(String accountContactNo) {
        this.accountContactNo = accountContactNo;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}
