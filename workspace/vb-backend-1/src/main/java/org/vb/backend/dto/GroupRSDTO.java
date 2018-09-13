package org.vb.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class GroupRSDTO {
	
	private Long groupId;
	private String groupName;
	private List<BoxRSDTO> boxList;

	public GroupRSDTO() {
		this.boxList = new ArrayList<>();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<BoxRSDTO> getBoxList() {
		return boxList;
	}

	public void setBoxList(List<BoxRSDTO> boxList) {
		this.boxList = boxList;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
