package cn.cloudworkshop.shop.utils;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2018/11/29 16:50
 * Email：1993911441@qq.com
 * Describe：
 */
@com.bumptech.glide.annotation.GlideModule
public class GlideConfigUtils extends AppGlideModule {
    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        builder.setDefaultRequestOptions(
                new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
        );


        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        int diskCacheSizeBytes = 1024 * 1024 * 100;  //100 MB
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes))
                .setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));


    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}

