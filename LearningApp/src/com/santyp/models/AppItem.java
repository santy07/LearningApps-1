package com.santyp.models;

import android.graphics.drawable.Drawable;

public class AppItem implements Comparable<AppItem> { 

	private String label;
	private String name;
	private Drawable icon;
	private boolean included;
	private boolean important;

	public AppItem(String label, String name, String packageName, Drawable icon, boolean included, boolean important){
		this.label= label;
		this.name= name;
		this.icon= icon;
		this.included= included;
		this.important= important;
	}

	public int compareTo(AppItem another) {
		if (isImportant() && !another.isImportant()) return -1;
		if (!isImportant() && another.isImportant()) return 1;
		return getLabel().compareTo(another.getLabel());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	public boolean isIncluded() {
		return included;
	}

	public void setIncluded(boolean included) {
		this.included = included;
	}
}