package br.com.gustavoalmeidacarvalho.operariosapi.api.model.notification;

import java.util.Map;

public record NotificationMessage(String title, String body, Map<String, String> data) {

}
