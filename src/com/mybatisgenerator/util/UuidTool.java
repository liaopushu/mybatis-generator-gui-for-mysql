package com.mybatisgenerator.util;

import java.util.UUID;

public class UuidTool {
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
