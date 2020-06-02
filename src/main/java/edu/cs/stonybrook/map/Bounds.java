package edu.cs.stonybrook.map;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Bounds {
	
	private int id;
	private double lat;
	private double lng;
	private String geojson;

	public Bounds() {
		
	}
	
	
	public Bounds(double lat, double lng, String geojson) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.geojson = geojson;
	}
	
	@Id
	@Column(name="BOUNDS_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="LATITUDE")
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	@Column(name="LONGITUDE")
	public double getLng() {
		return lng;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	@Column(name="GEOJSON")
	public String getGeojson() {
		return geojson;
	}
	
	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}	
	
}
