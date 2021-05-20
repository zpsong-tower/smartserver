package com.tower.smartservice.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 申请记录表 (TB_APPLY)
 * 申请添加群，申请添加好友等用户间的申请记录
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/20 10:24
 */
@Entity
@Table(name = "TB_APPLY")
public class ApplyEntity {
	/**
	 * 申请添加好友
	 */
	public static final int TYPE_ADD_USER = 1;

	/**
	 * 申请加入群
	 */
	public static final int TYPE_ADD_GROUP = 2;

	// Id
	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(updatable = false, nullable = false)
	private String id;

	// 申请理由
	@Column(nullable = false)
	private String description;

	// 附件
	@Column(columnDefinition = "TEXT")
	private String attachment;

	// 当前申请的类型
	@Column(nullable = false)
	private int type;

	// 申请人
	// 可能为系统消息，所以可以为空
	@ManyToOne
	@JoinColumn(name = "applicantId")
	private UserEntity applicant;
	@Column(updatable = false, insertable = false)
	private String applicantId;

	// 目标Id
	// 有可能为用户Id或群组Id，不建立主外键关系强关联
	@Column(nullable = false)
	private String targetId;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public UserEntity getApplicant() {
		return applicant;
	}

	public void setApplicant(UserEntity applicant) {
		this.applicant = applicant;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
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
