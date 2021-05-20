package com.tower.smartservice.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 群组表 (TB_GROUP)
 * 群组信息及其所有者
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/19 18:13
 */
@Entity
@Table(name = "TB_GROUP")
public class GroupEntity {
	// Id
	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(updatable = false, nullable = false)
	private String id;

	// 群名称
	@Column(nullable = false)
	private String name;

	// 群描述
	@Column(nullable = false)
	private String description;

	// 群头像
	@Column(nullable = false)
	private String portrait;

	// 群主 / Id
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "ownerId")
	private UserEntity owner;
	@Column(nullable = false, updatable = false, insertable = false)
	private String ownerId;

	// 创建时间
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createAt = LocalDateTime.now();

	// 更新时间
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updateAt = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
}
