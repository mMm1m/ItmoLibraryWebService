package com.example.bookCommunity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
//@Table(name = "Messages")
@AllArgsConstructor
public class Message {
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private long message_id;
	//@Column(name = "Sender")
	private String from = null;
	//@Column(name = "Recipient")
	private String to = null;
	//@Column(name = "Text")
	private String text = null;
	
}
