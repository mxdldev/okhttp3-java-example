package com.mxdl.okhttp3_example_01.bean;

public class AddUserResponse extends BaseResponse {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AddUserResponse{" +
                "user=" + user +
                '}';
    }
}
