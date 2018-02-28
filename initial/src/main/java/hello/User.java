package hello;

public class User {
    private long id;
    private String userName, accountCreationDate;

    public static String SqlUserCreateTable(){
        return "CREATE TABLE users(" +
                "id SERIAL," + 
                "user_name VARCHAR(255)," +
                 "account_creation_date VARCHAR(255));";
    } 

    public User(long id, String userName, String accountCreationDate) {
        this.id = id;
        this.userName = userName;
        this.accountCreationDate = accountCreationDate;
    }

    public User() {
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, userName='%s', accountCreationDate='%s']",
                id, userName, accountCreationDate);
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setAccountCreationDate(String accountCreationDate){
        this.accountCreationDate = accountCreationDate;
    }
    public void setId(long id){
        this.id = id;
    }

}