package com.pax.d820.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @className: AdapterBase
 * @description:
 * @author shihui
 * @date 2013-2-25 下午13:12:23
 */
public abstract class AdapterBase extends BaseAdapter {

	private Context mContext;
	private List mList;

	private LayoutInflater mInflater;
	private Resources mResources;

	public AdapterBase(Context context, List list) {
		this.mContext = context;
		this.mList = list;
		this.mInflater = LayoutInflater.from(mContext);
		this.mResources = mContext.getResources();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	public List getList() {
		return mList;
	}

	public void setList(List mList) {
		this.mList = mList;
	}

	public LayoutInflater getInflater() {
		return mInflater;
	}

	public void setInflater(LayoutInflater mInflater) {
		this.mInflater = mInflater;
	}

	public Resources getResources() {
		return mResources;
	}

	public void setResources(Resources mResources) {
		this.mResources = mResources;
	}
}
