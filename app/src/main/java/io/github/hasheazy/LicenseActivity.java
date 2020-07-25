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

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaredrummler.cyanea.Cyanea;
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import java.io.IOException;

import id.ionbit.ionalert.IonAlert;
import io.github.hasheazy.tools.ReadFile;
import io.github.hasheazy.ui.Alert;

public class LicenseActivity extends CyaneaAppCompatActivity {

	CollapsingToolbarLayout mToolbarLayout;
	FloatingActionButton mFab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_license);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		setTheme();
		mFab.setOnClickListener(view -> finish());

		IonAlert loadAlert = new Alert(this).loading();
		loadAlert.show();
		TextView license = findViewById(R.id.license);

		new Thread(() -> {
			try {
				String licString = new ReadFile().readString(getAssets().open("gpl-3.0.txt"));
				new Handler(Looper.getMainLooper()).post(() -> {
					license.setText(licString);
					license.invalidate();
					loadAlert.dismissWithAnimation();
				});
			} catch (IOException i) {
				i.printStackTrace();
			}
		}).start();
	}

	private void setTheme() {
		mToolbarLayout = findViewById(R.id.toolbar_layout);
		mToolbarLayout.setContentScrimColor(Cyanea.getInstance().getPrimary());
		mToolbarLayout.setStatusBarScrimColor(Cyanea.getInstance().getPrimary());
		mToolbarLayout.setBackgroundColor(Cyanea.getInstance().getPrimaryLight());
		mToolbarLayout.setCollapsedTitleTextColor(Cyanea.getInstance().getMenuIconColor());
		mToolbarLayout.setExpandedTitleColor(Cyanea.getInstance().getMenuIconColor());
		mToolbarLayout.invalidate();

		mFab = findViewById(R.id.fab);
		mFab.setBackgroundColor(Cyanea.getInstance().getAccent());
		mFab.setBackgroundTintList(
				new ColorStateList(new int[][] { new int[] { Cyanea.getInstance().getNavigationBar() } },
						new int[] { Cyanea.getInstance().getNavigationBar() }));
		mFab.setRippleColor(Cyanea.getInstance().getNavigationBar());
		mFab.setSupportImageTintList(
				new ColorStateList(new int[][] { new int[] { Cyanea.getInstance().getMenuIconColor() } },
						new int[] { Cyanea.getInstance().getMenuIconColor() }));
		mFab.invalidate();
	}
}