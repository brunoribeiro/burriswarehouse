package code.model.player.autocompletion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jme.math.FastMath;
import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import code.model.player.Player;
import code.util.Coordinate;
import code.util.DatabaseHandler;

public class AutoCompletionHandler {
	private ArrayList<Waypoint> path;
	private DatabaseHandler db;
	private ResultSet rs;
	private Waypoint start;
	private Waypoint finish;
	private int counter;
	private Player player;
	private boolean active;
	private boolean walking;
	private int section;
	private float playerDirection;
	protected static final Matrix3f incr = new Matrix3f();

	private boolean enabled = true;

	public static final int FORWARD = 0;
	public static final int ROTLEFT = 1;
	public static final int ROTRIGHT = 2;
	public static final int GETOFFPJ = 3;
	public static final int GETONPJ = 4;
	public static final int PICKUPBOX = 5;
	public static final int PUTDOWNBOX = 6;
	public static final int PICKUPPALLET = 7;

	public AutoCompletionHandler(Player player, int pickjob, int start,
			int finish) {
		if (!enabled)
			return;
		try {
			db = new DatabaseHandler("joseph.cedarville.edu", "vwburr",
					"warehouse", "vwburr15");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// this.counter = path.indexOf(this.start);
		this.counter = 0;
		this.player = player;
		this.walking = false;
		this.active = false;
		this.section = 1;
	}

	public boolean isActive() {
		return this.active;
	}

	public void activate() {
		if (!enabled)
			return;

		player.setCollisionDetection(false);

		try {
			this.path = new ArrayList<Waypoint>();
			rs = db
					.executeQuery("SELECT * FROM PICKWAYPOINT WHERE pickjob = 1 AND section="
							+ section);

			while (rs.next()) {
				Waypoint w = new Waypoint(rs.getFloat(3), rs.getFloat(4), rs
						.getInt(2), rs.getString(5), rs.getInt(6),
						rs.getInt(7), rs.getInt(8));
				path.add(w);
			}

			if (path.size() == 0) {
				section = -1;
				deactivate();
			}

			this.start = path.get(0);
			this.finish = path.get(path.size() - 1);

			this.active = true;
			if (!player.inVehicle()) {
				player.setLocalTranslation(start.getX(), .1f, start.getZ());
			}

			else {
				// I think instead of doing this, you should take either take
				// the player off the vehicle, reset player and vehicle,
				// and start from the beginning, or you should just start from
				// where the player got on the vehicle... -CM
				player.getVehicleBeingUsed().setLocalTranslation(start.getX(),
						.1f, start.getZ());
			}
			this.counter++;
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deactivate() {
		player.setCollisionDetection(true);
		this.active = false;
		advance();
		return;
	}

	public void advance() {
		counter = 0;
		section++;
	}

	public void update() {
		if (!enabled)
			return;
		if (!active)
			return;

		player.getDebugHud().setAutoCount(counter);

		Waypoint w = path.get(this.counter);

		int action = w.getAction();

		float x = w.getX();
		float z = w.getZ();

		float myX, myZ;

		if (action == GETONPJ) {
			player.checkVehicleEnterExit(true);
			counter++;
		} else if (action == GETOFFPJ) {
			player.checkVehicleEnterExit(true);
			counter++;
		} else if (action == PICKUPBOX) {
			player.checkProductGetDrop(true);
			counter++;
		} else if (action == PUTDOWNBOX) {
			player.checkProductGetDrop(true);
			counter++;
		} else if (action == PICKUPPALLET) {
			player.getVehicleBeingUsed().checkForPalletPickup(true);
			counter++;
		} else {

			if (!player.inVehicle()) {
				myX = player.getLocalTranslation().getX();
				myZ = player.getLocalTranslation().getZ();
				if ((Math.abs(myX - x) > .25f) || (Math.abs(myZ - z) > .25f)) {
					playerDirection = -(FastMath.atan2(myZ - z, myX - x));
					player.setLocalRotation(new Quaternion().fromAngleAxis(
						playerDirection, Vector3f.UNIT_Y));
				}
			} else {
				myX = player.getVehicleBeingUsed().getLocalTranslation().getX();
				myZ = player.getVehicleBeingUsed().getLocalTranslation().getZ();
				// if i'm far enough to care about direction...otherwise the 
				// corners look a little funky...
				if ((Math.abs(myX - x) > .75f) || (Math.abs(myZ - z) > .75f)) {
					playerDirection = -(FastMath.atan2(myZ - z, myX - x));
					player.getVehicleBeingUsed().setLocalRotation(
						new Quaternion().fromAngleAxis(playerDirection - 1.55f,
								Vector3f.UNIT_Y));
				}

			}

			walking = false;

			// if I'm close enough to go on to the next thing...
			if ((Math.abs(myX - x) < .1f) && (Math.abs(myZ - z) < .1f)) {
				this.counter++;

			} else if (!player.inVehicle()) {

				if ((myX < x) && (Math.abs(myX - x) > .07f)) {
					walking = true;
					player.setLocalTranslation(myX + .035f, .01f, myZ);

				} else if ((myX > x) && (Math.abs(myX - x) > .07f)) {
					walking = true;
					player.setLocalTranslation(myX - .035f, .01f, myZ);
				}

				if ((myZ < z) && Math.abs(myZ - z) > .07f) {
					walking = true;
					player.setLocalTranslation(myX, .01f, myZ + .035f);
				} else if ((myZ > z) && Math.abs(myZ - z) > .07f) {
					walking = true;
					player.setLocalTranslation(myX, .01f, myZ - .035f);
				}
			} else {
				walking = false;
				if ((myX < x) && (Math.abs(myX - x) > .07f)) {
					player.getVehicleBeingUsed().setLocalTranslation(
							myX + .070f, .01f, myZ);

				} else if ((myX > x) && (Math.abs(myX - x) > .07f)) {
					player.getVehicleBeingUsed().setLocalTranslation(
							myX - .070f, .01f, myZ);
				}

				if ((myZ < z) && Math.abs(myZ - z) > .07f) {
					player.getVehicleBeingUsed().setLocalTranslation(myX, .01f,
							myZ + .070f);
				} else if ((myZ > z) && Math.abs(myZ - z) > .07f) {
					player.getVehicleBeingUsed().setLocalTranslation(myX, .01f,
							myZ - .070f);
				}
			}
		}

		if (counter >= path.size()) {
			deactivate();
		}
		if (!walking) {
			player.stationaryAnim();
		} else {
			player.translateForwardAnim();
		}
	}
}