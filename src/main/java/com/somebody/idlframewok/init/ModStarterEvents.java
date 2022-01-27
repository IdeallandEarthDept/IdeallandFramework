package com.somebody.idlframewok.init;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.CUR_STARTER_KIT_VERSION;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STARTER_KIT_VERSION_TAG;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil.getPlayerIdeallandIntSafe;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModStarterEvents {
	
	//public static final String TAG_PLAYER_HAS_BOOK = IDLNBTDef.STARTER_BOOK_GIVEN;

	public static SimpleDateFormat formatLoginTime = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

	//if the event subscriber is static, so should the events be
	//Thanks Cadiboo for telling me that
	  @SubscribeEvent
	  public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		  EntityPlayer player = event.player;
		  Calendar calendar = player.world.getCurrentDate();
		  Idealland.Log("[IDL][LOGIN]%s(%s) logged in @%s", player.getDisplayNameString(), player.getUniqueID(), formatLoginTime.format(calendar.getTime()));

		  //Idealland.Log(getPlyrIdlTagSafe(player).toString());
		  int lastStarterVer = getPlayerIdeallandIntSafe(player, STARTER_KIT_VERSION_TAG);
		  if(lastStarterVer < CUR_STARTER_KIT_VERSION) {
              IDLNBTUtil.setPlayerIdeallandTagSafe(player, STARTER_KIT_VERSION_TAG, CUR_STARTER_KIT_VERSION);

			  if (player instanceof EntityPlayerMP) {
				  CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP)player, "idlframewok.msg.starter_kit_given", TextFormatting.AQUA);

			  }
			  Idealland.Log(String.format("Given starter items to player %s, ver %d", player.getDisplayNameString(), CUR_STARTER_KIT_VERSION));
		  }
//
//		  //do sth here
//
//		  data.setTag(IDEALLAND, idl_data);
//		  playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);

//		  if(data.getInteger(KILL_COUNT)) {
//			  //ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(TinkerCommons.book));
//			  data.setBoolean(TAG_PLAYER_HAS_BOOK, true);
//			  playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
//
//			  EntityPlayer player = event.player;
//
//			  ItemStack heirloom = new ItemStack(ModItems.HEIRLOOM);
//			  DWeaponSwordBase.SetOwner(heirloom, player.getDisplayNameString());
//
//			  event.player.addItemStackToInventory(heirloom);
//			  event.player.addItemStackToInventory(CreateManual(player));
//			  DWeapons.Log(String.format("Given starter items to player %s", player.getDisplayNameString()));
//		  }
	  }
	
}
