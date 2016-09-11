package com.zh.viewhelperlib;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhanghongjun on 16/9/11.
 */
public class ViewHelper {
    public static void bind(Activity activity) {
        try {
            bindView(activity);             //绑定控件
            bindOnClickMethod(activity);    //绑定方法
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    //绑定控件
    private static void bindView(Activity activity) throws IllegalAccessException {
        //1 获取activity字节码
        Class clazz = activity.getClass();

        //2 获取所有的Fields
        Field[] declaredFields = clazz.getDeclaredFields();

        //3 获取所有有注解的字段
        for (Field field : declaredFields) {
            Bind annotation = field.getAnnotation(Bind.class);
            if (annotation != null) {
                int id = annotation.value();
                View view = activity.findViewById(id);
                field.setAccessible(true);//访问权限
                field.set(activity, view);//设置值
            }
        }
    }

    //绑定onClick
    private static void bindOnClickMethod(final Activity activity) {
        Class clazz = activity.getClass();

        //找方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (final Method method : declaredMethods){
            OnClick annotation = method.getAnnotation(OnClick.class);
            if(annotation != null){
                Class<?>[] parameterTypes = method.getParameterTypes();
                if(parameterTypes.length == 1 && parameterTypes[0] == View.class){
                    method.setAccessible(true);
                    int id = annotation.value();
                    View view = activity.findViewById(id);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                method.invoke(activity,view);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }else {
                    throw new IllegalStateException("param of method must equal 1 and type must View");
                }
            }
        }
    }

    //绑定onItemClick
    private static void bindOnItemClickMethod(final Activity activity) {
        Class clazz = activity.getClass();
        //找方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (final Method method : declaredMethods){
            OnClick annotation = method.getAnnotation(OnClick.class);
            if(annotation != null){
                Class<?>[] parameterTypes = method.getParameterTypes();
                if(parameterTypes.length == 4
                        && parameterTypes[0] == AdapterView.class
                        && parameterTypes[1] == View.class
                        && parameterTypes[2] == Integer.class
                        && parameterTypes[3] == Long.class){
                    method.setAccessible(true);
                    int id = annotation.value();
                    AbsListView absListView = (AbsListView) activity.findViewById(id);
                    absListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            try {
                                method.invoke(activity,adapterView,view,i,l);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else {
                    throw new IllegalStateException("param of method is valid");
                }
            }
        }
    }


    public static <T extends View> T findById(View view,int id) {
        return (T) view.findViewById(id);
    }


}
















