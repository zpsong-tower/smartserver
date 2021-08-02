package com.tower.smartservice.bean.response;

import com.tower.smartservice.bean.db.ApplyEntity;

import com.google.gson.annotations.Expose;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

/**
 * 申请Card
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/2 20:46
 */
public class ApplyCard {
	// Id
	@Expose
	private String id;

	// 申请理由
	@Expose
	private String description;

	// 附件
	@Expose
	private String attachment;

	// 当前申请的类型
	@Expose
	private int type;

	// 申请人
	@Expose
	private String applicantId;

	// 目标Id
	@Expose
	private String targetId;

	// 创建时间
	@Expose
	private LocalDateTime createAt;

	public ApplyCard(@Nonnull ApplyEntity apply) {
		this.id = apply.getId();
		this.description = apply.getDescription();
		this.attachment = apply.getAttachment();
		this.type = apply.getType();
		this.applicantId = apply.getApplicantId();
		this.targetId = apply.getTargetId();
		this.createAt = apply.getCreateAt();
	}

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
}
