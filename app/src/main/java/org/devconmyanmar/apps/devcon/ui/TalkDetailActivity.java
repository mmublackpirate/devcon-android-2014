package org.devconmyanmar.apps.devcon.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmQuery;
import java.util.ArrayList;
import java.util.List;
import org.devconmyanmar.apps.devcon.R;
import org.devconmyanmar.apps.devcon.adapter.MyPagerAdapter;
import org.devconmyanmar.apps.devcon.model.Talk;
import org.devconmyanmar.apps.devcon.transformer.StackTransformer;

import static org.devconmyanmar.apps.devcon.Config.POSITION;

public class TalkDetailActivity extends BaseActivity {

  @InjectView(R.id.schedule_pager) ViewPager mScheduleViewPager;
  @InjectView(R.id.toolbar) Toolbar mToolbar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_schedule_detail);
    ButterKnife.inject(this);
    setSupportActionBar(mToolbar);
    mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_back_mtrl_am_alpha);

    Intent intent = getIntent();
    int position = intent.getIntExtra(POSITION, 0);

    List<Fragment> fragments = getTalkFragments();
    MyPagerAdapter mAdapter =
        new MyPagerAdapter(getSupportFragmentManager(), fragments);

    mScheduleViewPager.setPageTransformer(true, new StackTransformer());
    mScheduleViewPager.setAdapter(mAdapter);
    mScheduleViewPager.setCurrentItem(position);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_schedule_detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    switch(id){
      case R.id.action_settings:
        return true;
      case R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);

    }


  }

  private List<Fragment> getTalkFragments() {
    List<Fragment> fList = new ArrayList<Fragment>();
    Realm realm = Realm.getInstance(this);
    RealmQuery<Talk> query = realm.where(Talk.class);
    List<Talk> talks = query.findAll();

    for (Talk talk : talks) {
      int talkId = talk.getId();
      fList.add(TalkDetailFragment.newInstance(String.valueOf(talkId)));
    }

    return fList;
  }
}
