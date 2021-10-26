package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomUserData {
	public static String[] userNameAndEmail() {
		String nameString = "user" + String.valueOf(System.currentTimeMillis()).substring(6);
		String email = nameString + "@ballu.com";
		String updatedEmail = "updated" + email;
		String[] array = {nameString, email, updatedEmail};
		return array;
	}
	
	public static String gender() {
		List<String> list = Arrays.asList("male", "female");
		Random rand = new Random();
		String genderString = list.get(rand.nextInt(list.size())) ;
		return genderString;
	}
	
	public static String status() {
		List<String> list = Arrays.asList("active","inactive") ;
		Random rand = new Random();
		String status = list.get(rand.nextInt(list.size()));
		return status;
	}
}
