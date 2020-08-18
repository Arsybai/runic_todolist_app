package com.todoapp.arsybai;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TodoActivity extends AppCompatActivity {
	
	
	private FloatingActionButton _fab;
	private HashMap<String, Object> temp = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> todo = new ArrayList<>();
	
	private LinearLayout linear3;
	private LinearLayout addbox;
	private ListView listview1;
	private TextView textview3;
	private ImageView addbtn;
	private EditText q;
	
	private Intent intent = new Intent();
	private AlertDialog.Builder sgl;
	private AlertDialog.Builder dbl;
	private SharedPreferences data;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.todo);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		addbox = (LinearLayout) findViewById(R.id.addbox);
		listview1 = (ListView) findViewById(R.id.listview1);
		textview3 = (TextView) findViewById(R.id.textview3);
		addbtn = (ImageView) findViewById(R.id.addbtn);
		q = (EditText) findViewById(R.id.q);
		sgl = new AlertDialog.Builder(this);
		dbl = new AlertDialog.Builder(this);
		data = getSharedPreferences("data", Activity.MODE_PRIVATE);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (todo.get((int)(todo.size() - 1) - _position).get("isdone").toString().equals("true")) {
					todo.get((int)(todo.size() - 1) - _position).put("isdone", "false");
				}
				else {
					todo.get((int)(todo.size() - 1) - _position).put("isdone", "true");
				}
				data.edit().putString("object", new Gson().toJson(todo)).commit();
				listview1.setAdapter(new Listview1Adapter(todo));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				todo.remove((int)((todo.size() - 1) - _position));
				data.edit().putString("object", new Gson().toJson(todo)).commit();
				listview1.setAdapter(new Listview1Adapter(todo));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				return true;
			}
		});
		
		addbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (q.getText().toString().equals("")) {
					ArsybaiUtil.showMessage(getApplicationContext(), "ᚲᚨᚾ ᚾᛟᛏ ᛒᛖ ᛖᛗᛈᛏᚤ");
				}
				else {
					temp.clear();
					temp.put("name", q.getText().toString());
					temp.put("isdone", "false");
					todo.add(temp);
					listview1.setAdapter(new Listview1Adapter(todo));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}
				addbox.setVisibility(View.GONE);
				data.edit().putString("object", new Gson().toJson(todo)).commit();
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				addbox.setVisibility(View.VISIBLE);
			}
		});
	}
	private void initializeLogic() {
		addbox.setVisibility(View.GONE);
		if (data.getString("nodata", "").equals("false")) {
			todo = new Gson().fromJson(data.getString("object", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview1.setAdapter(new Listview1Adapter(todo));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
		else {
			data.edit().putString("object", "[{\"name\":\"Watch Anime\",\"isdone\":\"true\"}]").commit();
			data.edit().putString("nodata", "false").commit();
			todo = new Gson().fromJson(data.getString("object", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview1.setAdapter(new Listview1Adapter(todo));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		dbl.setTitle("ᛖᛪᛁᛏ?");
		dbl.setMessage("ᚨᚱᛖ ᚤᛟᚢ ᛋᚢᚱᛖ ᚹᚨᚾᛏ ᛏᛟ ᛖᛪᛁᛏ?");
		dbl.setPositiveButton("ᚤᛖᛋ", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finish();
			}
		});
		dbl.setNegativeButton("ᚾᛟ", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dbl.create().show();
	}
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.list, null);
			}
			
			final LinearLayout box = (LinearLayout) _v.findViewById(R.id.box);
			final ImageView imageview1 = (ImageView) _v.findViewById(R.id.imageview1);
			final TextView text = (TextView) _v.findViewById(R.id.text);
			
			if (todo.get((int)(todo.size() - 1) - _position).get("isdone").toString().equals("true")) {
				android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable(); s.setColor(Color.parseColor("#1de9b6")); s.setCornerRadius(10); box.setBackground(s);
				text.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				imageview1.setVisibility(View.VISIBLE);
			}
			else {
				android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable(); s.setColor(Color.parseColor("#03a9f4")); s.setCornerRadius(10); box.setBackground(s);
				imageview1.setVisibility(View.GONE);
			}
			text.setText(todo.get((int)(todo.size() - 1) - _position).get("name").toString());
			
			return _v;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
