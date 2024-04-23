package com.guillaume009.slayerwiki;

import com.google.common.base.CaseFormat;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.plugins.*;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.plugins.slayer.SlayerConfig;
import net.runelite.client.plugins.slayer.SlayerPlugin;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.LinkBrowser;
import net.runelite.client.util.Text;
import net.runelite.client.game.NPCManager;
import okhttp3.HttpUrl;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.client.plugins.PluginDescriptor;

import java.awt.*;

@Slf4j
@PluginDependency(SlayerPlugin.class)
@PluginDescriptor(
		name = "Slayer Wiki",
		description = "Adds Wiki option to slayer equipment to lookup current task.",
		tags = {"slayer", "wiki", "slayer wiki"}
)

public class SlayerWikiPlugin extends Plugin
{
	@Getter(AccessLevel.PACKAGE)
	@Inject
	private Client client;

	@Getter(AccessLevel.PACKAGE)
	@Inject
	private ChatboxPanelManager chatboxPanelManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private NPCManager npcManager;

	@Inject
	private SlayerMasterWikiDialog slayerMasterWikiDialog;

	@Inject
	private SlayerMasterWikiDialog input;

	@Inject
	private Utils utils;

	@Override
	protected void shutDown() throws Exception
	{
		if (input != null && chatboxPanelManager.getCurrentInput() == input) chatboxPanelManager.close();
		input = null;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			//utils.GetSlayerTaskName(true);
		}
	}

	@Subscribe
	public void onMenuOpened(MenuOpened event) {
		final String target = Text.removeTags(event.getFirstEntry().getTarget().toLowerCase());
		for ( String slayerItem : utils.SLAYER_ITEMS) {
			if (target.contains(slayerItem)) {
				final SlayerConfig slayerConfig = configManager.getConfig(SlayerConfig.class);

				final String slayerTask = utils.GetSlayerTaskName();

				if (!slayerTask.isEmpty()) {
					final MenuEntry[] menuEntries = event.getMenuEntries();
					final Integer menuLength = menuEntries.length + 1;
					final String TASK = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, slayerTask);

					client.createMenuEntry(1).setOption("Task Wiki").setTarget(ColorUtil.prependColorTag(TASK, Color.orange)).setType(MenuAction.RUNELITE).onClick(this::onTaskWikiMenuOptionClicked);
					client.createMenuEntry(1).setOption("DPS Wiki").setTarget(ColorUtil.prependColorTag(TASK, Color.orange)).setType(MenuAction.RUNELITE).onClick(this::onDPSWikiMenuOptionClicked);
				}
				break;
			}
		}
	}

	private void onTaskWikiMenuOptionClicked(MenuEntry event) {
		final String slayerTask = utils.GetSlayerTaskName();
		final Integer taskId = utils.SLAYER_TASKS.get(slayerTask.toLowerCase());

		HttpUrl.Builder urlBuilder = utils.WIKI_BASE_ENDPOINT.newBuilder();

		if (taskId != null) {
			// Lookup with ID if we can
			urlBuilder.addQueryParameter("id", taskId.toString());
		}
		// Fallback lookup with name (good for monsters with many types like bears and bandits)
		urlBuilder.addQueryParameter("name", slayerTask);
		LinkBrowser.browse(urlBuilder.build().toString());
	}

	private void onDPSWikiMenuOptionClicked(MenuEntry event) {
// 		if (input != null) input.closeIfTriggered();
// 		chatboxPanelManager.openInput(slayerMasterWikiDialog);
		slayerMasterWikiDialog.triggerOptionDps(); //TODO extract from dialog to make more clean
	}
}
