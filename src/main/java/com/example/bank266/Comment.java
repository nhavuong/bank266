package com.example.bank266;

public class Comment {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String removeJS() {
        message = message.replaceAll("<script>.*</script>", "");
        return message;
    }
}
