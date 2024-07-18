package com.gustxvo.bompastor_api.api.model;

import java.util.List;

public record NotificationMessage(List<String> tokens, String title, String body) {

}
