package code.persistence;

import java.sql.SQLException;

import code.model.action.pallet.StackedPallet;
import code.util.DatabaseHandler;

public class PersistenceHandler {
	public static final DatabaseHandler db = new DatabaseHandler("joseph.cedarville.edu", "vwburr", "warehouse", "vwburr15");
	
	public static void updatePalletLocation(StackedPallet pallet){
		try {
			double x = pallet.getLocalTranslation().getX();
			double z = pallet.getLocalTranslation().getZ();
			db.executeQuery("UPDATE DPallet SET X_Location = "+x+", Z_Location = "
					+z + " WHERE id="+pallet.getID());
			
			System.out.println("Moved pallet "+pallet.getID()+" to <"+x+", "+z+">.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}