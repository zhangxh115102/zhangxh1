package com.shsunedu.tool;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class SoftReferenceThreadLocal extends ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> {

        @Override
        protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
            return new SoftReference<Map<String, SimpleDateFormat>>(
                    new HashMap<String, SimpleDateFormat>());
        }
    }