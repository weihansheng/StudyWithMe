package com.app.swm.service;


import java.util.ArrayList;
import java.util.List;

import com.app.swm.entity.Group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatebaseService {
	private DatebaseHelper dbHelper;

	public DatebaseService(Context context) {
		this.dbHelper = new DatebaseHelper(context);
	}

	// 保存数据。
	public void save(Group news) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("title", news.getTitle());
		/*values.put("content", news.getContent());
		values.put("newsPics", news.getNewsPics());*/
		db.insert("news", null, values);
	}

	public void saveNewsList(List<Group> newslist) {

		for (Group news : newslist) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("title", news.getTitle());
			/*values.put("content", news.getContent());
			values.put("newsPics", news.getNewsPics());*/
			db.insert("news", null, values);
		}

	}

	/**
	 * 向数据库中的表中插入数据
	 * 
	 * @param name
	 * @return 插入数据的id
	 */
	public long insertData(Group news) {
		SQLiteDatabase db = null;
		long id = -1;
		try {
			// 获取数据库对象
			db = dbHelper.getWritableDatabase();
			// 使用execSQL()方法向表中插入数据
			// db.execSQL("insert into user(name) values('"+name+"')");
			// 使用insert()方法向表中插入数据
			// 创建contentvalues对象存储“列名-列值”的映射
			ContentValues values = new ContentValues();
			values.put("title", news.getTitle());
			/*values.put("content", news.getContent());
			values.put("newsPics", news.getNewsPics());
			values.put("time", news.getDate());*/
			// 调用方法插入数据
			id = db.insert("news", null, values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}

		return id;

	}

	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 *            数据的id
	 */
	public void delData(long id) {
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			// Object[] bingArg={id};
			// db.execSQL("delete from user where _id=?",bingArg);
			String whereClause = "newsid=?";
			String[] whereArgs = { String.valueOf(id) };
			db.delete("news", whereClause, whereArgs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}

	}

	/**
	 * 根据id修改name字段的值
	 * 
	 * @param id
	 *            数据的id
	 * @param name
	 *            更新的name
	 * @return 更新的行数
	 */
	public long updateData(long id, String title, String content,
			String newsPics) {
		long row = 0;
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put("title", title);
			cv.put("content", content);
			cv.put("newsPics", newsPics);
			String where = "newsid=?";
			String[] whereValue = { String.valueOf(id) };
			row = db.update("news", cv, where, whereValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}

		return row;

	}

	/**
	 * 
	 * 从数据库中查询数据
	 * 
	 * @return
	 */
	public List<Group> queryData() {
		List<Group> list = new ArrayList<Group>();
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			// 获取数据库对象，如果数据库不存在则创建
			db = dbHelper.getReadableDatabase();
			// 查询表中的数据，获取游标
			cursor = db.query("news", null, null, null, null, null,
					"newsid desc");
			// 获取id列的索引
			int idIndex = cursor.getColumnIndex("newsid");
			// 获取title列的索引
			int titleIndex = cursor.getColumnIndex("title");
			// 获取content列的索引
			int contentIndex = cursor.getColumnIndex("content");
			// 获取content列的索引
			int newsPicsIndex = cursor.getColumnIndex("newsPics");
			// 获取time列的索引
			int timeIndex = cursor.getColumnIndex("time");
			// 遍历查询结果集，将数据提取出来
			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				Group map = new Group();

				// 出错的地方 title是数字的原因是因为写成了map.put("itemsTitle", idIndex);
				// 返回的是idIndex
				// map.put("tvTitle", cursor.getString(titleIndex));
				// map.put("tvContent", cursor.getString(contentIndex));
				map.setTitle(cursor.getString(titleIndex));
				/*map.setContent(cursor.getString(contentIndex));
				map.setNewsPics(cursor.getString(newsPicsIndex));
				map.setDate(cursor.getString(timeIndex));*/
				map.setId(cursor.getInt(idIndex));
				list.add(map);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				// 关闭游标
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				// 关闭数据库对象
				db.close();
			}
		}
		return list;

	}

	/**
	 * 从数据库中查询数据
	 * 
	 * @param id
	 * @return
	 */
	public Group queryDataById(long id) {
		Group news = new Group();
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			// 获取数据库对象，如果数据库不存在则创建
			db = dbHelper.getReadableDatabase();
			// 查询表中的数据，获取游标
			String where = "newsid =?";
			String[] whereValue = { String.valueOf(id) };
			cursor = db
					.query("news", null, where, whereValue, null, null, null);
			if (cursor == null) {
				return null;
			}
			if (cursor.moveToFirst()) {
				// 获取id列的索引
				int idIndex = cursor.getColumnIndex("newsid");
				// 获取title列的索引
				int titleIndex = cursor.getColumnIndex("title");
				// 获取content列的索引
				int contentIndex = cursor.getColumnIndex("content");
				// 获取content列的索引
				int newsPicsIndex = cursor.getColumnIndex("newsPics");
				// 获取time列的索引
				int timeIndex = cursor.getColumnIndex("time");
				news.setTitle(cursor.getString(titleIndex));
				/*news.setContent(cursor.getString(contentIndex));
				news.setNewsPics(cursor.getString(newsPicsIndex));
				news.setDate(cursor.getString(timeIndex));*/
				news.setId(cursor.getInt(idIndex));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				// 关闭游标
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				// 关闭数据库对象
				db.close();
			}
		}
		return news;

	}

}
