package example.swm.app.entity;
/**
 * 
 * @author Johan
 * @
 *
 */
public class ClassRoom {
	private Integer id;
	private Integer capacity;
	private String type;
	private String buliding;
	private Integer roomNum;
	private Integer[] freeTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBuliding() {
		return buliding;
	}
	public void setBuliding(String buliding) {
		this.buliding = buliding;
	}
	public Integer getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}
	public Integer[] getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(Integer[] freeTime) {
		this.freeTime = freeTime;
	} 
	
	
	

}
