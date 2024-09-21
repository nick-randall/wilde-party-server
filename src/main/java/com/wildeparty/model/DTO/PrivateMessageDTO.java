package com.wildeparty.model.DTO;

import com.wildeparty.model.User;

public record PrivateMessageDTO(
    PrivateMessageType type,
    String message) {
}
