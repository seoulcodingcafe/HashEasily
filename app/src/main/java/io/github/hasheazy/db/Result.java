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

package io.github.hasheazy.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "result_table")
public class Result {
	public static LiveData<List<Result>> all(Context context) {
		return Database.getDatabase(context).resultDao().all();
	}

	public static List<Result> allPlain(Context context) {
		return Database.getDatabase(context).resultDao().allPlain();
	}

	public static void delete(Context context) {
		Database.getDatabase(context).resultDao().delete();
	}

	@PrimaryKey(autoGenerate = true)
	public int id;

	@NonNull
	public String filename = "";

	@NonNull
	public String result = "";

	public long createdAt = System.currentTimeMillis();

	public Result(String filename, String result) {
		this.filename = filename;
		this.result = result;
	}

	public void insert(Context context) {
		createdAt = System.currentTimeMillis();
		Database.getDatabase(context).resultDao().insert(this);
	}

	public void update(Context context) {
		createdAt = System.currentTimeMillis();
		Database.getDatabase(context).resultDao().update(this);
	}
}