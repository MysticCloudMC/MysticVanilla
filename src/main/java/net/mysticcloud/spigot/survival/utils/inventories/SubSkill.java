package net.mysticcloud.spigot.survival.utils.inventories;

public enum SubSkill {
	CRAFTING("Crafting"),
	SPELL("Spell-Crafting"),
	ENHANCE("Enhancement");
	
	String name;
	
	SubSkill(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public static SubSkill fromName(String skill) throws NullPointerException {
		for(SubSkill sskill : values()) {
			if(sskill.getName().equals(skill)) return sskill;
		}
		return null;
	}

}
