package net.mysticcloud.spigot.survival.utils.perks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.mysticcloud.spigot.survival.utils.Division;

public enum Perks {

	ARCHERY_RANGE_I("Archery-RangeI"), ARCHERY_RANGE_II("Archery-RangeII"), ARCHERY_SEEKER("Archery-Seeker"),
	MAGE_SWAP("Mage-Swap"), MAGE_MANA_BOOST("Mage-ManaBoost"), WARRIOR_FURY("Warrior-Fury");

	String name;

	Perks(String name) {
		this.name = name;
	}

	public static String getName(Perk perk) {
		String name = "";
		if (perk instanceof ArcheryPerk) {
			name = "Archery";
			if (perk instanceof ArcheryPerkRangeI)
				name = name + "-RangeI";
			if (perk instanceof ArcheryPerkRangeII)
				name = name + "-RangeII";
			if (perk instanceof ArcheryPerkSeeker)
				name = name + "-Seeker";
		}
		if (perk instanceof MagePerk) {
			name = "Mage";
			if (perk instanceof MagePerkSwap)
				name = name + "-Swap";
			if (perk instanceof MagePerkManaBoost)
				name = name + "-ManaBoost";
		}
		if (perk instanceof WarriorPerk) {
			name = "Warrior";
			if (perk instanceof WarriorPerkFury)
				name = name + "-Fury";
		}
		return name;
	}

	public String getName() {
		return name;
	}

	public static Perks getPerk(String name) {
		switch (name.toUpperCase()) {
		case "ARCHERY-RANGEI":
			return ARCHERY_RANGE_I;
		case "ARCHERY-RANGEII":
			return ARCHERY_RANGE_II;
		case "ARCHERY-SEEKER":
			return ARCHERY_SEEKER;
		case "MAGE-SWAP":
			return MAGE_SWAP;
		case "MAGE-MANABOOST":
			return MAGE_MANA_BOOST;
		case "WARRIOR-FURY":
			return Perks.WARRIOR_FURY;
		default:
			return null;
		}
	}

	public Perk getPerk(UUID uid) {
		switch (name) {
		case "Archery-RangeI":
			return new ArcheryPerkRangeI(uid);
		case "Archery-RangeII":
			return new ArcheryPerkRangeII(uid);
		case "Archery-Seeker":
			return new ArcheryPerkSeeker(uid);
		case "Mage-Swap":
			return new MagePerkSwap(uid);
		case "Mage-ManaBoost":
			return new MagePerkManaBoost(uid);
		case "Warrior-Fury":
			return new WarriorPerkFury(uid);
		default:
			return new Perk(uid);
		}
	}

	public static Perks getPerk(Division division, String perk) {
		String div = "";
		switch (division.name()) {
		case "ARCHER":
			div = "ARCHERY";
			break;
		case "MAGE":
		case "WARRIOR":
			div = division.name();
		default:
			break;
		}
		return getPerk(div + "-" + perk);
	}

	public static List<Perks> getPerks(Division div) {
		List<Perks> perks = new ArrayList<>();
		for(Perks perk : Perks.values()) {
			if(perk.getDivision().equals(div)) {
				perks.add(perk);
			}
		}
		return perks;
	}

	public Division getDivision() {
		for(Division div : Division.values()) {
			if(getName().split("-")[0].equalsIgnoreCase(div.getPerkPrefix())) {
				return div;
			}
		}
		return Division.MAGE;
	}

}
