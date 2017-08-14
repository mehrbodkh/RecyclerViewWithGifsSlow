package com.example.mehrbod.testingrecyclerview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

/**
 * Created by mehrbod on 8/13/17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private final String PATH = "/storage/emulated/0/Download/ge.mp4";
    private final Activity activity;

    public Adapter(Activity activity) {
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public VideoView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (VideoView) itemView.findViewById(R.id.myImageView);
        }
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        Log.d("ViewHolder", "Created");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Adapter.MyViewHolder holder, int position) {


        new Task().execute(holder);
        Log.d("RecyclerView", "onBind");
    }


    @Override
    public void onViewAttachedToWindow(MyViewHolder holder) {
        Log.d("RecyclerView", "onAttach");

        super.onViewAttachedToWindow(holder);
    }

    public class Task extends AsyncTask<MyViewHolder, String, String> {

        
        @Override
        protected String doInBackground(MyViewHolder... params) {
            final MyViewHolder holder = params[0];
            final Bitmap bitmap = ThumbnailUtils.createVideoThumbnail
                    (PATH, MediaStore.Images.Thumbnails.MINI_KIND);


            final Uri uri = Uri.parse(PATH);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    long startTime = System.currentTimeMillis();
                    holder.imageView.start();
                    holder.imageView.setVideoURI(uri);
                    Log.d("LoadTime", "" + (System.currentTimeMillis() - startTime));

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        Log.d("RecyclerView", "onDetach");
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return 10000;
    }
}
