package com.doxa.oauth2.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author vuducnoi
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Action {
	private boolean read;
	private boolean write;
	private boolean approve;
}
