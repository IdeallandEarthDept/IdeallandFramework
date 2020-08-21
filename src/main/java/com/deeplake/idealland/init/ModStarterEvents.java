package com.deeplake.idealland.init;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLNBT;
import com.deeplake.idealland.util.IDLSkillNBT;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import com.deeplake.idealland.util.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.lang.reflect.Modifier;

import static com.deeplake.idealland.util.IDLNBT.*;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModStarterEvents {
	
	//public static final String TAG_PLAYER_HAS_BOOK = IDLNBTDef.STARTER_BOOK_GIVEN;

	//if the event subscriber is static, so should the events be
	//Thanks Cadiboo for telling me that
	  @SubscribeEvent
	  public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		  EntityPlayer player = event.player;
		  //Idealland.Log(getPlyrIdlTagSafe(player).toString());
		  int lastStarterVer = getPlayerIdeallandIntSafe(player, STARTER_KIT_VERSION_TAG);
		  if(lastStarterVer < CUR_STARTER_KIT_VERSION) {
			  IDLNBT.setPlayerIdeallandTagSafe(player, STARTER_KIT_VERSION_TAG, CUR_STARTER_KIT_VERSION);

			  ItemStack scry = new ItemStack(ModItems.skillScry);
			  ItemStack guaPalm = new ItemStack(ModItems.skillGuaPalm);
			  IDLSkillNBT.SetGuaEnhanceFree(guaPalm, 3);
			  player.addItemStackToInventory(scry);
			  player.addItemStackToInventory(guaPalm);

			  if (player.getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty())
			  {
				  ItemStack geta = new ItemStack(ModItems.ITEM_ARMOR_UNDERFOOT_GETA);
				  IDLSkillNBT.SetGuaEnhanceFree(geta, 1);
				  player.setItemStackToSlot(EntityEquipmentSlot.FEET, geta);
			  }

			  if (player instanceof EntityPlayerMP) {
				  CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP)player, "idealland.msg.starter_kit_given", TextFormatting.AQUA);

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
