package com.wildeparty.model.DTO;

import com.wildeparty.User;

public record PrivateMessageDTO(
    MessageType type,
    User inviter,
    String subscriptionId,
    String message) {
}

enum MessageType {
  INVITE,
  ERROR_SUBSCRIBING,
}
