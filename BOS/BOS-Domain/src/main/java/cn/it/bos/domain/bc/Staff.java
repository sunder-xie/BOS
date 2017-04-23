package cn.it.bos.domain.bc;

import java.util.HashSet;
import java.util.Set;


/**
 * 取派员
 */

public class Staff  implements java.io.Serializable {


    // Fields    

     private String id;		 		//	hbm生成的主键测试，默认生成的是 assigned ，需要自定义主键id。
     private Standard standard;	// 关联的收派标准
     private String name;			// 取派员姓名
     private String telephone;	// 取派员手机号码
     private String haspda;		// 取派员是否有一定设备
     private String deltag = "1";	// 逻辑定义的删除标记。"1" 为默认，有效。"0" 为删除，无效。
     private String station;		//位置坐标
     private Set decidedZones = new HashSet(0);  //取派区域


    // Constructors

    /** default constructor */
    public Staff() {
    }

	@Override
	public String toString() {
		return "Staff [id=" + id + ", standard=" + standard + ", name=" + name + ", telephone=" + telephone
				+ ", haspda=" + haspda + ", deltag=" + deltag + ", station=" + station + ", decidedZones="
				+ decidedZones + "]";
	}

	/** minimal constructor */
    public Staff(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public Staff(String id, Standard standard, String name, String telephone, String haspda, String deltag, String station, Set decidedZones) {
        this.id = id;
        this.standard = standard;
        this.name = name;
        this.telephone = telephone;
        this.haspda = haspda;
        this.deltag = deltag;
        this.station = station;
        this.decidedZones = decidedZones;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public Standard getStandard() {
        return this.standard;
    }
    
    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHaspda() {
        return this.haspda;
    }
    
    public void setHaspda(String haspda) {
        this.haspda = haspda;
    }

    public String getDeltag() {
        return this.deltag;
    }
    
    public void setDeltag(String deltag) {
        this.deltag = deltag;
    }

    public String getStation() {
        return this.station;
    }
    
    public void setStation(String station) {
        this.station = station;
    }

    public Set getDecidedZones() {
        return this.decidedZones;
    }
    
    public void setDecidedZones(Set decidedZones) {
        this.decidedZones = decidedZones;
    }
   








}