package cn.it.bos.domain.bc;
// Generated 2016-12-21 22:50:21 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.qp.WorkBill;

/**
 * Staff generated by hbm2java
 */
@Entity
@Table(name = "bc_staff", catalog = "mavenbos")
public class Staff implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id; // hbm生成的主键测试，默认生成的是 assigned ，需要自定义主键id。
	private Standard standard; // 关联的收派标准
	private String name; // 取派员姓名
	private String telephone; // 取派员手机号码
	private String haspda; // 取派员是否有一定设备
	private String deltag = "1"; // 逻辑定义的删除标记。"1" 为默认，有效。"0" 为删除，无效。
	private String station;
	private Set<NoticeBill> noticeBills = new HashSet<NoticeBill>(0);
	private Set<WorkBill> workBills = new HashSet<WorkBill>(0);
	private Set<DecidedZone> decidedZones = new HashSet<DecidedZone>(0);

	public Staff() {
	}

	public Staff(String id) {
		this.id = id;
	}

	public Staff(String id, Standard standard, String name, String telephone, String haspda, String deltag,
			String station, Set<NoticeBill> noticeBills, Set<WorkBill> workBills, Set<DecidedZone> decidedZones) {
		this.id = id;
		this.standard = standard;
		this.name = name;
		this.telephone = telephone;
		this.haspda = haspda;
		this.deltag = deltag;
		this.station = station;
		this.noticeBills = noticeBills;
		this.workBills = workBills;
		this.decidedZones = decidedZones;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "standard_id")
	public Standard getStandard() {
		return this.standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "telephone", length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "haspda", length = 1)
	public String getHaspda() {
		return this.haspda;
	}

	public void setHaspda(String haspda) {
		this.haspda = haspda;
	}

	@Column(name = "deltag", length = 1)
	public String getDeltag() {
		return this.deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	@Column(name = "station", length = 40)
	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	@JSON(serialize=false)
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "staff")
	public Set<NoticeBill> getNoticeBills() {
		return this.noticeBills;
	}

	public void setNoticeBills(Set<NoticeBill> noticeBills) {
		this.noticeBills = noticeBills;
	}
	@JSON(serialize=false)
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "staff")
	public Set<WorkBill> getWorkBills() {
		return this.workBills;
	}

	public void setWorkBills(Set<WorkBill> workBills) {
		this.workBills = workBills;
	}
	@JSON(serialize=false)
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "staff")
	public Set<DecidedZone> getDecidedZones() {
		return this.decidedZones;
	}

	public void setDecidedZones(Set<DecidedZone> decidedZones) {
		this.decidedZones = decidedZones;
	}

}
