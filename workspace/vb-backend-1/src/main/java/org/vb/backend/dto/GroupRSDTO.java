package org.vb.backend.dto;

public class GroupRSDTO {
	
	private Long groupId;
	private String groupName;
	private Long boxCount;

	public GroupRSDTO() {

	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getBoxCount() {
		return boxCount;
	}

	public void setBoxCount(Long boxCount) {
		this.boxCount = boxCount;
	}
}
