package com.wildeparty.model.DTO;

import com.wildeparty.model.cards.CardType;

public record CardDTO(Long id, String name, String image, CardType cardType) {

}
