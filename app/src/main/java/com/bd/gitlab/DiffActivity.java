package com.bd.gitlab;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.bd.gitlab.model.Diff;
import com.bd.gitlab.model.DiffLine;
import com.bd.gitlab.tools.Repository;
import com.bd.gitlab.tools.RetrofitHelper;
import com.bd.gitlab.views.DiffView;
import com.bd.gitlab.views.MessageView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class DiffActivity extends ActionBarActivity {

    @InjectView(R.id.message_container)	LinearLayout messageContainer;
	@InjectView(R.id.diff_container) LinearLayout diffContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diff);
		ButterKnife.inject(this);

        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));

		init();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Crouton.cancelAllCroutons();
	}
	
	private void init() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Repository.selectedCommit.getShortId());
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.ic_actionbar));

		Repository.getService().getCommit(Repository.selectedProject.getId(), Repository.selectedCommit.getId(), commitCallback);
		Repository.getService().getCommitDiff(Repository.selectedProject.getId(), Repository.selectedCommit.getId(), diffCallback);
	}

	private Callback<DiffLine> commitCallback = new Callback<DiffLine>() {
		@Override
		public void success(DiffLine diffLine, Response response) {
			messageContainer.addView(new MessageView(DiffActivity.this, diffLine));
		}

		@Override
		public void failure(RetrofitError e) {
			RetrofitHelper.printDebugInfo(DiffActivity.this, e);

            Crouton.makeText(DiffActivity.this, R.string.connection_error, Style.ALERT);
		}
	};
	
	private Callback<List<Diff>> diffCallback = new Callback<List<Diff>>() {
		
		@Override
		public void success(List<Diff> diffs, Response resp) {
			for(Diff diff : diffs) {
				diffContainer.addView(new DiffView(DiffActivity.this, diff));
			}
		}
		
		@Override
		public void failure(RetrofitError e) {
			RetrofitHelper.printDebugInfo(DiffActivity.this, e);
			
			Crouton.makeText(DiffActivity.this, R.string.connection_error, Style.ALERT);
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.text_wrap_checkbox:
				item.setChecked(!item.isChecked());
				setTextWrap(item.isChecked());
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setTextWrap(boolean checked) {
		((MessageView) messageContainer.getChildAt(0)).setWrapped(checked);

		for(int i = 0; i < diffContainer.getChildCount(); ++i) {
			((DiffView) diffContainer.getChildAt(i)).setWrapped(checked);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.diff, menu);
		return true;
	}
}