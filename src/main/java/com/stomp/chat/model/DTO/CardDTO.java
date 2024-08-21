package com.stomp.chat.model.DTO;

import com.stomp.chat.model.cards.CardType;

public record CardDTO(Long id, String name, String image, CardType cardType) {

}
