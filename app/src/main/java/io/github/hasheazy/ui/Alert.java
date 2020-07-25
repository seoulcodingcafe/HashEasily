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

import com.jaredrummler.cyanea.Cyanea;

import id.ionbit.ionalert.IonAlert;
import io.github.hasheazy.R;

public class Alert {

	private Context mContext;

	public Alert(Context context) {
		mContext = context;
	}

	public void copied() {
		IonAlert.DARK_STYLE = Cyanea.getInstance().isDark();
		new IonAlert(mContext, IonAlert.SUCCESS_TYPE).setTitleText(mContext.getString(R.string.copied)).show();
	}

	public void copiedHash() {
		IonAlert.DARK_STYLE = Cyanea.getInstance().isDark();
		new IonAlert(mContext, IonAlert.SUCCESS_TYPE).setTitleText(mContext.getString(R.string.copied_hash)).show();
	}

	public void error(String error) {
		IonAlert.DARK_STYLE = Cyanea.getInstance().isDark();
		new IonAlert(mContext, IonAlert.ERROR_TYPE).setTitleText(mContext.getString(R.string.error))
				.setContentText(error).show();
	}

	public IonAlert loading() {
		IonAlert.DARK_STYLE = Cyanea.getInstance().isDark();
		return new IonAlert(mContext, IonAlert.PROGRESS_TYPE).setTitleText(mContext.getString(R.string.loading))
				.setSpinKit("FadingCircle")
				.setSpinColor("#" + Integer.toHexString(Cyanea.getInstance().getPrimaryDark()));
	}

	public void noResults() {
		IonAlert.DARK_STYLE = Cyanea.getInstance().isDark();
		new IonAlert(mContext, IonAlert.ERROR_TYPE).setTitleText(mContext.getString(R.string.no_results)).show();
	}

	public void noSelectedFiles() {
		IonAlert.DARK_STYLE = Cyanea.getInstance().isDark();
		new IonAlert(mContext, IonAlert.ERROR_TYPE).setTitleText(mContext.getString(R.string.no_selected_files)).show();
	}
}
