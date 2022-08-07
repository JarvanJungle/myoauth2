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
public class Authority {
	private String featureCode;
	private Action actions;
}
