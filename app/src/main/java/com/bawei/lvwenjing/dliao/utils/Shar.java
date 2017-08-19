package com.bawei.lvwenjing.dliao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Shar {
	private static final String FILENAME = "faly";

	public static void setFile(Context context, String key, Object value) {
		SharedPreferences sp = context.getSharedPreferences(FILENAME,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		String name = value.getClass().getSimpleName();
		if ("String".equals(name)) {
			edit.putString(key, (String) value);
		} else if ("boolean".equals(name)) {
			edit.putBoolean(key, (boolean) value);
		} else if ("Integer".equals(name)) {
			edit.putInt(key, (int) value);

		} else if ("float".equals(name)) {
			edit.putFloat(key, (float) value);
		} else if ("long".equals(name)) {
			edit.putLong(key, (long) value);
		}
		edit.commit();
	}

	public static Object getFliet(Context context, String key, Object value) {
		SharedPreferences sp = context.getSharedPreferences(FILENAME,
				Context.MODE_PRIVATE);

		String name = value.getClass().getSimpleName();
		if ("String".equals(name)) {
			return sp.getString(key, "");
		} else if ("boolean".equals(name)) {
			return sp.getBoolean(key, false);
		} else if ("Integer".equals(name)) {
			return sp.getInt(key, 1);
		} else if ("float".equals(name)) {
			return sp.getFloat(key, 0.1f);
		} else if ("long".equals(name)) {
			return sp.getLong(key, 5);
		}

		return null;

	}
}
