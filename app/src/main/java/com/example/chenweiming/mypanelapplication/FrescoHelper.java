package com.example.chenweiming.mypanelapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.text.TextUtils;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by david.chen@soocii.me on 2016/12/7.
 */

// TODO: integrate loadingIndicatorView into drawee view
public class FrescoHelper {

    public interface ResultListener {
        void onSuccess(Bitmap bitmap);
        void onFailure(Throwable throwable);
    }

    // TODO: to be aligned with dimension resource
    public static int DEFAULT_RESIZE_WIDTH_DP = 310;
    public static int DEFAULT_RESIZE_HEIGHT_DP = 170;

    public static int DEFAULT_PORTRAIT_RESIZE_WIDTH_DP = 225;
    public static int DEFAULT_PORTRAIT_RESIZE_HEIGHT_DP = 500;


    public static int DEFAULT_BADGE_ICON_DP = 14;
    public static int DEFAULT_SMALL_ICON_DP = 40;
    public static int DEFAULT_CIRCLE_SMALL_ICON_DP = 32;
    public static int DEFAULT_CIRCLE_MEDIUM_ICON_DP = 40;
    public static int DEFAULT_CIRCLE_LARGE_ICON_DP = 72;
    public static int DEFAULT_CIRCLE_HUGE_ICON_DP = 136;

    public static ResizeOptions defSmallIconOptions;
    public static ResizeOptions defGridLandOptions;
    public static ResizeOptions defHighlightOptions;


    public static void loadInto(@NonNull String photoUrl, @NonNull final SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse(photoUrl);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .setTapToRetryEnabled(true)
                .build();

        simpleDraweeView.setController(controller);
    }

}
