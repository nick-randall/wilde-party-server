package com.wildeparty.model.DTO;

import com.wildeparty.User;

public record PrivateMessageDTO(
    PrivateMessageType type,
    User inviter,
    String subscriptionId,
    String message) {
}
