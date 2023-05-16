package org.lightnsalt.hikingdom.chat.dto.response.message;

import org.lightnsalt.hikingdom.chat.dto.MessageType;

import lombok.Data;

@Data
public class MessageRes {
	private MessageType type;

	public MessageRes(MessageType type) {
		this.type = type;
	}
}
