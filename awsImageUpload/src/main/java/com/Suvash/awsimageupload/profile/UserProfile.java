package com.Suvash.awsimageupload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
	private final UUID userProfileId;
	private final String username;
	private String userProfileImageLink; // S3 Key
	
	public UserProfile(UUID userProfileId, String username, String userProfileImageLink) {
		super();
		this.userProfileId = userProfileId;
		this.username = username;
		this.userProfileImageLink = userProfileImageLink;
	}
	
	
	
	public UUID getUserProfileId() {
		return userProfileId;
	}

	public String getUsername() {
		return username;
	}

	public Optional<String> getUserProfileImageLink() {
		return Optional.ofNullable(userProfileImageLink);
	}



	public void setUserProfileImageLink(String userProfileImageLink) {
		this.userProfileImageLink = userProfileImageLink;
	}



	@Override
	public String toString() {
		return "UserProfile [username=" + username + ", userProfileId=" + userProfileId + ", userProfileImageLink="
				+ userProfileImageLink + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(userProfileId, userProfileImageLink, username);
	}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userProfileId, that.userProfileId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(userProfileImageLink, that.userProfileImageLink);
	}
	
	

}
