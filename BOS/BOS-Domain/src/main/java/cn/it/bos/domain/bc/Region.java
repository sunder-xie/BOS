package cn.it.bos.domain.bc;

import java.util.HashSet;
import java.util.Set;


/**
 * Region entity. @author MyEclipse Persistence Tools
 */

public class Region  implements java.io.Serializable {

	private String id;			//编号（委派主键）
	private String province;  //省份
	private String city; 			//	城市
	private String district;		// 区域
	private String postcode;	// 邮编
	private String shortcode;//  简码
	private String citycode;	//	城市编码					
	private Set subareas = new HashSet(0);	// 


     @Override
	public String toString() {
		return "Region [id=" + id + ", province=" + province + ", city=" + city + ", district=" + district
				+ ", postcode=" + postcode + ", shortcode=" + shortcode + ", citycode=" + citycode + ", subareas="
				+ subareas + "]";
	}



    // Constructors

    /** default constructor */
    public Region() {
    }

	/** minimal constructor */
    public Region(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public Region(String id, String province, String city, String district, String postcode, String shortcode, String citycode, Set subareas) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.district = district;
        this.postcode = postcode;
        this.shortcode = shortcode;
        this.citycode = citycode;
        this.subareas = subareas;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return this.province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return this.district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostcode() {
        return this.postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getShortcode() {
        return this.shortcode;
    }
    
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getCitycode() {
        return this.citycode;
    }
    
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public Set getSubareas() {
        return this.subareas;
    }
    
    public void setSubareas(Set subareas) {
        this.subareas = subareas;
    }
   

    public String getInfo() {
    	return province+"-"+city+"-"+district;
    }







}