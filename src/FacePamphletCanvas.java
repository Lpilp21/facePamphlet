/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	String bottomText = "";
	GLabel profileNameLabel;
	double nameX = LEFT_MARGIN;
	double nameY = TOP_MARGIN;
	JScrollPane scrollPane;


	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {

	}

	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		bottomText = msg;
		GLabel label = new GLabel(bottomText);
		label.setFont(MESSAGE_FONT);
		double x = (getWidth() - label.getWidth())/2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(label, x, y);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		displayName(profile);
		displayImage(profile);
		displayStatus(profile);
		displayFriends(profile);

	}

	private void displayFriends(FacePamphletProfile profile) {
		GLabel headLabel = new GLabel("Friends:");
		headLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		double fX = nameX + IMAGE_WIDTH + IMAGE_MARGIN *3;
		double fY = 2* nameY;
		add(headLabel, fX, fY);
		Iterator<String> iter = profile.getFriends();
		int i = 0;
		while (iter.hasNext()){
			i++;
			GLabel friendLabel = new GLabel(iter.next());
			friendLabel.setFont(PROFILE_FRIEND_FONT);
			double x = fX;
			double y = fY + friendLabel.getAscent()*i*2;
			add(friendLabel, x, y);


		}

	}

	private void displayStatus(FacePamphletProfile profile) {
		String status = profile.getStatus();
		GLabel label = new GLabel(status);
		label.setFont(PROFILE_STATUS_FONT);
		double x = nameX;
		double y = nameY + profileNameLabel.getAscent() + IMAGE_MARGIN*2 + IMAGE_HEIGHT + STATUS_MARGIN;
		add(label, x, y);


	}

	private void displayImage(FacePamphletProfile profile) {
		double x = nameX;
		double y = nameY + profileNameLabel.getAscent() + IMAGE_MARGIN;
		if(profile.getImage() != null){
			GImage image = profile.getImage();
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, x, y);
		}
		else {
			GRect imageBox = new GRect(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(imageBox);
			GLabel label = new GLabel("No Image");
			label.setFont(PROFILE_IMAGE_FONT);
			double labelX = x + (IMAGE_WIDTH - label.getWidth())/2;
			double labelY = y + (IMAGE_HEIGHT - label.getAscent())/2;
			add(label, labelX, labelY);
		}
	}

	private void displayName(FacePamphletProfile profile) {
		String name = profile.getName();
		profileNameLabel = new GLabel(name);
		profileNameLabel.setFont(PROFILE_NAME_FONT);
		add(profileNameLabel, nameX, nameY);
	}


}
