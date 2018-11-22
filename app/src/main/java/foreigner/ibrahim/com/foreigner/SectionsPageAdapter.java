package foreigner.ibrahim.com.foreigner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdapter  extends FragmentPagerAdapter {
    private final List<Fragment>nFragmentList=new ArrayList<>();
    private final List<String>nFragmenTitleList=new ArrayList<>();

    public void addFragment(Fragment fragment, String title){
        nFragmentList.add(fragment);
        nFragmenTitleList.add(title);
    }



    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return nFragmenTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return nFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return nFragmentList.size();
    }



    }



