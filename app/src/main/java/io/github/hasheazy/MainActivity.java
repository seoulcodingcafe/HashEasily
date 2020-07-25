//    The GNU General Public License does not permit incorporating this program
//    into proprietary programs.
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <https://www.gnu.org/licenses/>.

package io.github.hasheazy;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.hasheazy.db.Result;
import io.github.hasheazy.tools.BytesToString;
import io.github.hasheazy.ui.Alert;

public class MainActivity extends CyaneaAppCompatActivity {

	public TextView mTextViewSelectedFiles;
	public ProgressBar mProgressBar;
	List<Uri> mSelectedFiles = new ArrayList<>();
	BottomNavigationView mNavView;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			mSelectedFiles.clear();
			if (data != null && data.getClipData() != null) {
				ClipData clipData = data.getClipData();
				for (int i = 0; i < clipData.getItemCount(); i++) {
					ClipData.Item item = clipData.getItemAt(i);
					Uri uri = item.getUri();
					mSelectedFiles.add(uri);
				}
			} else {
				Uri uri = data.getData();
				mSelectedFiles.add(uri);
			}
			updateTextViewSelectedFiles();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavView = findViewById(R.id.nav_view);
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_hash,
				R.id.navigation_configuration, R.id.navigation_about).build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(mNavView, navController);
		updateTextViewSelectedFiles();
	}

	public void startClearProcess() {
		Result.delete(this);
	}

	public void startCopyAllProcess() {
		String resultsCopy = "";
		List<Result> results = Result.allPlain(this);
		if (results.isEmpty()) {
			new Alert(this).noResults();
			return;
		}
		for (Result result : results) {
			resultsCopy += result.filename + "\n" + result.result + "\n";
		}
		ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clipData = ClipData.newPlainText(resultsCopy, resultsCopy);
		clipboardManager.setPrimaryClip(clipData);
		new Alert(this).copied();
	}

	public void startHashProcess(String type) {
		if (mSelectedFiles.isEmpty()) {
			new Alert(this).noSelectedFiles();
			return;
		}
		long size = 0;
		for (Uri uri : mSelectedFiles) {
			Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			if (cursor != null && cursor.moveToFirst()) {
				int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
				if (!cursor.isNull(sizeIndex))
					size += Objects.requireNonNull(cursor).getLong(sizeIndex);
			}
		}
		if (mProgressBar != null) {
			mProgressBar.setProgress(0);
			mProgressBar.setMax((int) size / 1024);
		}
		new Thread(() -> {
			startClearProcess();
			for (Uri uri : mSelectedFiles) {
				startHashUri(type, uri);
			}
		}).start();
	}

	private void startHashUri(String type, Uri uri) {
		String filename = uri.toString();
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			int filenameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
			if (!cursor.isNull(filenameIndex))
				filename = Objects.requireNonNull(cursor).getString(filenameIndex);
		}
		try {
			InputStream is = getContentResolver().openInputStream(uri);
			MessageDigest md = MessageDigest.getInstance(type);
			byte[] bytes = new byte[1024];
			int sizeRead;
			do {
				sizeRead = is.read(bytes);
				if (sizeRead > 0) {
					md.update(bytes, 0, sizeRead);
				}
				if (mProgressBar != null) {
					int finalSizeRead = sizeRead;
					mProgressBar.post(() -> {
						mProgressBar.setProgress(mProgressBar.getProgress() + finalSizeRead / 1024);
					});
				}
			} while (sizeRead != -1);
			is.close();
			new Result(filename, new BytesToString().bytesToString(md.digest())).insert(this);
		} catch (NoSuchAlgorithmException | IOException e) {
			if (mProgressBar != null) {
				mProgressBar.post(() -> {
					new Alert(this).error(e.getMessage());
				});
			}
		}
	}

	public void startSelectFilesProcess() {
		Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		i.setType("*/*");
		i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		startActivityForResult(i, 1);
	}

	private void updateTextViewSelectedFiles() {
		if (mTextViewSelectedFiles != null)
			mTextViewSelectedFiles.setText("" + mSelectedFiles.size() + " " + getString(R.string.selected_files));
	}
}