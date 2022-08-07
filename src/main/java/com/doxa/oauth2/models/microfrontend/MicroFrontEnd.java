package com.doxa.oauth2.models.microfrontend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "micro_front_end")
public class MicroFrontEnd {
	@Id
	private String id;
	private String name;
	private String code;
	private String environment;
	private String host;
}
