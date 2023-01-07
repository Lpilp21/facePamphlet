/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import acm.graphics.GImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {
	private BufferedReader rd = null;

	HashMap<String, FacePamphletProfile> dataBase = new HashMap<>();
	ArrayList<FacePamphletProfile> profiles = new ArrayList<>();

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		readFile();
	}
	public void updateFile(){
		try {
			profiles.clear();
			for (FacePamphletProfile value : dataBase.values()) {
				profiles.add(value);
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\levani\\Desktop\\extension  facepamphlet\\src\\recources\\data.txt", true));
			for (int i = 0; i < profiles.size(); i++) {
				writer.newLine();
				writer.write(profiles.get(i).toString()+"\n");
				System.out.println(profiles.get(i).toString()+"\n");
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void readFile(){
		try {
			rd = new BufferedReader(new FileReader("C:\\Users\\levani\\Desktop\\extension  facepamphlet\\src\\recources\\data.txt"));
		} catch (IOException ex) {
			System.out.println("File not found!!!");
		}

		try {
			String line = rd.readLine();
			while(line != null) {
				dataBase.put(lineToProfile(line).getName(), lineToProfile(line));
			}
			rd.close();
		} catch (IOException ex) {
		}
	}

	private FacePamphletProfile lineToProfile(String line) {
		String[] lines = line.split("||");
		String[] data = lines[0].split("|%$");
		String[] friends = lines[1].split("|%$");
		FacePamphletProfile profile = new FacePamphletProfile(data[0]);
		profile.setStatus(data[1]);
		profile.setImage(new GImage(data[2]));
		for (int i = 0; i < friends.length; i++) {
			profile.addFriend(friends[i]);
		}
		return profile;
	}


	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		dataBase.put(profile.getName(), profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		return dataBase.get(name);
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		Iterator<String> iter = getProfile(name).getFriends();
		while (iter.hasNext()){
			getProfile(iter.next()).removeFriend(name);
		}
		dataBase.remove(name);

	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		return dataBase.containsKey(name);
	}

}
