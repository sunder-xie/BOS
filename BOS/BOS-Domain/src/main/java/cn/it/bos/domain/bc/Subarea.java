package cn.it.bos.domain.bc;

/**
 * Subarea entity. @author MyEclipse Persistence Tools
 */

public class Subarea implements java.io.Serializable {

	// Fields

	private String id;    //  主键id
	private DecidedZone decidedZone; // 定区编码
	private Region region; // 管理分区
	private String addresskey; //  关键字
	private String startnum;//  起始号
	private String endnum;//   结束号
	private String single;//      单双号
	private String position;//  位置坐标

	public Subarea() {
	}

	public Subarea(String id) {
		this.id = id;
	}

	public Subarea(String id, DecidedZone decidedZone, Region region, String addresskey, String startnum, String endnum,
			String single, String position) {
		this.id = id;
		this.decidedZone = decidedZone;
		this.region = region;
		this.addresskey = addresskey;
		this.startnum = startnum;
		this.endnum = endnum;
		this.single = single;
		this.position = position;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DecidedZone getDecidedZone() {
		return this.decidedZone;
	}

	public void setDecidedZone(DecidedZone decidedZone) {
		this.decidedZone = decidedZone;
	}

	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getAddresskey() {
		return this.addresskey;
	}

	public void setAddresskey(String addresskey) {
		this.addresskey = addresskey;
	}

	public String getStartnum() {
		return this.startnum;
	}

	public void setStartnum(String startnum) {
		this.startnum = startnum;
	}

	public String getEndnum() {
		return this.endnum;
	}

	public void setEndnum(String endnum) {
		this.endnum = endnum;
	}

	public String getSingle() {
		return this.single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	public String getSid() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Subarea [id=" + id + ", decidedZone=" + decidedZone + ", region=" + region + ", addresskey="
				+ addresskey + ", startnum=" + startnum + ", endnum=" + endnum + ", single=" + single + ", position="
				+ position + "]";
	}

}