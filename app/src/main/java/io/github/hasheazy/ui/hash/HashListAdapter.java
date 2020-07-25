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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.hasheazy.R;
import io.github.hasheazy.db.Result;
import io.github.hasheazy.ui.Alert;

public class HashListAdapter extends RecyclerView.Adapter<HashListAdapter.ResultViewHolder> {

	static class ResultViewHolder extends RecyclerView.ViewHolder {
		private final TextView mTextViewFilename;
		private final TextView mTextViewResult;
		private final View mBackGround;
		Result mResult;

		private ResultViewHolder(View itemView) {
			super(itemView);
			mTextViewFilename = itemView.findViewById(R.id.textViewFilename);
			mTextViewResult = itemView.findViewById(R.id.textViewResult);
			mBackGround = itemView.findViewById(R.id.backGround);
		}

		void copyResult(Context context) {
			ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clipData = ClipData.newPlainText(mResult.result, mResult.result);
			clipboardManager.setPrimaryClip(clipData);

			new Alert(context).copiedHash();
		}

		void updateContent() {
			mTextViewFilename.setText(mResult.filename);
			mTextViewResult.setText(mResult.result);
			mTextViewFilename.setOnClickListener(v -> copyResult(v.getContext()));
			mTextViewResult.setOnClickListener(v -> copyResult(v.getContext()));
			mTextViewResult.setOnClickListener(v -> copyResult(v.getContext()));
			mBackGround.setOnClickListener(v -> copyResult(v.getContext()));
		}
	}

	private final LayoutInflater mInflater;

	private List<Result> mResults;

	HashListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getItemCount() {
		if (mResults != null)
			return mResults.size();
		else
			return 0;
	}

	@Override
	public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
		if (mResults != null) {
			holder.mResult = mResults.get(position);
			holder.updateContent();
		}
	}

	@Override
	public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = mInflater.inflate(R.layout.recycler_result, parent, false);

		return new ResultViewHolder(itemView);
	}

	void setResults(List<Result> results) {
		mResults = results;
		notifyDataSetChanged();
	}
}
