package daichan4649.volley;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class TestListFragment extends SherlockListFragment {

    // debug data
    private static final int DATA_SIZE = 50;
    private static List<BindData> dataList;
    static {
        List<String> urlList = new ArrayList<String>();
        urlList.add("http://developer.android.com/design/media/index_landing_page.png");
        urlList.add("http://developer.android.com/design/media/ui_overview_app_ui.png");
        urlList.add("http://developer.android.com/design/media/ui_overview_notifications.png");
        urlList.add("http://developer.android.com/design/media/ui_overview_system_ui.png");
        urlList.add("http://developer.android.com/design/media/ui_overview_recents.png");
        urlList.add("http://developer.android.com/design/media/ui_overview_all_apps.png");
        urlList.add("http://developer.android.com/design/media/ui_overview_home_screen.png");
        urlList.add("http://developer.android.com/design/media/creative_vision_main.png");
        urlList.add("http://developer.android.com/design/media/action_bar_pattern_overview.png");
        urlList.add("http://developer.android.com/design/media/action_bar_pattern_rotation.png");

        dataList = new ArrayList<BindData>();
        for (int i = 0; i < DATA_SIZE; i++) {
            BindData data = new BindData();
            data.text = String.format("i=%d", i);
            data.imageUrl = urlList.get(i % urlList.size());
            dataList.add(data);
        }
    }

    public static TestListFragment newInstance() {
        return new TestListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TestAdapter adapter = new TestAdapter(getActivity(), dataList);
        setListAdapter(adapter);
    }

    // Adapter
    private static class TestAdapter extends ArrayAdapter<BindData> {

        private LayoutInflater mInflater;
        private ImageLoader mImageLoader;

        public TestAdapter(Context context, List<BindData> dataList) {
            super(context, 0, dataList);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Volley setting
            RequestQueue queue = Volley.newRequestQueue(context);
            mImageLoader = new ImageLoader(queue, new BitmapCache());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.imageView = (NetworkImageView) convertView.findViewById(R.id.network_image_view);
                holder.text = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            BindData data = getItem(position);
            holder.imageView.setImageUrl(data.imageUrl, mImageLoader);
            holder.text.setText(data.text);

            return convertView;
        }
    }

    private static class BindData {
        String imageUrl;
        String text;
    }

    private static class ViewHolder {
        NetworkImageView imageView;
        TextView text;
    }
}
