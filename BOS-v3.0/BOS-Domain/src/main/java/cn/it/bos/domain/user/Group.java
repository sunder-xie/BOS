package cn.it.bos.domain.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="group"
    ,catalog="mavenbos"
)
public class Group implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 主键uuid
	private String name;// 组名
	private String type;// 组类型
	private Set<User> users = new HashSet<User>(0);

	

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_group", catalog="mavenbos", joinColumns = { 
        @JoinColumn(name="group_id", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="user_id", nullable=false, updatable=false) })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}


	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", type=" + type + ", users=" + users + "]";
	}

}