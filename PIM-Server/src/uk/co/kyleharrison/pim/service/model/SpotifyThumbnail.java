package uk.co.kyleharrison.pim.service.model;

public class SpotifyThumbnail {

	String provider_url;
	Double version;
	int thumbnail_width;
	int height;
	int thumbnail_height;
	String title;
	int width;
	String thumbnail_url;
	String provider_name;
	String type;
	String html;
	
	public SpotifyThumbnail() {
		super();
	}
	
	public SpotifyThumbnail(String provider_url, Double version,
			int thumbnail_width, int height, int thumbnail_height,
			String title, int width, String thumbnail_url,
			String provider_name, String type, String html) {
		super();
		this.provider_url = provider_url;
		this.version = version;
		this.thumbnail_width = thumbnail_width;
		this.height = height;
		this.thumbnail_height = thumbnail_height;
		this.title = title;
		this.width = width;
		this.thumbnail_url = thumbnail_url;
		this.provider_name = provider_name;
		this.type = type;
		this.html = html;
	}
	
	public String getProvider_url() {
		return provider_url;
	}
	public void setProvider_url(String provider_url) {
		this.provider_url = provider_url;
	}
	public Double getVersion() {
		return version;
	}
	public void setVersion(Double version) {
		this.version = version;
	}
	public int getThumbnail_width() {
		return thumbnail_width;
	}
	public void setThumbnail_width(int thumbnail_width) {
		this.thumbnail_width = thumbnail_width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getThumbnail_height() {
		return thumbnail_height;
	}
	public void setThumbnail_height(int thumbnail_height) {
		this.thumbnail_height = thumbnail_height;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getThumbnail_url() {
		return thumbnail_url;
	}
	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
	public String getProvider_name() {
		return provider_name;
	}
	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
}
