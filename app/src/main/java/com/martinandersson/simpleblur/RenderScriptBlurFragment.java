package com.martinandersson.simpleblur;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by martin.andersson on 8/20/15.
 */
public class RenderScriptBlurFragment extends Fragment {
    public static final String TAG = RenderScriptBlurFragment.class.getSimpleName();

    @Bind(R.id.image_view)
    ImageView mImageView;

    @Bind(R.id.blurred_view)
    ImageView mBlurredView;

    @Bind(R.id.status_text)
    TextView mStatusText;

    float mBlurRadius;
    float mAlpha;

    public static RenderScriptBlurFragment newInstance(float blurRadius, float alpha) {
        RenderScriptBlurFragment fragment = new RenderScriptBlurFragment();
        fragment.mBlurRadius = blurRadius;
        fragment.mAlpha = alpha;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView");

        applyBlur();
        return view;
    }

    private void applyBlur() {
        mImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                mImageView.buildDrawingCache();

                Bitmap bitmap = mImageView.getDrawingCache();
                blur(bitmap, mBlurredView);
                mImageView.setVisibility(View.GONE);
                mBlurredView.setAlpha(mAlpha);
                return true;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bkg, ImageView imageView) {
        long startMs = System.currentTimeMillis();

        Bitmap overlay = Bitmap.createBitmap(imageView.getMeasuredWidth(), imageView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);
        canvas.translate(-imageView.getLeft(), -imageView.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);

        RenderScript rs = RenderScript.create(getActivity());
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(mBlurRadius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);

//        imageView.setBackground(new BitmapDrawable(getResources(), overlay));
        imageView.setImageBitmap(overlay);

        rs.destroy();
        mStatusText.setText((System.currentTimeMillis() - startMs) + "ms (RS), radius=" + mBlurRadius + ", alpha=" + mAlpha);

    }

}