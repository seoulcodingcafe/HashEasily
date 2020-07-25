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

package io.github.hasheazy.ui.hash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.hasheazy.MainActivity;
import io.github.hasheazy.R;
import io.github.hasheazy.db.ResultViewModel;

public class HashFragment extends Fragment {

	private HashListAdapter mAdapter;
	private ResultViewModel mResultViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_hash, container, false);
		RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

		root.findViewById(R.id.buttonSelectFiles)
				.setOnClickListener(v -> ((MainActivity) requireActivity()).startSelectFilesProcess());
		root.findViewById(R.id.buttonCopyAll)
				.setOnClickListener(v -> ((MainActivity) requireActivity()).startCopyAllProcess());
		root.findViewById(R.id.buttonClear)
				.setOnClickListener(v -> ((MainActivity) requireActivity()).startClearProcess());
		root.findViewById(R.id.buttonMD5)
				.setOnClickListener(v -> ((MainActivity) requireActivity()).startHashProcess("MD5"));
		root.findViewById(R.id.buttonSHA1)
				.setOnClickListener(v -> ((MainActivity) requireActivity()).startHashProcess("SHA-1"));
		root.findViewById(R.id.buttonSHA256)
				.setOnClickListener(v -> ((MainActivity) requireActivity()).startHashProcess("SHA-256"));
		root.findViewById(R.id.buttonSHA512)
				.setOnClickListener(v -> ((MainActivity) requireActivity()).startHashProcess("SHA-512"));
		((MainActivity) requireActivity()).mTextViewSelectedFiles = root.findViewById(R.id.textViewSelectedFiles);
		((MainActivity) requireActivity()).mTextViewSelectedFiles.setText("0 " + getString(R.string.selected_files));
		((MainActivity) requireActivity()).mProgressBar = root.findViewById(R.id.progressBar);
		mAdapter = new HashListAdapter(getActivity());
		recyclerView.setAdapter(mAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		mResultViewModel = new ViewModelProvider(this).get(ResultViewModel.class);
		mResultViewModel.getAllResults().observe(getViewLifecycleOwner(), results -> mAdapter.setResults(results));

		return root;
	}

}