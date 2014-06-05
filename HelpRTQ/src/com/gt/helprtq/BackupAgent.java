package com.gt.helprtq;

import java.io.File;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;

// deriva da com.gt.helprtq
//	<meta-data android:name="com.google.android.backup.api_key" android:value="AEdPqrEAAAAIZs7liKj6_VuQwJEdI7WHzYkOU1RT5yylEfYQnQ" />
// deriva da com.gt.helprtq.MainActivity
//	<meta-data android:name="com.google.android.backup.api_key" android:value="AEdPqrEAAAAI4zeRY4Q7RXCTeKxkRvQ4g2_IPehyCN3ZJY02iQ" />
public class BackupAgent extends BackupAgentHelper {
	private static final String DB_NAME = LocalDB.DATABASE_NAME;

	@Override
	public void onCreate(){
		FileBackupHelper dbs = new FileBackupHelper(this, DB_NAME);
		addHelper("helprtq", dbs);
	}

	@Override
	public File getFilesDir(){
		File path = getDatabasePath(DB_NAME);
		return path.getParentFile();
	}
}