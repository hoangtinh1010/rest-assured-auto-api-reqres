package models;

public class UserData {
    private String name;
    private String job;
    private int expectedStatusCode;

    public UserData(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getExpectedStatusCode() {
        return expectedStatusCode;
    }
    public void setExpectedStatusCode(int expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", expectedStatusCode=" + expectedStatusCode +
                '}';
    }
}
