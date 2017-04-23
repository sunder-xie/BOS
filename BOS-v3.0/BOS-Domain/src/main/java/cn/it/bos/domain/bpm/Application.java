package cn.it.bos.domain.bpm;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import cn.it.bos.domain.user.User;

/**
 * 申请实体
 *
 */
@Entity
@Table(name = "t_application", catalog = "mavenbos")
public class Application implements java.io.Serializable {
	private Integer id;
	private String title;//申请的标题 格式：{模板名称}_{姓名}_{日期}
	private User applicant;//申请人
	private Date applyDate;//申请日期
	private String status;//申请状态	
	private String docFilePath;//doc文件路径
	private Set<ApproveInfo> approveInfos;//当前申请对应的多个审批实体
	private Template template;//当前申请使用的模板对象
	
	public static final String STATUS_APPROVING = "审批中";
	public static final String STATUS_UNAPPROVED = "未通过";
	public static final String STATUS_APPROVED = "已通过";
	
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
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@ManyToOne(targetEntity=cn.it.bos.domain.user.User.class,fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	public User getApplicant() {
		return applicant;
	}
	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "applyDate", length = 19)
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "docFilePath")
	public String getDocFilePath() {
		return docFilePath;
	}
	public void setDocFilePath(String docFilePath) {
		this.docFilePath = docFilePath;
	}
	@OneToMany(orphanRemoval=true)
	@JoinColumn(name="application_id")
	public Set<ApproveInfo> getApproveInfos() {
		return approveInfos;
	}
	public void setApproveInfos(Set<ApproveInfo> approveInfos) {
		this.approveInfos = approveInfos;
	}
	@ManyToOne(targetEntity=cn.it.bos.domain.bpm.Template.class,fetch=FetchType.LAZY)
    @JoinColumn(name="template_id")
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	
}
