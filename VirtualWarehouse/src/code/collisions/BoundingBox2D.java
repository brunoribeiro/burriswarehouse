package code.collisions;

public class BoundingBox2D {
	private float leftX;
	private float rightX;
	private float lowerZ;
	private float upperZ;

	public BoundingBox2D() {
	}
	
	public BoundingBox2D(float leftX, float rightX, float lowerZ, float upperZ) {
		this.leftX = leftX;
		this.rightX = rightX;
		this.lowerZ = lowerZ;
		this.upperZ = upperZ;
	}

	public void setLeftX(float leftX) {
		this.leftX = leftX;
	}

	public float getLeftX() {
		return leftX;
	}

	public void setRightX(float rightX) {
		this.rightX = rightX;
	}

	public float getRightX() {
		return rightX;
	}

	public void setLowerZ(float lowerZ) {
		this.lowerZ = lowerZ;
	}

	public float getLowerZ() {
		return lowerZ;
	}

	public void setUpperZ(float upperZ) {
		this.upperZ = upperZ;
	}

	public float getUpperZ() {
		return upperZ;
	}

	

	/*public void checkForCollisions(Player playerNode, float lastX, float lastZ) {
		float playerX = playerNode.getLocalTranslation().getX();
		float playerZ = playerNode.getLocalTranslation().getZ();
		
		if(playerNode.inVehicle() && playerNode.getVehicleBeingUsed() != null){
			playerX = playerNode.getVehicleBeingUsed().getLocalTranslation().getX();
			playerZ = playerNode.getVehicleBeingUsed().getLocalTranslation().getZ();
		}

		if ((playerX > leftX) && (playerZ < lowerZ) && (playerX < rightX) && (playerZ > upperZ)) {
			float diffRightX = Math.abs(playerX-rightX);
			float diffLeftX = Math.abs(playerX-leftX);
			float top = Math.abs(playerZ-upperZ);
			float bottom = Math.abs(playerZ-lowerZ);
			
			// so if I'm closest to the right of the wall...
			if (diffRightX <= diffLeftX && diffRightX <= top && diffRightX<= bottom){
				playerNode.setLocalTranslation(playerX+diffRightX, .1f, playerZ);
			}
			
			else if (diffLeftX <= diffRightX && diffLeftX <= top && diffLeftX <= bottom){
				playerNode.setLocalTranslation(playerX-diffLeftX, .1f, playerZ);
			}
			
			else if (top <= bottom && top <= diffRightX && top <= diffLeftX){
				playerNode.setLocalTranslation(playerX, .1f, playerZ-top);
			}
			else if (bottom <= top && bottom <= diffRightX && bottom <= diffLeftX){
				playerNode.setLocalTranslation(playerX, .1f, playerZ+bottom);
			}
		}		
	}*/
	
	public boolean isCollidingWith(BoundingBox2D b){
		/*if(this.leftX >= b.rightX)return false;
		if(this.rightX >= b.leftX)return false;
		if(this.lowerZ >= b.upperZ)return false;
		if(this.upperZ <= b.lowerZ)return false; 
		return true;*/
		return !(b.leftX > this.rightX || b.rightX < this.leftX || b.upperZ > this.lowerZ || b.lowerZ < this.upperZ);
	}

}