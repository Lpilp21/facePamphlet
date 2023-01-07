/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the
	 * interactors in the application, and taking care of any other
	 * initialization that needs to be performed.
	 */
	JLabel nameLabel;
	JTextField nameFeild;
	JButton addBtn;
	JButton deleteBtn;
	JButton lookupBtn;

	JTextField statusField;
	JButton statusBtn;
	JTextField pictureField;
	JButton pictureBtn;
	JTextField addFriendField;
	JButton addFriendBtn;
	FacePamphletProfile profile;
	FacePamphletDatabase dataBase = new FacePamphletDatabase();
	FacePamphletCanvas canvas;



	public void init() {

		canvas  = new FacePamphletCanvas();
		setup();
		add(canvas);
		addActionListeners();
		statusField.addActionListener(this);
		pictureField.addActionListener(this);
		addFriendField.addActionListener(this);



	}


	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked or interactors are used, so you will have to add code
	 * to respond to these actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addBtn){
			add();
		}
		if (e.getSource() == deleteBtn) {
			delete();
		}
		if (e.getSource() == lookupBtn) {
			lookUp();
		}

		if (e.getSource() == statusBtn || e.getSource() == statusField) {
			statusUpdate();


		}
		if (e.getSource() == pictureBtn || e.getSource() == pictureField) {
			pictureUpdate();



		}
		if (e.getSource() == addFriendBtn || e.getSource() == addFriendField) {
			addFriend();

		}
	}
	private void setup(){
		nameLabel = new JLabel("Name");
		add(nameLabel, NORTH);
		nameFeild = new JTextField(TEXT_FIELD_SIZE);
		add(nameFeild, NORTH);
		addBtn = new JButton("Add");
		add(addBtn, NORTH);
		deleteBtn = new JButton("Delete");
		add(deleteBtn, NORTH);
		lookupBtn = new JButton("Look Up");
		add(lookupBtn, NORTH);

		statusField = new JTextField(TEXT_FIELD_SIZE);
		add(statusField, WEST);
		statusBtn = new JButton("Change Status");
		add(statusBtn, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		pictureField = new JTextField(TEXT_FIELD_SIZE);
		add(pictureField, WEST);
		pictureBtn = new JButton("Change Picture");
		add(pictureBtn, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		add(addFriendField, WEST);
		addFriendBtn = new JButton("Add Friend");
		add(addFriendBtn, WEST);
	}
	private void add(){
		String name = nameFeild.getText();
		if (dataBase.containsProfile(name)){
			profile = dataBase.getProfile(name);
			canvas.displayProfile(profile);
			canvas.showMessage("A Profile with the name " + name  +" already exists");

		}
		else {
			profile = new FacePamphletProfile(name);
			dataBase.addProfile(profile);
			canvas.displayProfile(profile);
			canvas.showMessage("New profile created");
			dataBase.updateFile();
		}
		nameFeild.setText(EMPTY_LABEL_TEXT);


	}
	private void delete(){
		String name = nameFeild.getText();
		if (dataBase.containsProfile(name)){
			dataBase.deleteProfile(name);
			canvas.removeAll();
			canvas.showMessage("Profile of " + name + " deleted");
			dataBase.updateFile();
		}
		else{
			canvas.displayProfile(profile);
			canvas.showMessage("A profile with name " + name + " does not exist");
		}
		nameFeild.setText(EMPTY_LABEL_TEXT);
	}
	private void lookUp(){
		String name = nameFeild.getText();
		if (dataBase.containsProfile(name)){
			profile = dataBase.getProfile(name);
			canvas.displayProfile(profile);
			canvas.showMessage("Displaying "+ name);
			System.out.println(profile.toString());
		}
		else {
			canvas.removeAll();
			canvas.showMessage("A profile with name " + name + " does not exist");

		}
		nameFeild.setText(EMPTY_LABEL_TEXT);

	}
	private void statusUpdate(){
		if (profile != null){
			String status = statusField.getText();
			profile.setStatus(status);
			canvas.displayProfile(profile);
			canvas.showMessage("status updated to " + profile.getStatus());
			statusField.setText(EMPTY_LABEL_TEXT);
			dataBase.updateFile();
		}
		else {
			canvas.showMessage("Please select a profile to change status");

		}

	}
	private void pictureUpdate(){
		String fileName = pictureField.getText();
		GImage image = null;
		try {
			image = new GImage(fileName);

		} catch (ErrorException ex) {
			canvas.displayProfile(profile);
			canvas.showMessage("Unable to open image file: " + fileName);

		}
		if (profile != null && image!= null) {
			profile.setImage(image);
			profile.imageFIleName = fileName;
			canvas.displayProfile(profile);
			canvas.showMessage("Picture updated");
			dataBase.updateFile();
			pictureField.setText("");

		}else if(profile == null){
			canvas.showMessage("Please select a profile to change picture");
		}

	}
	private void addFriend(){
		String friendName = addFriendField.getText();
		if(profile!= null && !profile.getName().equals(friendName) && dataBase.containsProfile(friendName)){
			if (dataBase.containsProfile(friendName)){
				dataBase.getProfile(friendName).addFriend(profile.getName());
				profile.addFriend(friendName);
				canvas.displayProfile(profile);
				canvas.showMessage(friendName + " added as a friend");
				addFriendField.setText(EMPTY_LABEL_TEXT);
				dataBase.updateFile();
			}
			else{
				canvas.showMessage(profile.getName() + " already has "  + friendName +" as a friend");

			}
		}
		else if(profile.getName().equals(friendName)){
			canvas.displayProfile(profile);
			canvas.showMessage("You can not add yourself as a friend ://");

		}
		else if(profile == null){
			canvas.removeAll();
			canvas.showMessage("Please select a profile to add friend");
		}
		else if(!dataBase.containsProfile(friendName)){
			canvas.displayProfile(profile);
			canvas.showMessage("There is no account associated with this name");
		}
	}

}
