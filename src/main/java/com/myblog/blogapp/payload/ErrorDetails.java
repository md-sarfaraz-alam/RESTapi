package com.myblog.blogapp.payload;

import java.util.Date;

public class ErrorDetails {

    private Date time_Stamp;
    private String message;
    private String details;

    public ErrorDetails(Date time_Stamp, String message, String details) {
        this.time_Stamp = time_Stamp;
        this.message = message;
        this.details = details;
    }

    public Date getTime_Stamp() {
        return time_Stamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
