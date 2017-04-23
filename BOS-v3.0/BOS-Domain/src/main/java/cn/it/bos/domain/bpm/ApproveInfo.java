package cn.it.bos.domain.bpm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import cn.it.bos.domain.user.User;

/**
 * 审批实体
 *
 */

@Entity
@Table(name = "t_approveinfo", catalog = "mavenbos")
public class ApproveInfo implements java.io.Serializable{
	private Integer id;
	private User approver;//审批人
	private Date approveDate;//审批时间
	private Boolean approval;//是否通过
	private String comment;//审批意见
	private Application application;//当前审批对应的申请实体
	
	
	@GenericGenerator(name = "generator", strategy = "native")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	public User getApprover() {
		return approver;
	}
	public void setApprover(User approver) {
		this.approver = approver;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approveDate", length = 19)
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	@Column(name = "approval")
	public Boolean getApproval() {
		return approval;
	}
	public void setApproval(Boolean approval) {
		this.approval = approval;
	}
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="application_id")
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	
}
