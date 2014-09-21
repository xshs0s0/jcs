package com.s0s0.app.ui;

public class JcsContext {
	private String profilefilepath=null;
	private SearchProfile profile=null;
	
	public JcsContext()
	{
	}

	public String getProfilefilepath() {
		return profilefilepath;
	}

	public void setProfilefilepath(String profilefilepath) {
		this.profilefilepath = profilefilepath;
	}

	public SearchProfile getProfile() {
		return profile;
	}

	public void setProfile(SearchProfile profile) {
		this.profile = profile;
	}
}
