package hello;

public class Greeting {


    private long id;
    private String content;

    public Greeting() {
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}