package com.bd.gitlab.fragments;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.bd.gitlab.FileActivity;
import com.bd.gitlab.R;
import com.bd.gitlab.adapter.FilesAdapter;
import com.bd.gitlab.model.TreeItem;
import com.bd.gitlab.tools.Repository;
import com.bd.gitlab.tools.RetrofitHelper;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class FilesFragment extends Fragment implements OnItemClickListener, OnRefreshListener {
	
	private ArrayList<String> path;
	
	@InjectView(R.id.fragmentList) ListView listView;
	@InjectView(R.id.error_text) TextView errorText;
    @InjectView(R.id.ptr_layout) PullToRefreshLayout ptrLayout;
	
	public FilesFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_files, container, false);
		ButterKnife.inject(this, view);
		
		listView.setOnItemClickListener(this);

        ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable().listener(this).setup(ptrLayout);
		
		path = new ArrayList<String>();
		
		if(Repository.selectedProject != null)
			loadData();
		
		return view;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
        ButterKnife.reset(this);
	}
	
	public void loadData() {
		path = new ArrayList<String>();
		loadFiles();
	}
	
	@Override
	public void onRefreshStarted(View view) {
		loadFiles();
	}
	
	private void loadFiles() {
		String branch = "master";
		if(Repository.selectedBranch != null)
			branch = Repository.selectedBranch.getName();
		
		if(ptrLayout != null && !ptrLayout.isRefreshing())
            ptrLayout.setRefreshing(true);
		
		String currentPath = "";
        for(String p : path) {
            currentPath += p;
        }
		
		Repository.getService().getTree(Repository.selectedProject.getId(), branch, currentPath, filesCallback);
	}
	
	private Callback<List<TreeItem>> filesCallback = new Callback<List<TreeItem>>() {
		
		@Override
		public void success(List<TreeItem> files, Response resp) {
            if(ptrLayout != null && ptrLayout.isRefreshing())
                ptrLayout.setRefreshComplete();
			
			FilesAdapter filesAdapter = new FilesAdapter(getActivity(), files);
			listView.setAdapter(filesAdapter);
		}
		
		@Override
		public void failure(RetrofitError e) {
			if(ptrLayout != null && ptrLayout.isRefreshing())
                ptrLayout.setRefreshComplete();
			
			if(e.getResponse().getStatus() == 404) {
				errorText.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
			}
			else {
				if(path.size() > 0)
					path.remove(path.size() - 1);
				
				listView.setAdapter(null);
				
				if(e.getResponse().getStatus() != 500) {
                    RetrofitHelper.printDebugInfo(getActivity(), e);
                    Crouton.makeText(getActivity(), R.string.connection_error_files, Style.ALERT).show();
                }
			}
		}
	};
	
	public boolean onBackPressed() {
		if(path.size() > 0) {
			path.remove(path.size() - 1);
			loadFiles();
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Repository.selectedFile = ((FilesAdapter) listView.getAdapter()).getItem(position);
		
		if(Repository.selectedFile.getType().equals("tree")) {
			path.add(Repository.selectedFile.getName() + "/");
			loadFiles();
		}
		else if(Repository.selectedFile.getType().equals("blob")) {
			String pathExtra = "";
			for(String p : path) {
                pathExtra += p;
            }
			
			Intent i = new Intent(getActivity(), FileActivity.class);
			i.putExtra("path", pathExtra);
			startActivity(i);
		}
	}
}