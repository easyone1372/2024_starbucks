package com.example.starbucks.status;

public enum ResponseStatus {
    SUCCESS(200, "Success"),
    ERROR(500,"Error"),
    NOT_FOUND(404,"Not Found"),
    UNAUTHORIZED(401,"Unauthorized"),
    FAIL(400,"Fail");

    private int code;
    private String description;

    ResponseStatus(int code, String description){
        this.code=code;
        this.description=description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
