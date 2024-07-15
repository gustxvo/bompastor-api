package com.gustxvo.bompastor_api.api.model.user;

import com.gustxvo.bompastor_api.domain.model.user.User;

import java.util.List;

public record WorkerNames(List<String> workers) {

    public static WorkerNames fromUserList(List<User> users) {
        return new WorkerNames(users.stream().map(User::getName).toList());
    }
}
