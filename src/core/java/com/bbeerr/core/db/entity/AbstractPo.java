package com.bbeerr.core.db.entity;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;

import com.bbeerr.web.security.Passport;

@SuppressWarnings("serial")
public abstract class AbstractPo implements java.io.Serializable {

	public abstract void setCreateusername(String createusername);

	public abstract void setCreatedatetime(Date createdatetime);

	public abstract void setUpdateusername(String updateusername);

	public abstract void setUpdatedatetime(Date updatedatetime);

	public void setDefaultValue() {
		Passport passport = getPassport();
		if (passport == null) {
			setCreateusername("annoymous");
			setUpdateusername("annoymous");
			Date now = new Date();
			setCreatedatetime(now);
			setUpdatedatetime(now);
		} else {
			setCreateusername(passport.getUser().getUsername());
			setUpdateusername(passport.getUser().getUsername());
			Date now = new Date();
			setCreatedatetime(now);
			setUpdatedatetime(now);
		}
	}

	public static Passport getPassport() {
		Passport passport = null;
		try {
			passport = (Passport) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception ex) {

		}
		return passport;
	}

}