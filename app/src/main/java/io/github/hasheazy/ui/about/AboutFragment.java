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

package io.github.hasheazy.ui.about;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import io.github.hasheazy.R;
import io.github.hasheazy.ui.About;

public class AboutFragment extends PreferenceFragmentCompat {

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.about, rootKey);
		findPreference("commandabout").setOnPreferenceClickListener(preference -> {
			new About(getActivity()).about();
			return false;
		});
		findPreference("commandlicense").setOnPreferenceClickListener(preference -> {
			new About(getActivity()).license();
			return false;
		});
		findPreference("commandversion").setOnPreferenceClickListener(preference -> {
			try {
				new About(getActivity()).version();
			} catch (PackageManager.NameNotFoundException n) {
				n.printStackTrace();
			}
			return false;
		});

	}

}