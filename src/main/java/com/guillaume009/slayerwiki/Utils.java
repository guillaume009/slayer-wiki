package com.guillaume009.slayerwiki;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.runelite.api.NpcID;
import okhttp3.HttpUrl;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import net.runelite.api.Client;

public class Utils
{
	@Inject
	public Client client;
	public String slayerTaskName;
	private static final String SLAYER_HELMET = "slayer helmet";
	private static final String ETERNAL_GEM = "eternal gem";
	private static final String ENCHANTED_GEM = "enchanted gem";
	private static final String SLAYER_RING = "slayer ring";
	private static final String BLACK_MASK = "black mask";
	public static final HttpUrl WIKI_BASE_ENDPOINT = HttpUrl.parse("https://oldschool.runescape.wiki/w/Special:Lookup?type=npc");
	public static final String WIKI_DPS_UI_ENDPOINT = "https://tools.runescape.wiki/osrs-dps/";
	public static final String WIKI_DPS_SHORTLINK_ENDPOINT = "https://tools.runescape.wiki/osrs-dps/shortlink";
	public static final Set<String> SLAYER_ITEMS = ImmutableSet.of(SLAYER_HELMET, SLAYER_RING, ETERNAL_GEM, ENCHANTED_GEM, BLACK_MASK);
	public static final Map<String, Integer> SLAYER_TASKS = ImmutableMap.<String, Integer>builder()
																		 .put("aberrant spectres", NpcID.ABERRANT_SPECTRE)
																		 .put("abyssal demons", NpcID.ABYSSAL_DEMON_415)
																		 .put("adamant dragons", NpcID.ADAMANT_DRAGON)
																		 .put("ankous", NpcID.ANKOU)
																		 .put("aviansie", NpcID.AVIANSIE)
																		 .put("banshees", NpcID.BANSHEE)
																		 .put("basilisks", NpcID.BASILISK_417)
																		 .put("birds", NpcID.BIRD)
																		 .put("bears", NpcID.BLACK_BEAR)
																		 .put("black demons", NpcID.BLACK_DEMON)
																		 .put("black dragons", NpcID.BLACK_DRAGON)
																		 .put("black knights", NpcID.BLACK_KNIGHT)
																		 .put("bloodvelds", NpcID.BLOODVELD)
																		 .put("blue dragons", NpcID.BLUE_DRAGON)
																		 .put("brine rats", NpcID.BRINE_RAT)
																		 .put("bronze dragons", NpcID.BRONZE_DRAGON)
																		 .put("catablepon", NpcID.CATABLEPON)
																		 .put("cave bugs", NpcID.CAVE_BUG)
																		 .put("cave crawlers", NpcID.CAVE_CRAWLER)
																		 .put("cave horrors", NpcID.CAVE_HORROR)
																		 .put("cave slimes", NpcID.CAVE_SLIME)
																		 .put("cave kraken", NpcID.CAVE_KRAKEN)
																		 .put("chaos druids", NpcID.CHAOS_DRUID)
																		 .put("cockatrices", NpcID.COCKATHRICE)
																		 .put("cows", NpcID.COW)
																		 .put("crawling hands", NpcID.CRAWLING_HAND)
																		 .put("crocodiles", NpcID.CROCODILE)
																		 .put("dagannoths", NpcID.DAGANNOTH_970)
																		 .put("dark beast", NpcID.DARK_BEAST)
																		 .put("dark warriors", NpcID.DARK_WARRIOR)
																		 .put("dogs", NpcID.GUARD_DOG)
																		 .put("drakes", NpcID.DRAKE)
																		 .put("dwarfs", NpcID.DWARF)
																		 .put("earth warriors", NpcID.EARTH_WARRIOR)
																		 .put("elves", NpcID.ELF_ARCHER)
																		 .put("ent", NpcID.ENT)
																		 .put("fever spiders", NpcID.FEVER_SPIDER)
																		 .put("fire giants", NpcID.FIRE_GIANT)
																		 .put("flesh crawlers", NpcID.FLESH_CRAWLER)
																		 .put("fossil island wyvern", NpcID.SPITTING_WYVERN)
																		 .put("gargoyles", NpcID.GARGOYLE)
																		 .put("ghosts", NpcID.GHOST)
																		 .put("ghouls", NpcID.GHOUL)
																		 .put("goblins", NpcID.GOBLIN)
																		 .put("greater demons", NpcID.GREATER_DEMON)
																		 .put("green dragons", NpcID.GREEN_DRAGON)
																		 .put("harpie bug swarms", NpcID.HARPIE_BUG_SWARM)
																		 .put("hellhounds", NpcID.HELLHOUND)
																		 .put("hill giant", NpcID.HILL_GIANT)
																		 .put("hobgoblins", NpcID.HOBGOBLIN)
																		 .put("hydras", NpcID.HYDRA)
																		 .put("icefiends", NpcID.ICEFIEND)
																		 .put("ice giants", NpcID.ICE_GIANT)
																		 .put("infernal mages", NpcID.INFERNAL_MAGE)
																		 .put("iron dragons", NpcID.IRON_DRAGON)
																		 .put("jellies", NpcID.JELLY)
																		 .put("jungle horrors", NpcID.JUNGLE_HORROR)
																		 .put("kalphites", NpcID.KALPHITE_SOLDIER)
																		 .put("killerwatts", NpcID.KILLERWATT)
																		 .put("kurasks", NpcID.KURASK)
																		 .put("lava dragons", NpcID.LAVA_DRAGON)
																		 .put("lesser demons", NpcID.LESSER_DEMON)
																		 .put("lizardmen", NpcID.LIZARDMAN)
																		 .put("lizards", NpcID.LIZARD)
																		 .put("magic axes", NpcID.MAGIC_AXE)
																		 .put("mithril dragons", NpcID.MITHRIL_DRAGON)
																		 .put("minotaurs", NpcID.MINOTAUR)
																		 .put("mogres", NpcID.MOGRE)
																		 .put("molanisks", NpcID.MOLANISK)
																		 .put("monkeys", NpcID.MONKEY_1038)
																		 .put("moss giants", NpcID.MOSS_GIANT)
																		 .put("nechryael", NpcID.NECHRYAEL)
																		 .put("ogres", NpcID.OGRE)
																		 .put("otherworldly beings", NpcID.OTHERWORLDLY_BEING)
																		 .put("pirate", NpcID.PIRATE)
																		 .put("pyrefiends", NpcID.PYREFIEND)
																		 .put("red dragons", NpcID.RED_DRAGON)
																		 .put("rockslugs", NpcID.ROCKSLUG)
																		 .put("rogues", NpcID.ROGUE)
																		 .put("rune dragons", NpcID.RUNE_DRAGON)
																		 .put("scabarites", NpcID.SCABARAS)
																		 .put("scorpions", NpcID.SCORPION)
																		 .put("sea snakes", NpcID.SEA_SNAKE_YOUNG)
																		 .put("shades", NpcID.SHADE)
																		 .put("shadow warriors", NpcID.SHADOW_WARRIOR)
																		 .put("skeletal wyverns", NpcID.SKELETAL_WYVERN)
																		 .put("skeletons", NpcID.SKELETON)
																		 .put("smoke devils ", NpcID.SMOKE_DEVIL)
																		 .put("spiders", NpcID.SPIDER)
																		 .put("steel dragons", NpcID.STEEL_DRAGON)
																		 .put("suqahs", NpcID.SUQAH)
																		 .put("terror dogs", NpcID.TERROR_DOG)
																		 .put("trolls", NpcID.TROLL)
																		 .put("turoths", NpcID.TUROTH)
																		 .put("vampyres", NpcID.FERAL_VAMPYRE)
																		 .put("wall beasts", NpcID.WALL_BEAST)
																		 .put("waterfiends", NpcID.WATERFIEND)
																		 .put("werewolves", NpcID.WEREWOLF)
																		 .put("wolves ", NpcID.WOLF)
																		 .put("wyrms ", NpcID.WYRM)
																		 .put("zombies", NpcID.ZOMBIE)
																		 .put("zygomites", NpcID.ZYGOMITE)
																		 .put("abyssal sire", NpcID.ABYSSAL_SIRE)
																		 .put("alchemical hydra", NpcID.ALCHEMICAL_HYDRA)
																		 .put("barrows", NpcID.DHAROK_THE_WRETCHED)
																		 .put("callisto", NpcID.CALLISTO)
																		 .put("cerberus", NpcID.CERBERUS)
																		 .put("chaos elemental", NpcID.CHAOS_ELEMENTAL)
																		 .put("chaos fanatic", NpcID.CHAOS_FANATIC)
																		 .put("commander zilyana", NpcID.COMMANDER_ZILYANA)
																		 .put("crazy archaeologist", NpcID.CRAZY_ARCHAEOLOGIST)
																		 .put("dagannoth kings", NpcID.DAGANNOTH_REX)
																		 .put("giant mole", NpcID.GIANT_MOLE)
																		 .put("general graardor", NpcID.GENERAL_GRAARDOR)
																		 .put("k'ril tsutsaroth", NpcID.KRIL_TSUTSAROTH)
																		 .put("kalphite queen", NpcID.KALPHITE_QUEEN)
																		 .put("king black dragon", NpcID.KING_BLACK_DRAGON)
																		 .put("kraken", NpcID.KRAKEN)
																		 .put("kree'arra", NpcID.KREEARRA)
																		 .put("sarachnis", NpcID.SARACHNIS)
																		 .put("scorpia", NpcID.SCORPIA)
																		 .put("thermonuclear smoke devil", NpcID.THERMONUCLEAR_SMOKE_DEVIL)
																		 .put("venenatis", NpcID.VENENATIS)
																		 .put("vet'ion", NpcID.VETION)
																		 .put("vorkath", NpcID.VORKATH)
																		 .put("zulrah", NpcID.ZULRAH)
																		 .put("dust devils", NpcID.DUST_DEVIL)
																		 .build();

	public String GetSlayerTaskName (){
// 		if(fromMainThread) {
			int taskId = this.client.getVarpValue(395);
			String taskName;
			int areaId;
			if (taskId == 98) {
				areaId = this.client.getEnum(5008).getIntValue(this.client.getVarbitValue(4723));
				taskName = this.client.getStructComposition(areaId).getStringValue(1801);
			} else {
				taskName = this.client.getEnum(693).getStringValue(taskId);
			}
			slayerTaskName = taskName;
			return taskName;
// 		}
// 		else
// 		{
// 			//TODO refresh value when required / remove this crap
// 			return slayerTaskName;
// 		}
	}
}
