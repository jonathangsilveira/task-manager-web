package edu.jonathangs.practice.taskmanager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public final class Converters {

	private Converters() {
		
	}
	
	public static Timestamp toTimestamp(Date date) {
		return Objects.nonNull(date) ? new Timestamp(date.getTime()) : null;
	}
	
	public static int toBinary(boolean aBoolean) {
		return aBoolean ? 1 : 0; 
	}
	
}
