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

package io.github.hasheazy.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jaredrummler.cyanea.Cyanea;

import id.ionbit.ionalert.IonAlert;
import io.github.hasheazy.LicenseActivity;
import io.github.hasheazy.R;

public class About {

	private Context mContext;

	public About(Context context) {
		mContext = context;
	}

	public void about() {
		IonAlert.DARK_STYLE = Cyanea.getInstance().isDark();
		new IonAlert(mContext, IonAlert.NORMAL_TYPE).setTitleText(mContext.getString(R.string.app_name))
				.setContentText(mContext.getString(R.string.this_program_is_free_software)).show();
	}

	public void license() {
		mContext.startActivity(new Intent(mContext, LicenseActivity.class));
	}

	public void version() throws PackageManager.NameNotFoundException {
		PackageInfo p = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
		IonAlert.DARK_STYLE = Cyanea.getInstance().isDark();
		new IonAlert(mContext, IonAlert.NORMAL_TYPE).setTitleText(mContext.getString(R.string.version))
				.setContentText(p.versionName).show();
	}
}
