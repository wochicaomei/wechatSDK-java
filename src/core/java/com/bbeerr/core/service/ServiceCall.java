package com.bbeerr.core.service;

@SuppressWarnings({ "serial" })
public class ServiceCall implements java.io.Serializable {

	protected String uri;
	protected String message;
	protected String ref;
	protected Object data;
	protected Object page;

	public ServiceCall(String ref, Object data) {
		this.ref = ref;
		this.data = data;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Object getPage() {
		return page;
	}

	public void setPage(Object page) {
		this.page = page;
	}

}