package cn.it.bos.domain.bc;

import java.util.HashSet;
import java.util.Set;

/**
 * DecidedZone entity. @author MyEclipse Persistence Tools
 */

public class DecidedZone implements java.io.Serializable {

	// Fields

	private String id;    //主键id，
	private Staff staff; // 关联的取派员
	private String name;// 区域名称
	private Set subareas = new HashSet(0); // 管理分区


	public DecidedZone() {
	}

	public DecidedZone(String id) {
		this.id = id;
	}

	public DecidedZone(String id, Staff staff, String name, Set subareas) {
		this.id = id;
		this.staff = staff;
		this.name = name;
		this.subareas = subareas;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getSubareas() {
		return this.subareas;
	}

	public void setSubareas(Set subareas) {
		this.subareas = subareas;
	}

	@Override
	public String toString() {
		return "DecidedZone [id=" + id + ", staff=" + staff + ", name=" + name + ", subareas=" + subareas + "]";
	}

}