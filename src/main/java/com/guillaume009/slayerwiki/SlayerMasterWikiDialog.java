package com.guillaume009.slayerwiki;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.inject.Inject;
import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.*;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.chatbox.ChatboxInput;
import net.runelite.client.input.KeyListener;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.LinkBrowser;
import okhttp3.HttpUrl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class SlayerMasterWikiDialog extends ChatboxInput implements KeyListener
{
	private static final int X_OFFSET = 13;
	private static final int Y_OFFSET = 16;
	private final SlayerWikiPlugin plugin;
	private final OkHttpClient okHttpClient;
	private final Gson gson;
	@Getter
	private boolean closeMessage;
	@Inject
	private Utils utils;

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == '1')
		{
			triggerOptionInformation();
		}
		if (e.getKeyChar() == '2')
		{
			triggerOptionLocation();
		}
		if (e.getKeyChar() == '3')
		{
			triggerOptionDrops();
		}
		if (e.getKeyChar() == '4')
		{
			triggerOptionDps();
		}

		triggerCloseViaMessage();

		e.consume();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}
	@Subscribe
	public void onGameTick(GameTick gameTick)
	{
		//TODO if moved or done action, hide window
		closeIfTriggered();
	}

	@Inject
	private SlayerMasterWikiDialog(SlayerWikiPlugin plugin, OkHttpClient okHttpClient, Gson gson)
	{
		this.plugin = plugin;
		this.okHttpClient = okHttpClient;
		this.gson = gson;
	}

	@Override
	public void open()
	{
		//TODO open when talking to a slayer master
		closeMessage = false;
		final Widget chatboxContainer = plugin.getChatboxPanelManager().getContainerWidget();

		final Widget widgetLine1 = chatboxContainer.createChild(-1, WidgetType.TEXT);
		final Widget widgetLine2 = chatboxContainer.createChild(-1, WidgetType.TEXT);
		final Widget monsterInformation = chatboxContainer.createChild(-1, WidgetType.TEXT);
		final Widget monsterLocation = chatboxContainer.createChild(-1, WidgetType.TEXT);
		final Widget monsterDrops = chatboxContainer.createChild(-1, WidgetType.TEXT);
		final Widget monsterDps = chatboxContainer.createChild(-1, WidgetType.TEXT);

		widgetLine1.setText("It's dangerous to go alone! What information");
		widgetLine1.setTextColor(Color.BLACK.getRGB());
		widgetLine1.setFontId(FontID.QUILL_8);
		widgetLine1.setXPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		widgetLine1.setOriginalX(73 + X_OFFSET);
		widgetLine1.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		widgetLine1.setOriginalY(0 + Y_OFFSET);
		widgetLine1.setOriginalWidth(390);
		widgetLine1.setOriginalHeight(30);
		widgetLine1.setXTextAlignment(WidgetTextAlignment.CENTER);
		widgetLine1.setYTextAlignment(WidgetTextAlignment.LEFT);
		widgetLine1.setWidthMode(WidgetSizeMode.ABSOLUTE);
		widgetLine1.revalidate();

		widgetLine2.setText("would you like to retrieve from the " + ColorUtil.wrapWithColorTag("Wiki", Color.RED) + "?");
		widgetLine2.setTextColor(Color.BLACK.getRGB());
		widgetLine2.setFontId(FontID.QUILL_8);
		widgetLine2.setXPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		widgetLine2.setOriginalX(73 + X_OFFSET);
		widgetLine2.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		widgetLine2.setOriginalY(15 + Y_OFFSET);
		widgetLine2.setOriginalWidth(390);
		widgetLine2.setOriginalHeight(30);
		widgetLine2.setXTextAlignment(WidgetTextAlignment.CENTER);
		widgetLine2.setYTextAlignment(WidgetTextAlignment.LEFT);
		widgetLine2.setWidthMode(WidgetSizeMode.ABSOLUTE);
		widgetLine2.revalidate();

		monsterInformation.setText("Monster information.");
		monsterInformation.setTextColor(Color.BLACK.getRGB());
		monsterInformation.setFontId(FontID.QUILL_8);
		monsterInformation.setXPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		monsterInformation.setOriginalX(73 + X_OFFSET);
		monsterInformation.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		monsterInformation.setOriginalY(45 + Y_OFFSET);
		monsterInformation.setOriginalWidth(390);
		monsterInformation.setOriginalHeight(17);
		monsterInformation.setXTextAlignment(WidgetTextAlignment.CENTER);
		monsterInformation.setYTextAlignment(WidgetTextAlignment.LEFT);
		monsterInformation.setWidthMode(WidgetSizeMode.ABSOLUTE);
		monsterInformation.setAction(0, "Continue");
		monsterInformation.setOnOpListener((JavaScriptCallback) ev -> triggerOptionInformation());
		monsterInformation.setOnMouseOverListener((JavaScriptCallback) ev -> monsterInformation.setTextColor(Color.WHITE.getRGB()));
		monsterInformation.setOnMouseLeaveListener((JavaScriptCallback) ev -> monsterInformation.setTextColor(Color.BLACK.getRGB()));
		monsterInformation.setHasListener(true);
		monsterInformation.revalidate();

		//TODO fix locations + drops
// 		monsterLocation.setText("Monster locations.");
// 		monsterLocation.setTextColor(Color.BLACK.getRGB());
// 		monsterLocation.setFontId(FontID.QUILL_8);
// 		monsterLocation.setXPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
// 		monsterLocation.setOriginalX(73 + X_OFFSET);
// 		monsterLocation.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
// 		monsterLocation.setOriginalY(60 + Y_OFFSET);
// 		monsterLocation.setOriginalWidth(390);
// 		monsterLocation.setOriginalHeight(17);
// 		monsterLocation.setXTextAlignment(WidgetTextAlignment.CENTER);
// 		monsterLocation.setYTextAlignment(WidgetTextAlignment.LEFT);
// 		monsterLocation.setWidthMode(WidgetSizeMode.ABSOLUTE);
// 		monsterLocation.setAction(0, "Continue");
// 		monsterLocation.setOnOpListener((JavaScriptCallback) ev -> triggerOptionLocation());
// 		monsterLocation.setOnMouseOverListener((JavaScriptCallback) ev -> monsterLocation.setTextColor(Color.WHITE.getRGB()));
// 		monsterLocation.setOnMouseLeaveListener((JavaScriptCallback) ev -> monsterLocation.setTextColor(Color.BLACK.getRGB()));
// 		monsterLocation.setHasListener(true);
// 		monsterLocation.revalidate();
//
// 		monsterDrops.setText("Monster drops.");
// 		monsterDrops.setTextColor(Color.BLACK.getRGB());
// 		monsterDrops.setFontId(FontID.QUILL_8);
// 		monsterDrops.setXPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
// 		monsterDrops.setOriginalX(73 + X_OFFSET);
// 		monsterDrops.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
// 		monsterDrops.setOriginalY(75 + Y_OFFSET);
// 		monsterDrops.setOriginalWidth(390);
// 		monsterDrops.setOriginalHeight(17);
// 		monsterDrops.setXTextAlignment(WidgetTextAlignment.CENTER);
// 		monsterDrops.setYTextAlignment(WidgetTextAlignment.LEFT);
// 		monsterDrops.setWidthMode(WidgetSizeMode.ABSOLUTE);
// 		monsterDrops.setAction(0, "Continue");
// 		monsterDrops.setOnOpListener((JavaScriptCallback) ev -> triggerOptionDrops());
// 		monsterDrops.setOnMouseOverListener((JavaScriptCallback) ev -> monsterDrops.setTextColor(Color.WHITE.getRGB()));
// 		monsterDrops.setOnMouseLeaveListener((JavaScriptCallback) ev -> monsterDrops.setTextColor(Color.BLACK.getRGB()));
// 		monsterDrops.setHasListener(true);
// 		monsterDrops.revalidate();

		monsterDps.setText("Monster DPS against current gear.");
		monsterDps.setTextColor(Color.BLACK.getRGB());
		monsterDps.setFontId(FontID.QUILL_8);
		monsterDps.setXPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		monsterDps.setOriginalX(73 + X_OFFSET);
		monsterDps.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		monsterDps.setOriginalY(90 + Y_OFFSET);
		monsterDps.setOriginalWidth(390);
		monsterDps.setOriginalHeight(17);
		monsterDps.setXTextAlignment(WidgetTextAlignment.CENTER);
		monsterDps.setYTextAlignment(WidgetTextAlignment.LEFT);
		monsterDps.setWidthMode(WidgetSizeMode.ABSOLUTE);
		monsterDps.setAction(0, "Continue");
		monsterDps.setOnOpListener((JavaScriptCallback) ev -> triggerOptionDps());
		monsterDps.setOnMouseOverListener((JavaScriptCallback) ev -> monsterDps.setTextColor(Color.WHITE.getRGB()));
		monsterDps.setOnMouseLeaveListener((JavaScriptCallback) ev -> monsterDps.setTextColor(Color.BLACK.getRGB()));
		monsterDps.setHasListener(true);
		monsterDps.revalidate();

		//buildSpriteWidget(chatboxContainer, true);
	}

	private static void buildSpriteWidget(Widget chatboxContainer, boolean isQuest) //TODO rename variables inside
	{
		final Widget questReqSprite = chatboxContainer.createChild(-1, WidgetType.GRAPHIC);

		questReqSprite.setSpriteId(isQuest ? 777 : 1298);
		questReqSprite.setXPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		questReqSprite.setOriginalX(X_OFFSET);
		questReqSprite.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		questReqSprite.setOriginalY(10);
		questReqSprite.setOriginalWidth(99);
		questReqSprite.setOriginalHeight(108);
		questReqSprite.revalidate();
	}

	void closeIfTriggered()
	{
		if (closeMessage && plugin.getChatboxPanelManager().getCurrentInput() == this)
		{
			plugin.getChatboxPanelManager().close();
		}
	}

	HttpUrl getUrlBuilder ()
	{
		final String slayerTask = utils.GetSlayerTaskName();
		final Integer taskId = utils.SLAYER_TASKS.get(slayerTask.toLowerCase());

		HttpUrl.Builder urlBuilder = utils.WIKI_BASE_ENDPOINT.newBuilder();

		if (taskId != null) {
			// Lookup with ID if we can
			urlBuilder.addQueryParameter("id", taskId.toString());
		}
		// Fallback lookup with name (good for monsters with many types like bears and bandits)
		urlBuilder.addQueryParameter("name", slayerTask);
		return urlBuilder.build();
	}

	private void triggerCloseViaMessage()
	{
		final Widget questReqContinue = plugin.getClient().getWidget(ComponentID.CHATBOX_CONTAINER).getChild(2);
		questReqContinue.setText("Please wait...");

		closeMessage = true;
	}

	private void triggerOptionInformation()
	{
		LinkBrowser.browse(getUrlBuilder().toString());
		closeMessage = true;
	}

	private void triggerOptionLocation()
	{
		LinkBrowser.browse(getUrlBuilder().toString() + "#Location");
		closeMessage = true;
	}

	private void triggerOptionDrops()
	{
		LinkBrowser.browse(getUrlBuilder().toString() + "#Drops");
		closeMessage = true;
	}

	public void triggerOptionDps()
	{
		final String slayerTask = utils.GetSlayerTaskName();
		final Integer taskId = utils.SLAYER_TASKS.get(slayerTask.toLowerCase());

		JsonObject jsonBody = buildShortlinkData();
		Request request = new Request.Builder()
				.url(utils.WIKI_DPS_SHORTLINK_ENDPOINT)
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody.toString()))
				.build();

		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				//TODO log error
			}

			@Override
			public void onResponse(Call call, Response response)
			{
				String monsterIdParameter = "";
				ShortlinkResponse resp;
				try
				{
					if (response.isSuccessful() && response.body() != null)
					{
						resp = gson.fromJson(response.body().charStream(), ShortlinkResponse.class);

						if (taskId != null) {
							monsterIdParameter = "&monster=" + taskId.toString();
						}
						LinkBrowser.browse(utils.WIKI_DPS_UI_ENDPOINT + "?id=" + resp.data + monsterIdParameter);
					}
					else
					{
						//TODO log error
						//log.warn("Failed to create shortlink for DPS calculator: http status {}", response.code());
					}
				}
				catch (IllegalStateException | IndexOutOfBoundsException e)//TODO change exceptions
				{
					//TODO log error
					//log.warn("error parsing wiki response {}", body, e);
				}
				finally
				{
					response.close();
				}
			}
		});


		closeMessage = true;
	}

	private static class ShortlinkResponse
	{
		String data;
	}

	private JsonObject buildShortlinkData()
	{
		JsonObject j = new JsonObject();

		// Build the player's loadout data
		JsonArray loadouts = new JsonArray();
		ItemContainer eqContainer = plugin.getClient().getItemContainer(InventoryID.EQUIPMENT);

		JsonObject l = new JsonObject();
		JsonObject eq = new JsonObject();

		eq.add("ammo", createEquipmentObject(eqContainer, EquipmentInventorySlot.AMMO));
		eq.add("body", createEquipmentObject(eqContainer, EquipmentInventorySlot.BODY));
		eq.add("cape", createEquipmentObject(eqContainer, EquipmentInventorySlot.CAPE));
		eq.add("feet", createEquipmentObject(eqContainer, EquipmentInventorySlot.BOOTS));
		eq.add("hands", createEquipmentObject(eqContainer, EquipmentInventorySlot.GLOVES));
		eq.add("head", createEquipmentObject(eqContainer, EquipmentInventorySlot.HEAD));
		eq.add("legs", createEquipmentObject(eqContainer, EquipmentInventorySlot.LEGS));
		eq.add("neck", createEquipmentObject(eqContainer, EquipmentInventorySlot.AMULET));
		eq.add("ring", createEquipmentObject(eqContainer, EquipmentInventorySlot.RING));
		eq.add("shield", createEquipmentObject(eqContainer, EquipmentInventorySlot.SHIELD));
		eq.add("weapon", createEquipmentObject(eqContainer, EquipmentInventorySlot.WEAPON));
		l.add("equipment", eq);

		JsonObject skills = new JsonObject();
		skills.addProperty("atk", plugin.getClient().getRealSkillLevel(Skill.ATTACK));
		skills.addProperty("def", plugin.getClient().getRealSkillLevel(Skill.DEFENCE));
		skills.addProperty("hp", plugin.getClient().getRealSkillLevel(Skill.HITPOINTS));
		skills.addProperty("magic", plugin.getClient().getRealSkillLevel(Skill.MAGIC));
		skills.addProperty("mining", plugin.getClient().getRealSkillLevel(Skill.MINING));
		skills.addProperty("prayer", plugin.getClient().getRealSkillLevel(Skill.PRAYER));
		skills.addProperty("ranged", plugin.getClient().getRealSkillLevel(Skill.RANGED));
		skills.addProperty("str", plugin.getClient().getRealSkillLevel(Skill.STRENGTH));
		l.add("skills", skills);

		JsonObject buffs = new JsonObject();
		buffs.addProperty("inWilderness", plugin.getClient().getVarbitValue(Varbits.IN_WILDERNESS) == 1);
		buffs.addProperty("kandarinDiary", plugin.getClient().getVarbitValue(Varbits.DIARY_KANDARIN_HARD) == 1);
		buffs.addProperty("onSlayerTask", plugin.getClient().getVarpValue(VarPlayer.SLAYER_TASK_SIZE) > 0);
		buffs.addProperty("chargeSpell", plugin.getClient().getVarpValue(VarPlayer.CHARGE_GOD_SPELL) > 0);
		l.add("buffs", buffs);

		l.addProperty("name", plugin.getClient().getLocalPlayer().getName());

		loadouts.add(l);
		j.add("loadouts", loadouts);

		return j;
	}

	@Nullable
	private JsonObject createEquipmentObject(ItemContainer itemContainer, EquipmentInventorySlot slot)
	{
		if (itemContainer == null)
		{
			return null;
		}

		if (slot == EquipmentInventorySlot.BOOTS && itemContainer.count() == 1 && itemContainer.contains(ItemID.CHEFS_HAT))
		{
			JsonObject o = new JsonObject();
			o.addProperty("id", ItemID.SNAIL_SHELL);
			return o;
		}

		Item item = itemContainer.getItem(slot.getSlotIdx());
		if (item != null)
		{
			JsonObject o = new JsonObject();
			o.addProperty("id", item.getId());
			return o;
		}
		return null;
	}
}
