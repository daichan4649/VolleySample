package daichan4649.volley;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class MainActivity extends FragmentActivity {

    private static final String URL_IMAGE = "http://developer.android.com/design/media/index_landing_page.png";

    private static final Object TAG = new Object();
    private RequestQueue mQueue = null;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        mImageLoader = new ImageLoader(mQueue, new BitmapCache());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.use_image_request:
            useImageRequest();
            return true;

        case R.id.use_nw_imageview:
            useNetworkImageView();
            return true;

        case R.id.test_listview:
            Intent intent = new Intent(MainActivity.this, TestListActivity.class);
            startActivity(intent);
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void useImageRequest() {
        final ImageView imageView = (ImageView) findViewById(R.id.image_view);

        // clear
        imageView.setImageBitmap(null);

        // start load
        Listener<Bitmap> listener = new Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap result) {
                imageView.setImageBitmap(result);
            }
        };
        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        };
        ImageRequest imageRequest = new ImageRequest(URL_IMAGE, listener, 0, 0, Config.ARGB_8888, errorListener);
        imageRequest.setTag(TAG);
        mQueue.add(imageRequest);
    }

    private void useNetworkImageView() {
        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.network_image_view);

        // clear
        imageView.setImageUrl(null, null);

        // start load
        imageView.setImageUrl(URL_IMAGE, mImageLoader);
    }
}
