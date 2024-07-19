package com.gustxvo.bompastor_api.api.model.notification;

import java.util.Map;

public record NotificationMessage(String title, String body, Map<String, String> data) {

}
