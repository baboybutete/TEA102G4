package com.resseat.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectedSeatVO {
	/*<custid seletedseats>*/
	private Map<String,List<String>> selectedSeat;

	public Map<String, List<String>> getSelectedSeat() {
		return selectedSeat;
	}
	public SelectedSeatVO() {
		this.selectedSeat = new HashMap<String,List<String>>();
	}

	public void setSelectedSeat(Map<String, List<String>> selectedSeat) {
		this.selectedSeat = selectedSeat;
	}
	
}
